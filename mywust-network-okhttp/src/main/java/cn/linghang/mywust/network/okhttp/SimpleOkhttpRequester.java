package cn.linghang.mywust.network.okhttp;

import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.util.StringUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>使用Okhttp的Requester，简单的实现了mywust的需求，能应付普通的使用</p>
 * <p>可以设置OkhttpClient是否使用单例模式以节省部分内存和对象分配的消耗，默认不使用（多例模式）</p>
 * <p>按照Okhttp官方的说法以及其他人的测试，使用多例Okhttp对性能消耗其实并不是很大，可以忽略，在多例模式下性能可能也比httpclient要好</p>
 * <p>但是如果每次请求的代理或者超时等设置有动态需求的话<b>只能使用多例模式</b>，因为单例模式下OkhttpClient的设置是全局一致的，无法定制选项</p>
 * <p>详见：<a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/">官方OkHttpClient文档</a></p>
 * <p>StackOverflow上也有人讨论过：<a href="https://stackoverflow.com/questions/42955571/how-to-set-proxy-for-each-request-in-okhttp">How to set proxy for each request in OkHttp?</a></p>
 * <p>注意，这里的“多例”并不是每次都new一个builder来build，这种情况每个client都有一个独立的连接池和线程，多了会导致oom，因此如果想要“多例”，应该是rootClient.newBuild()而不是new Builder().build()</p>
 * <p>总之看文档就知道了</p>
 * <br>
 * <p>如果有更加高级的需求实现，也可以自己实现Requester接口</p>
 *
 * @author lensfrex
 * @create 2022-10-15 09:49
 * @edit 2023-01-21 15:35
 */
public class SimpleOkhttpRequester implements Requester {
    private static final Logger log = LoggerFactory.getLogger(SimpleOkhttpRequester.class);

    private final boolean useSingletonClient;

    private static volatile OkHttpClient rootClient;

    /**
     * 默认的，多例模式的SimpleOkhttpRequester构造方法
     */
    public SimpleOkhttpRequester() {
        this(false);
    }

    /**
     * 指定是否使用单例模式的SimpleOkhttpRequester构造方法
     *
     * @param useSingletonClient 是否使用单例模式
     */
    public SimpleOkhttpRequester(boolean useSingletonClient) {
        this.useSingletonClient = useSingletonClient;
        this.setRootClient();
    }

    /**
     * 使用特定okhttpClient对象作为rootClient，仅在rootClient为null时起作用，并指定是否使用单例模式的SimpleOkhttpRequester私有构造方法
     *
     * @param okHttpClient       okhttpClient对象
     * @param useSingletonClient 是否使用单例模式
     */
    private SimpleOkhttpRequester(OkHttpClient okHttpClient, boolean useSingletonClient) {
        this.useSingletonClient = useSingletonClient;
        this.setRootClient(okHttpClient);
    }

    /**
     * 指定client参数选项的SimpleOkhttpRequester构造方法，仅在rootClient为null时起作用，默认不使用单例模式
     *
     * @param requestClientOption client参数选项，为null时使用默认选项
     */
    public SimpleOkhttpRequester(RequestClientOption requestClientOption) {
        this(requestClientOption, false);
    }

    /**
     * 指定client参数选项的SimpleOkhttpRequester构造方法，仅在rootClient为null时起作用，并且指定是否使用单例模式
     *
     * @param requestClientOption client参数选项，为null时使用默认选项
     * @param useSingletonClient  是否使用单例模式
     */
    public SimpleOkhttpRequester(RequestClientOption requestClientOption, boolean useSingletonClient) {
        this(requestClientOption, useSingletonClient, null);
    }

    /**
     * 指定client参数选项的SimpleOkhttpRequester构造方法，仅在rootClient为null时起作用，并且指定是否使用单例模式以及CookieJar
     *
     * @param requestClientOption client参数选项，为null时使用默认选项
     * @param useSingletonClient  是否使用单例模式
     * @param cookieJar           指定的cookieJar
     */
    public SimpleOkhttpRequester(RequestClientOption requestClientOption, boolean useSingletonClient, CookieJar cookieJar) {
        this.useSingletonClient = useSingletonClient;
        if (rootClient == null) {
            synchronized (SimpleOkhttpRequester.class) {
                if (rootClient == null ) {
                    rootClient = this.buildClient(new OkHttpClient.Builder(), requestClientOption, cookieJar);
                }
            }
        }
    }

