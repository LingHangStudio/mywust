package cn.wustlinghang.mywust.core.request.factory;

import cn.wustlinghang.mywust.network.entitys.HttpRequest;

import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    public static HttpRequest makeHttpRequest(String url, String data) {
        return makeStringDataHttpRequest(url, data, null);
    }

    public static HttpRequest makeHttpRequest(String url, String data, String cookies, Map<String, String> additionalHeaders) {
        return makeStringDataHttpRequest(url, data, cookies).addHeaders(additionalHeaders);
    }

    private static final byte[] ZERO_BYTE = {0};

    public static HttpRequest makeStringDataHttpRequest(String url, String stringData, String cookies) {
        return HttpRequest.builder()
                .url(makeUrl(url))
                .data(stringData == null ? null : stringData.getBytes(StandardCharsets.UTF_8))
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
