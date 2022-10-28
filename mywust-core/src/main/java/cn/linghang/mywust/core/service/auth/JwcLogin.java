package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.core.api.UnionAuth;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JwcLogin {
    private static final Logger log = LoggerFactory.getLogger(JwcLogin.class);

    private final Requester requester;

    private final UnionLogin unionLogin;

    public JwcLogin(Requester requester) {
        this.requester = requester;
        this.unionLogin = new UnionLogin(requester);
    }

    public JwcLogin(Requester requester, UnionLogin unionLogin) {
        this.requester = requester;
        this.unionLogin = unionLogin;
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestOption) throws IOException, BasicException {
        // 获取service ticket以进行进一步的登录
        String serviceTicket = unionLogin.getServiceTicket(username, password, UnionAuth.Service.BKJX_SSO_SERVICE, requestOption);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = BkjxRequestFactory.sessionCookieRequest(serviceTicket);
        HttpResponse sessionResponse = requester.get(sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (roughCheckCookie(cookies)) {
            throw new BasicException();
        }

        return cookies;
    }

    private boolean roughCheckCookie(String cookies) {
        return cookies == null || !cookies.contains("JSESSIONID") || !cookies.contains("SERVERID");
    }

    private static final int COOKIES_ERROR_RESPONSE_LENGTH =
            ("<script languge='javascript'>" +
                    "window.location.href='https://auth.wust.edu.cn/lyuapServer/login?service=http%3A%2F%2Fbkjx.wust.edu.cn%2Fjsxsd%2Fframework%2FblankPage.jsp'" +
                    "</script>").length();

    public boolean checkCookies(String cookies, RequestClientOption option) throws IOException {
        HttpRequest testRequest = BkjxRequestFactory.makeHttpRequest(Bkjx.BKJX_TEST_API, null, cookies);
        HttpResponse testResponse = requester.get(testRequest, option);

        // 判断响应长度是否为这么多个字，登录跳转响应长度
        // 这种办法虽然在极端情况下可能会出错（而且还挺蠢的），但是是最快的办法中比较准确的了
        return testResponse.getBody().length != COOKIES_ERROR_RESPONSE_LENGTH;
    }

}
