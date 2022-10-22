package cn.linghang.mywust.core.service.library;

import cn.linghang.mywust.core.api.Library;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.RequestFactory;

public class LibraryAuthRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(Library.LIBRARY_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest indexRequest() {
        return makeHttpRequest(Library.LIBRARY_INDEX_URL);
    }
}
