package cn.linghang.mywust.core.request;

import cn.linghang.mywust.core.api.Library;
import cn.linghang.mywust.network.HttpRequest;

public class LibraryRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(Library.LIBRARY_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest indexRequest() {
        return makeHttpRequest(Library.LIBRARY_INDEX_URL);
    }
}
