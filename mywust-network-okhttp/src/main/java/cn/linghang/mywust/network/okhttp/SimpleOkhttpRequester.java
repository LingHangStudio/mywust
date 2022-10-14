package cn.linghang.mywust.network.okhttp;

import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>使用Okhttp的Requester，简单的实现了mywust的需求，能应付普通的使用</p>
 * <p>可以设置OkhttpClient是否使用单例模式以节省部分内存和对象分配的消耗，默认不使用（多例模式）</p>
 * <p>按照Okhttp官方的说法以及其他人的测试，使用多例Okhttp对性能消耗其实并不是很大，可以忽略，在多例模式下性能可能也比httpclient要好</p>
 * <p>但是如果每次请求的代理或者超时等设置有动态需求的话只能使用多例模式，单例模式下OkhttpClient的设置是全局一致的</p>
 * <br>
 * <p>如果有更加高级的需求实现，也可以自己实现Requester接口</p>
 *
 * @author lensfrex
 * @create 2022-10-15 09:49
 * @edit 2022-10-15 09:49
 */
public class SimpleOkhttpRequester implements Requester {
    private final boolean singletonClient;

    public SimpleOkhttpRequester() {
        singletonClient = false;
    }

    public SimpleOkhttpRequester(boolean singletonClient) {
        this.singletonClient = singletonClient;
    }

    private static volatile OkHttpClient singletonOkhttp;

    private static OkHttpClient newOkhttpClient(RequestClientOption requestClientOption) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS)
                .connectTimeout(requestClientOption.getTimeout(), TimeUnit.SECONDS);

        // 设置代理
        RequestClientOption.Proxy proxyOption = requestClientOption.getProxy();
        if (proxyOption != null) {
            InetSocketAddress proxyAddress = new InetSocketAddress(proxyOption.getAddress(), proxyOption.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);

            builder.proxy(proxy);
        }

        return builder.build();
    }

    private static OkHttpClient getSingletonClientInstant(RequestClientOption options) {
        if (singletonOkhttp == null) {
            synchronized (SimpleOkhttpRequester.class) {
                if (singletonOkhttp == null) {
                    singletonOkhttp = newOkhttpClient(options);
                }
            }
        }

        return singletonOkhttp;
    }

    private OkHttpClient getOkhttpClient(RequestClientOption options) {
        if (singletonClient) {
            return getSingletonClientInstant(options);
        } else {
            return newOkhttpClient(options);
        }
    }

    private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public enum RequestMethod {
        GET, POST
    }

    private Request buildRequest(RequestMethod requestMethod, URL url, HttpRequest httpRequest) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        Map<String, String> requestHeaders = httpRequest.getHeaders();
        requestHeaders.forEach(requestBuilder::header);

        requestBuilder.header("Cookie", httpRequest.getCookies());

        if (requestMethod == RequestMethod.GET) {
            requestBuilder.get();
        } else if (requestMethod == RequestMethod.POST) {
            // 按照指定的Content-Type设置MediaType，不指定的话按form处理
            String contentType = httpRequest.getHeaders().get("Content-Type");
            MediaType mediaType;
            if (!"".equals(contentType)) {
                mediaType = MediaType.get(contentType);
            } else {
                mediaType = MediaType.get(DEFAULT_CONTENT_TYPE);
            }

            requestBuilder.post(RequestBody.create(mediaType, httpRequest.getData()));
        }

        return requestBuilder.build();
    }

    private HttpResponse sendRequest(OkHttpClient client, Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            HttpResponse httpResponse = new HttpResponse();

            // 取所有的响应头，如果同一个响应头字段有多个值只拿第一个
            Map<String, List<String>> responseHeaders = response.headers().toMultimap();
            Map<String, String> headerMap = new HashMap<>();
            responseHeaders.forEach((k, v) -> headerMap.put(k, v.get(0)));
            httpResponse.setHeaders(headerMap);

            ResponseBody body = response.body();
            if (body != null) {
                // 如果响应太大的话会导致oom，但其实大部分的请求都不会oom，因此直接使用bytes()
                // 如果请求响应特别大的话请用ByteStream
                httpResponse.setBody(body.bytes());
            }

            httpResponse.setCookies(response.header("Set-Cookie"));
        }

        return null;
    }

    private HttpResponse doRequest(RequestMethod requestMethod, URL url, HttpRequest httpRequest, RequestClientOption requestClientOption) throws IOException {
        OkHttpClient client = this.getOkhttpClient(requestClientOption);
        Request request = this.buildRequest(requestMethod, url, httpRequest);

        return this.sendRequest(client, request);
    }

    @Override
    public HttpResponse get(URL url, HttpRequest httpRequest, RequestClientOption requestClientOption) throws IOException {
        return this.doRequest(RequestMethod.GET, url, httpRequest, requestClientOption);
    }

    @Override
    public HttpResponse post(URL url, HttpRequest request, RequestClientOption options) throws IOException {
        return this.doRequest(RequestMethod.POST, url, request, options);
    }

    @Override
    public HttpResponse postJson(URL url, HttpRequest request, Object requestBody, RequestClientOption options) throws IOException {
        String json = new ObjectMapper().writeValueAsString(requestBody);
        request.setData(json.getBytes(StandardCharsets.UTF_8));
        request.getHeaders().put("Content-Type", JSON_CONTENT_TYPE);

        return this.doRequest(RequestMethod.POST, url, request, options);
    }
}
