package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthRequestGenerator {
    public static HttpRequest unionLoginTGTRequest(String username, String password, String service) {
        Map<String, String> requestForm = new HashMap<>();
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
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("service", service);

        String queryString = StringUtil.generateQueryString(requestForm);

        HttpRequest request = new HttpRequest();
        request.setData(queryString.getBytes(StandardCharsets.UTF_8));

        return request;
    }

    public static HttpRequest sessionCookieRequest() {
        return new HttpRequest();
    }
}
