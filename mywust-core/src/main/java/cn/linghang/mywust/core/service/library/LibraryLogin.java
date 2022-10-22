package cn.linghang.mywust.core.service.library;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.core.api.Library;
import cn.linghang.mywust.core.api.UnionAuth;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.core.service.undergraduate.BkjxAuthRequestFactory;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryLogin {
    private final Requester requester;

    private final UnionLogin unionLogin;

    public LibraryLogin(Requester requester, UnionLogin unionLogin) {
        this.requester = requester;
        this.unionLogin = unionLogin;
    }

    public String getLibraryLoginCookie(String username, String password, RequestClientOption requestOption) throws BasicException, IOException {
        String serviceTicket = unionLogin.getServiceTicket(username, password, UnionAuth.LIBRARY_SSO_SERVICE, requestOption);

        // 获取登录cookie（session）
        HttpRequest sessionRequest = LibraryAuthRequestFactory.sessionCookieRequest();
        HttpResponse sessionResponse = requester.get(new URL(String.format(Library.LIBRARY_SESSION_COOKIE_API, serviceTicket)), sessionRequest, requestOption);
        String cookies = sessionResponse.getCookies();

        // 请求一次首页
        HttpRequest indexRequest = LibraryAuthRequestFactory.indexRequest();
        indexRequest.setCookies(cookies);
        requester.get(new URL(Library.LIBRARY_INDEX_URL), indexRequest, requestOption);

        return cookies;
    }

    public boolean checkCookie(String cookies) throws IOException {
        RequestClientOption option = RequestClientOption.DEFAULT_OPTION;

        HttpRequest testRequest = LibraryAuthRequestFactory.getDefaultHttpRequest();
        testRequest.setCookies(cookies);
        HttpResponse testResponse = requester.get(new URL(Library.LIBRARY_COOKIE_TEST_URL), testRequest, option);

        // 响应居然是JSON，好评！
        // 但是我们只要看有没有Unauthorized关键字就行了
        // 未认证的话是Unauthorized（如果人家没有抽风改掉的话）
        byte[] responseData = testResponse.getBody();
        return responseData != null && !new String(responseData).equalsIgnoreCase("Unauthorized");
    }
}
