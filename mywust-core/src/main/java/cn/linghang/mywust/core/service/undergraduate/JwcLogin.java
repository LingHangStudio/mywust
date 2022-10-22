package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.core.api.UnionAuth;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.exception.PasswordWornException;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class JwcLogin {
    private static final Logger log = LoggerFactory.getLogger(JwcLogin.class);

    private final Requester requester;

    private final UnionLogin unionLogin;

    public JwcLogin(Requester requester, UnionLogin unionLogin) {
        this.requester = requester;
        this.unionLogin = unionLogin;
    }

    /**
     * <p>旧版的登录方式，既相当于直接登录<a href="http://bkjx.wust.edu.cn">bkjx系统，不建议使用</a></p>
     * <p>注意，这种登录方式的密码和新版可能是不一样的</p>
     * <p>不过不论使用哪种登录方式获取到的cookie都是可用的</p>
     *
     * @return 获取到的Cookies
     */
    @Deprecated
    public String getLoginCookieLegacy(String username, String password, RequestClientOption requestOption) throws IOException, BasicException {
        // 获取某段神秘的dataStr（反正官网代码是这么叫的）
        HttpRequest dataStringRequest = BkjxAuthRequestFactory.Legacy.dataStringRequest();
        HttpResponse dataStringResponse = requester.post(new URL(Bkjx.Legacy.BKJX_DATA_STRING_API), dataStringRequest, requestOption);
        if (dataStringResponse.getBody() == null) {
            throw new BasicException();
        }

        String dataString = new String(dataStringResponse.getBody());

        // 获取登录ticket
        String encoded = PasswordEncoder.legacyPassword(username, password, dataString);
        HttpRequest ticketRequest = BkjxAuthRequestFactory.Legacy.ticketRedirectRequest(encoded);
        ticketRequest.setCookies(dataStringResponse.getCookies());
        HttpResponse ticketResponse = requester.post(new URL(Bkjx.Legacy.BKJX_SESSION_COOKIE_API), ticketRequest, requestOption);
        if (ticketResponse.getBody() == null) {
            throw new BasicException();
        }

        // 使用跳转得到的链接获取cookies
        String sessionRedirect = ticketResponse.getHeaders().get("Location");
        if (sessionRedirect == null) {
            throw new PasswordWornException();
        }
        HttpRequest sessionRequest = BkjxAuthRequestFactory.Legacy.dataStringRequest();

        HttpResponse sessionResponse = requester.get(new URL(sessionRedirect), sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (roughCheckCookie(cookies)) {
            throw new BasicException();
        }

        return cookies;
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestOption) throws IOException, BasicException {
        String serviceTicket = unionLogin.getServiceTicket(username, password, UnionAuth.BKJX_SSO_SERVICE, requestOption);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = BkjxAuthRequestFactory.sessionCookieRequest();
        HttpResponse sessionResponse = requester.get(new URL(String.format(Bkjx.BKJX_SESSION_COOKIE_API, serviceTicket)), sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (roughCheckCookie(cookies)) {
            throw new BasicException();
        }

        return cookies;
    }

    private boolean roughCheckCookie(String cookies) throws BasicException {
        return cookies == null || !cookies.contains("JSESSIONID") || !cookies.contains("SERVERID");
    }

    private static final int COOKIES_ERROR_RESPONSE_LENGTH =
            ("<script languge='javascript'>window.location.href=" +
                    "'https://auth.wust.edu.cn/lyuapServer/login?service=http%3A%2F%2Fbkjx.wust.edu.cn%2Fjsxsd%2Fxxwcqk%2Fxxwcqk_idxOnzh.do'" +
                    "</script>").length();


    public boolean checkCookies(String cookies) throws IOException {
        RequestClientOption option = RequestClientOption.DEFAULT_OPTION;

        HttpRequest testRequest = BkjxAuthRequestFactory.getDefaultHttpRequest();
        testRequest.setCookies(cookies);
        HttpResponse testResponse = requester.get(new URL(Bkjx.BKJX_TEST_API), testRequest, option);

        // 判断响应长度是否为178个字，登录跳转响应长度
        // 这种办法虽然在极端情况下可能会出错（而且还挺蠢的），但是是最快的办法中比较准确的了
        return testResponse.getBody().length != COOKIES_ERROR_RESPONSE_LENGTH;
    }

}
