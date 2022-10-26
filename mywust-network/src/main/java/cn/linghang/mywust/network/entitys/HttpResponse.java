package cn.linghang.mywust.network.entitys;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResponse {
    public static final int HTTP_OK = 200;
    public static final int HTTP_RESOURCE_CREATED = 201;

    public static final int HTTP_REDIRECT_301 = 301;
    public static final int HTTP_REDIRECT_302 = 302;

    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;

    public static final int HTTP_SERVER_ERROR = 500;
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;

    private int statusCode;

    private Map<String, String> headers;

    private String cookies;

    private byte[] body;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpResponse{");
        sb.append("headers=").append(headers);
        sb.append(", cookies='").append(cookies).append('\'');
        sb.append(", body=");
        if (body == null) sb.append("null");
        else {
            sb.append(new String(body));
        }
        sb.append('}');
        return sb.toString();
    }

    public static HttpResponseBuilder builder() {
        return new HttpResponseBuilder();
    }

    public static final class HttpResponseBuilder {
        private final HttpResponse httpResponse;

        private HttpResponseBuilder() {
            httpResponse = new HttpResponse();
        }

        public static HttpResponseBuilder aHttpResponse() {
            return new HttpResponseBuilder();
        }

        public HttpResponseBuilder headers(Map<String, String> headers) {
            httpResponse.setHeaders(headers);
            return this;
        }

        public HttpResponseBuilder cookies(String cookies) {
            httpResponse.setCookies(cookies);
            return this;
        }

        public HttpResponseBuilder body(byte[] body) {
            httpResponse.setBody(body);
            return this;
        }

        public HttpResponse build() {
            return httpResponse;
        }
    }
}
