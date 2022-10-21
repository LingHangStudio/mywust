package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthRequestFactory {
    public static HttpRequest unionLoginTGTRequest(String username, String password, String service) {
        Map<String, String> requestForm = new HashMap<>(4);
        requestForm.put("username", username);
        requestForm.put("password", password);
        requestForm.put("service", service);
        requestForm.put("loginType", "");

        String queryString = StringUtil.generateQueryString(requestForm);

        HttpRequest request = new HttpRequest();
        request.setData(queryString.getBytes(StandardCharsets.UTF_8));

        return request;
    }

    public static HttpRequest loginTicketRequest(String service) {
        Map<String, String> requestForm = new HashMap<>(1);
        requestForm.put("service", service);

        String queryString = StringUtil.generateQueryString(requestForm);

        HttpRequest request = new HttpRequest();
        request.setData(queryString.getBytes(StandardCharsets.UTF_8));

        return request;
    }

    public static HttpRequest sessionCookieRequest() {
        return DEFAULT_HTTP_REQUEST;
    }

    private static final HttpRequest DEFAULT_HTTP_REQUEST = new HttpRequest();

    public static class Legacy {
        public static HttpRequest dataStringRequest() {
            return DEFAULT_HTTP_REQUEST;
        }

        public static HttpRequest ticketRedirectRequest(String encode) {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("userAccount", "");
            queryParams.put("userPassword", "");
            queryParams.put("encoded", encode);

            String queryString = StringUtil.generateQueryString(queryParams);

            Map<String, String> extendHeaders = new HashMap<>();
            extendHeaders.put("Referer", "http://bkjx.wust.edu.cn/");
            extendHeaders.put("Origin", "http://bkjx.wust.edu.cn");

            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setHeaders(extendHeaders);
            httpRequest.setData(queryString.getBytes(StandardCharsets.UTF_8));

            return httpRequest;
        }
    }
}
