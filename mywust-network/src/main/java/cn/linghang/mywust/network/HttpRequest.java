package cn.linghang.mywust.network;

import lombok.Data;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Data
public class HttpRequest {
    private static final Map<String, String> DEFAULT_HEADERS = initDefaultHeaders();

    private static Map<String, String> initDefaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Accept", "application/json, text/plain, */*");
        defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        defaultHeaders.put("Cache-Control", "no-cache");
        defaultHeaders.put("Connection", "keep-alive");
        defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36 Edg/106.0.1370.47");

        return defaultHeaders;
    }

    private URL url;

    private Map<String, String> headers;

    private String cookies;

    private byte[] data;

    public HttpRequest() {
        headers = new HashMap<>(DEFAULT_HEADERS);
    }

    public HttpRequest addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpRequest addHeaders(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpRequest{");
        sb.append("urlParams=").append(url);
        sb.append(", headers=").append(headers);
        sb.append(", cookies='").append(cookies).append('\'');
        sb.append(", data=");
        if (data == null) sb.append("null");
        else {
            sb.append(new String(data));
        }
        sb.append('}');
        return sb.toString();
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        private final HttpRequest httpRequest;

        private HttpRequestBuilder() {
            httpRequest = new HttpRequest();
        }

        public static HttpRequestBuilder aHttpRequest() {
            return new HttpRequestBuilder();
        }

        public HttpRequestBuilder url(URL url) {
            httpRequest.setUrl(url);
            return this;
        }

        public HttpRequestBuilder headers(Map<String, String> headers) {
            httpRequest.setHeaders(headers);
            return this;
        }

        public HttpRequestBuilder cookies(String cookies) {
            httpRequest.setCookies(cookies);
            return this;
        }

        public HttpRequestBuilder data(byte[] data) {
            httpRequest.setData(data);
            return this;
        }

        public HttpRequest build() {
            return httpRequest;
        }
    }
}
