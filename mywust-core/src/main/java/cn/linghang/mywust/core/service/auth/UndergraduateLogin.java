package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.api.UndergradUrls;
import cn.linghang.mywust.core.api.UnionAuthUrls;
import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UndergraduateLogin {
    private static final Logger log = LoggerFactory.getLogger(UndergraduateLogin.class);

    private final Requester requester;

    private final UnionLogin unionLogin;

    public UndergraduateLogin(Requester requester) {
        this.requester = requester;
        this.unionLogin = new UnionLogin(requester);
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestOption) throws IOException, ApiException {
        // 获取service ticket以进行进一步的登录
        String serviceTicket = unionLogin.getServiceTicket(username, password, UnionAuthUrls.Service.BKJX_SSO_SERVICE, requestOption);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = BkjxRequestFactory.sessionCookieRequest(serviceTicket);
        HttpResponse sessionResponse = requester.get(sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (checkCookies(cookies, requestOption)) {
            log.warn("[mywust]: Cookie检查不通过：{}", cookies);
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, "登录获取的Cookie无效");
        }

        return cookies;
    }

    /**
     * <p>旧版的登录方式，既相当于直接登录<a href="http://bkjx.wust.edu.cn">bkjx系统</a>，不建议使用</p>
     * <p>注意，这种登录方式的密码和新版可能是不一样的</p>
     * <p>不过不论使用哪种登录方式获取到的cookie都是可用的</p>
     *
     * @return 获取到的Cookies
     */
    @Deprecated
    public String getLoginCookieLegacy(String username, String password, RequestClientOption requestOption) throws IOException, ApiException {
        // 获取某段神秘的dataStr（反正官网代码是这么叫的）
        HttpRequest dataStringRequest = BkjxRequestFactory.Legacy.dataStringRequest();
        HttpResponse dataStringResponse = requester.post(dataStringRequest, requestOption);
        if (dataStringResponse.getBody() == null) {
            log.warn("[mywust]: 本科教学系统旧版登录方式：获取dataStr时发生错误");
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
        }

        String dataString = new String(dataStringResponse.getBody());

        // 获取登录ticket
        String encoded = PasswordEncoder.legacyPassword(username, password, dataString);
        HttpRequest ticketRequest = BkjxRequestFactory.Legacy.ticketRedirectRequest(encoded);
        ticketRequest.setCookies(dataStringResponse.getCookies());

        HttpResponse ticketResponse = requester.post(ticketRequest, requestOption);
        if (ticketResponse.getBody() == null) {
            log.warn("[mywust]: 本科教学系统旧版登录方式：获取登录ticket时发生错误");
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
        }

        // 使用跳转得到的链接获取cookies
        String sessionRedirect = ticketResponse.getHeaders().get("Location");
        if (sessionRedirect == null) {
            throw new ApiException(ApiException.Code.BKJX_LEGACY_LOGIN_PASSWORD_WRONG);
        }

        HttpRequest sessionRequest = BkjxRequestFactory.makeHttpRequest(sessionRedirect);
        HttpResponse sessionResponse = requester.get(sessionRequest, requestOption);

        String cookies = sessionResponse.getCookies();
        if (checkCookies(cookies, requestOption)) {
            log.warn("[mywust]: Cookie检查不通过：{}", cookies);
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, "登录获取的Cookie无效");
        }

        return cookies;
    }

    private boolean roughCheckCookie(String cookies) {
        return cookies == null || !cookies.contains("JSESSIONID") || !cookies.contains("SERVERID");
    }

    private static final int COOKIES_ERROR_RESPONSE_LENGTH =
            ("<script languge='javascript'>window.location.href='https://auth.wust.edu.cn/lyuapServer/login?service=http%3A%2F%2Fbkjx.wust.edu.cn%2Fjsxsd%2Fframework%2FblankPage.jsp'</script>")
                    .length();

    public boolean checkCookies(String cookies, RequestClientOption option) throws IOException {
        HttpRequest testRequest = BkjxRequestFactory.makeHttpRequest(UndergradUrls.BKJX_TEST_API, null, cookies);
        HttpResponse testResponse = requester.get(testRequest, option);

        // 判断响应长度是否为这么多个字，登录跳转响应长度
        // 这种办法虽然在极端情况下可能会出错（而且还挺蠢的），但是是最快的办法中比较准确的了
        return Math.abs(COOKIES_ERROR_RESPONSE_LENGTH - testResponse.getBody().length) > 8;
    }
}
