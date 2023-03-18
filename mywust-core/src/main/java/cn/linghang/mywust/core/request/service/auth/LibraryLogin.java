package cn.linghang.mywust.core.request.service.auth;

import cn.linghang.mywust.core.api.LibraryUrls;
import cn.linghang.mywust.core.api.UnionAuthUrls;
import cn.linghang.mywust.exception.ApiException;
import cn.linghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class LibraryLogin {
    private final Requester requester;

    private final UnionLogin unionLogin;

    public LibraryLogin(Requester requester) {
        this.requester = requester;
        this.unionLogin = new UnionLogin(requester);
    }

    public String getLoginCookie(String username, String password, RequestClientOption requestOption) throws ApiException, IOException {
        // 获取service ticket以进行进一步的登录
        String serviceTicket = unionLogin.getServiceTicket(username, password, UnionAuthUrls.Service.LIBRARY_SSO_SERVICE, requestOption);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = LibraryRequestFactory.sessionCookieRequest(serviceTicket);
        HttpResponse sessionResponse = requester.get(sessionRequest, requestOption);
        String cookies = sessionResponse.getCookies();

        // 请求一次首页
        HttpRequest indexRequest = LibraryRequestFactory.indexRequest();
        indexRequest.setCookies(cookies);
        requester.get(indexRequest, requestOption);

        return cookies;
    }

    public boolean checkCookie(String cookies) throws IOException {
        return this.checkCookie(cookies, null);
    }

    public boolean checkCookie(String cookies, RequestClientOption option) throws IOException {
        HttpRequest testRequest = LibraryRequestFactory.makeHttpRequest(LibraryUrls.LIBRARY_COOKIE_TEST_URL, null, cookies);
        HttpResponse testResponse = requester.get(testRequest, option);

        // cookie不正确的话状态码会是401，而不是200
        return testResponse.getStatusCode() == 200;
    }
}