    /**
     * 设置指定的client为根client，仅在rootClient为null时设置
     *
     * @param okHttpClient 传入的client
     */
    private void setRootClient(OkHttpClient okHttpClient) {
        if (rootClient == null) {
            synchronized (SimpleOkhttpRequester.class) {
                if (rootClient == null ) {
                    rootClient = okHttpClient;
                }
            }
        }
    }

    /**
     * 设置一个新的根client，只在rootClient为null时设置，保证根client只有一个
     */
    private void setRootClient() {
        if (rootClient == null) {
            synchronized (SimpleOkhttpRequester.class) {
                if (rootClient == null) {
                    rootClient = new OkHttpClient();
                }
            }
        }
    }

    /**
     * 构建一个okhttpClient
     *
     * @param builder             okhttpClient.Builder
     * @param requestClientOption client参数选项，当null时为默认的option
     * @param cookieJar           提供的CookieJar，当为null时不设置
     * @return 构建好的client
     */
    private OkHttpClient buildClient(OkHttpClient.Builder builder, RequestClientOption requestClientOption, CookieJar cookieJar) {
        if (requestClientOption == null) {
            requestClientOption = new RequestClientOption();
        }

        builder.callTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS)
                .connectTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS)
                .followRedirects(requestClientOption.isFallowUrlRedirect())
                .addInterceptor(new RedirectInterceptor());

        // 是否忽略SSL错误
        if (requestClientOption.isIgnoreSSLError()) {
            builder.sslSocketFactory(TrustAllCert.getSSLSocketFactory(), TrustAllCert.getX509TrustManager())
                    .hostnameVerifier(TrustAllCert.getHostnameVerifier());
        }

        // 设置代理
        RequestClientOption.Proxy proxyOption = requestClientOption.getProxy();
        if (proxyOption != null) {
            InetSocketAddress proxyAddress = new InetSocketAddress(proxyOption.getAddress(), proxyOption.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);

            builder.proxy(proxy);
        }

        // 设置CookieJar
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }

        return builder.build();
    }

    /**
     * 获取client，若为单例模式，直接返回rootClient
     *
     * @param requestClientOption client参数选项
     * @return okhttpClient
     */
    private OkHttpClient getOkhttpClient(RequestClientOption requestClientOption) {
        // 单例模式直接使用root client
        if (useSingletonClient) {
            return rootClient;
        }

        return buildClient(rootClient.newBuilder(), requestClientOption, null);
    }

    /**
     * 默认的content-type（form）
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 请求方法
     */
    private enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    /**
     * 根据请求header判断并获取MediaType
     *
     * @param httpRequest 请求
     * @return MediaType
     */
    private MediaType getMediaType(HttpRequest httpRequest) {
        // 按照指定的Content-Type设置MediaType，不指定的话按form处理
        Map<String, String> headers = httpRequest.getHeaders();
        if (headers == null) {
            return MediaType.get(DEFAULT_CONTENT_TYPE);
        }

        String contentType = headers.get("Content-Type");
        if (contentType == null || "".equals(contentType)) {
            return MediaType.get(DEFAULT_CONTENT_TYPE);
        } else {
            return MediaType.get(contentType);
        }
    }

    /**
     * 根据请求信息来构建Request
     *
     * @param requestMethod 请求方法，可选值：GET, POST, PUT, DELETE
     * @param httpRequest   请求体
     * @return Request
     */
    private Request buildRequest(RequestMethod requestMethod, HttpRequest httpRequest) {
        Request.Builder requestBuilder = new Request.Builder().url(httpRequest.getUrl());

        Map<String, String> requestHeaders = httpRequest.getHeaders();
        if (requestHeaders != null) {
            requestHeaders.forEach(requestBuilder::header);
        }

        String requestCookie = httpRequest.getCookies();
        if (requestCookie != null) {
            requestBuilder.header("Cookie", requestCookie);
        }

        byte[] data = httpRequest.getData();
        if (data == null && requestMethod != RequestMethod.GET) {
            data = new byte[]{0};
        }

        if (requestMethod == RequestMethod.GET) {
            requestBuilder.get();

        } else if (requestMethod == RequestMethod.POST) {
            requestBuilder.post(RequestBody.create(this.getMediaType(httpRequest), data));

        } else if (requestMethod == RequestMethod.PUT) {
            requestBuilder.put(RequestBody.create(this.getMediaType(httpRequest), data));

        } else if (requestMethod == RequestMethod.DELETE) {
            if (httpRequest.getData() != null) {
                requestBuilder.delete(RequestBody.create(this.getMediaType(httpRequest), data));
            } else {
                requestBuilder.delete();
            }
        }

        return requestBuilder.build();
    }

    /**
     * 发送请求并获取响应，包装成HttpResponse
     *
     * @param client  okhttpClient
     * @param request 请求
     * @return 包装好的Http响应
     * @throws IOException 如果网络请求有异常
     */
    private HttpResponse sendRequest(OkHttpClient client, Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            HttpResponse httpResponse = new HttpResponse();

            httpResponse.setStatusCode(response.code());

            // 取所有的响应头，如果同一个响应头字段有多个值只拿第一个
            Map<String, List<String>> responseHeaders = response.headers().toMultimap();
            Map<String, String> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            responseHeaders.forEach((k, v) -> headerMap.put(k, v.get(0)));
            httpResponse.setHeaders(headerMap);

            ResponseBody body = response.body();
            if (body != null) {
                // 如果响应太大的话会导致oom，但其实大部分的请求响应都不会大到导致oom，因此直接使用bytes()
                // 如果请求响应特别大的话请用ByteStream，或者自造轮子
                httpResponse.setBody(body.bytes());
            }

            // 提取Cookie
            List<String> cookieHeaders = response.headers("Set-Cookie");
            httpResponse.setCookies(StringUtil.parseCookie(cookieHeaders));

            log.debug("Receive Response: {}", response);
            log.debug("Response Headers: {}", responseHeaders);

            return httpResponse;
        }
    }

    /**
     * 执行请求
     *
     * @param requestMethod       请求方法
     * @param httpRequest         请求体
     * @param requestClientOption 请求响应
     * @return 包装好的Http响应
     * @throws IOException 如果网络请求有异常
     */
    private HttpResponse doRequest(RequestMethod requestMethod, HttpRequest httpRequest, RequestClientOption requestClientOption) throws IOException {
        if (requestClientOption == null) {
            requestClientOption = RequestClientOption.DEFAULT_OPTION;
        }

        OkHttpClient client = getOkhttpClient(requestClientOption);
        Request request = this.buildRequest(requestMethod, httpRequest);

        log.debug("------------Do Request------------");
        log.debug("Request Options: {}", requestClientOption);
        log.debug("Request: {}", request);
        log.debug("Headers: {}", request.headers());

        return this.sendRequest(client, request);
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest, RequestClientOption requestClientOption) throws IOException {
        return this.doRequest(RequestMethod.GET, httpRequest, requestClientOption);
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) throws IOException {
        return this.doRequest(RequestMethod.GET, httpRequest, null);
    }

    @Override
    public HttpResponse post(HttpRequest request, RequestClientOption requestClientOption) throws IOException {
        return this.doRequest(RequestMethod.POST, request, requestClientOption);
    }

    @Override
    public HttpResponse post(HttpRequest request) throws IOException {
        return this.doRequest(RequestMethod.POST, request, null);
    }

    @Override
    public HttpResponse put(HttpRequest request, RequestClientOption requestClientOption) throws IOException {
        return this.doRequest(RequestMethod.PUT, request, requestClientOption);
    }

    @Override
    public HttpResponse put(HttpRequest request) throws IOException {
        return this.doRequest(RequestMethod.PUT, request, null);
    }

    @Override
    public HttpResponse delete(HttpRequest request, RequestClientOption requestClientOption) throws IOException {
        return this.doRequest(RequestMethod.DELETE, request, requestClientOption);
    }

    @Override
    public HttpResponse delete(HttpRequest request) throws IOException {
        return this.doRequest(RequestMethod.DELETE, request, null);
    }

}
