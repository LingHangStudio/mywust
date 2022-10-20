package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.api.JwcApiUrl;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.exception.PasswordWornException;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;

import java.io.IOException;
import java.net.URL;

public class JwcLogin {
    // 默认5秒超时
    private static final int DEFAULT_TIMEOUT = 5;

    private final Requester requester;

    public JwcLogin(Requester requester) {
        this.requester = requester;
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestOption) throws IOException, BasicException {
        // 获取ticket granting ticket（TGT）
        HttpRequest TGTRequest = AuthRequestGenerator.unionLoginTGTRequest(username, password, JwcApiUrl.JWC_SSO_SERVICE);

        HttpResponse unionAuthResponse = requester.post(new URL(JwcApiUrl.UNION_AUTH_API), TGTRequest, requestOption);

        String redirectAddress = unionAuthResponse.getHeaders().get("Location");
        if (redirectAddress == null) {
            throw new PasswordWornException();
        }

        // 获取服务ticket（service ticket，st）
        HttpRequest serviceTicketRequest = AuthRequestGenerator.loginTicketRequest(JwcApiUrl.JWC_SSO_SERVICE);

        HttpResponse serviceTicketResponse =
                requester.post(new URL(redirectAddress.replace("http:", "https:")), serviceTicketRequest, requestOption);

        byte[] serviceTicketResponseData = serviceTicketResponse.getBody();
        if (serviceTicketResponseData == null) {
            System.err.println("获取服务st出错，serviceTicketResponseData == null");
            throw new BasicException();
        }
        String serviceTicket = new String(serviceTicketResponseData);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = AuthRequestGenerator.sessionCookieRequest();
        HttpResponse sessionResponse = requester.get(new URL(String.format(JwcApiUrl.JWC_TICKET_API, serviceTicket)), sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (cookies == null || !cookies.contains("JSESSIONID") || !cookies.contains("SERVERID")) {
            System.err.println("获取服务session cookie出错，关键Cookie缺失");
            System.err.println("Cookies: " + cookies);
            throw new BasicException();
        }

        return cookies;
    }

    private void getLoginGTG(String username, String password) {

    }
}
