package cn.linghang.mywust.network;

public class RequestFactory {
    protected static final HttpRequest DEFAULT_HTTP_REQUEST = new HttpRequest();

    public static HttpRequest getDefaultHttpRequest() {
        return DEFAULT_HTTP_REQUEST;
    }
}
