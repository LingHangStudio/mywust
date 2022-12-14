package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.physics.PhysicsIndexPageParser;
import cn.linghang.mywust.core.request.physics.PhysicsSystemRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PhysicsLogin {
    private static final Logger log = LoggerFactory.getLogger(PhysicsLogin.class);

    private final Requester requester;

    private static final PhysicsIndexPageParser physicsIndexPageParser = new PhysicsIndexPageParser();

    public PhysicsLogin(Requester requester) {
        this.requester = requester;
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestClientOption) throws IOException, ApiException, ParseException {
        // 直接登录，ASP.NET_SessionId其实在这步就能获取到，不需要再请求一遍首页获取
        HttpRequest loginCookieRequest = PhysicsSystemRequestFactory.loginCookiesRequest(username, password, null);
        HttpResponse loginCookieResponse = requester.post(loginCookieRequest, requestClientOption);
        if (loginCookieResponse.getStatusCode() != HttpResponse.HTTP_REDIRECT_302) {
            throw new ApiException(ApiException.Code.PHYSICS_PASSWORD_WRONG);
        }

        String loginCookies = loginCookieResponse.getCookies();
        if (loginCookies == null) {
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
        }

        // 请求主页，获取实验选课系统的链接，顺便测试获取到的cookie是否有效，主页能否正常访问
        HttpRequest indexRequest = PhysicsSystemRequestFactory.mainIndexRequest(loginCookies);
        HttpResponse indexResponse = requester.get(indexRequest, requestClientOption);
        if (indexResponse.getStatusCode() != HttpResponse.HTTP_OK || indexResponse.getBody() == null) {
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
        }

        // 登录实验选课系统，因为之前使用的登录接口和真正的物理实验选课系统是两个不一样的系统，
        // 因此需要再次带上cookie请求一遍真正的物理实验选课系统，cookie才能在后面的接口中生效
        String indexHtml = new String(indexResponse.getBody());
        String redirect = physicsIndexPageParser.parse(indexHtml);
        HttpRequest systemIndexRequest = PhysicsSystemRequestFactory.physicsSystemIndexRequest(redirect, loginCookies);

        requestClientOption.setFallowUrlRedirect(true);
        HttpResponse response = requester.get(systemIndexRequest, requestClientOption);
        if (response.getStatusCode() != HttpResponse.HTTP_OK) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }

        return loginCookies;
    }
}