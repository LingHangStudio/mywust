package cn.wustlinghang.mywust.core.request.service.auth;

import cn.wustlinghang.mywust.core.parser.physics.PhysicsIndexPageParser;
import cn.wustlinghang.mywust.core.request.factory.physics.PhysicsSystemRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
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
        // 获取“动态”的表单参数，例如__VIEWSTATE等
        HttpRequest loginIndexPageRequest = PhysicsSystemRequestFactory.loginIndexRequest();
        HttpResponse loginIndexPageResponse = requester.get(loginIndexPageRequest);
        String loginIndex = loginIndexPageResponse.getStringBody();

        // 直接登录，ASP.NET_SessionId其实在这步就能获取到，不需要再请求一遍首页获取
        HttpRequest loginCookieRequest = PhysicsSystemRequestFactory.loginCookiesRequest(username, password, loginIndex);
        HttpResponse loginCookieResponse = requester.post(loginCookieRequest, requestClientOption);
        if (loginCookieResponse.getStatusCode() != HttpResponse.HTTP_REDIRECT_302) {
            String responseHtml = loginCookieResponse.getStringBody();
            if (responseHtml.contains("该用户不存在于当前学期")){
                throw new ApiException(ApiException.Code.PHYSICS_NOT_CURRENT_TERM);
            } else if (responseHtml.contains("用户名或者密码有误")) {
                throw new ApiException(ApiException.Code.PHYSICS_PASSWORD_WRONG);
            } else {
                throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
            }
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

        requestClientOption.setFollowUrlRedirect(true);
        HttpResponse response = requester.get(systemIndexRequest, requestClientOption);
        if (response.getStatusCode() != HttpResponse.HTTP_OK) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }

        return loginCookies;
    }

    public boolean checkCookie(String cookie, RequestClientOption option) throws IOException {
        HttpRequest testRequest = PhysicsSystemRequestFactory.physicsSystemIndexRequest(cookie);
        HttpResponse testResponse = requester.get(testRequest, option);

        return testResponse.getStatusCode() != HttpResponse.HTTP_REDIRECT_302;
    }
}