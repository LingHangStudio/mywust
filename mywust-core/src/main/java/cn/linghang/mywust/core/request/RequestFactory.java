package cn.linghang.mywust.core.request;

import cn.linghang.mywust.network.entitys.HttpRequest;

import java.net.URL;
import java.util.Map;

public class RequestFactory {
    protected static final HttpRequest DEFAULT_HTTP_REQUEST = new HttpRequest();

    public static HttpRequest makeHttpRequest(String url) {
        return makeHttpRequest(url, null, null);
    }

    public static HttpRequest makeHttpRequest(String url, byte[] data) {
        return makeHttpRequest(url, data, null);
    }

    public static HttpRequest makeHttpRequest(String url, byte[] data, String cookies, Map<String, String> additionalHeaders) {
        return makeHttpRequest(url, data, cookies).addHeaders(additionalHeaders);
    }

    public static HttpRequest makeHttpRequest(String url, byte[] data, String cookies) {
        return HttpRequest.builder()
                .url(makeUrl(url))
                .data(data)
                .cookies(cookies)
                .build()
                .addHeaders(DEFAULT_HTTP_REQUEST.getHeaders());
    }

    public static URL makeUrl(String url) {
        try {
            return new URL(url);
        } catch (Exception e) {
            return null;
        }
    }
}
