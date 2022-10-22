package cn.linghang.mywust.core.service.library;

import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.RequestFactory;

public class LibraryAuthRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest() {
        return DEFAULT_HTTP_REQUEST;
    }

    public static HttpRequest indexRequest() {
        return DEFAULT_HTTP_REQUEST;
    }
}
