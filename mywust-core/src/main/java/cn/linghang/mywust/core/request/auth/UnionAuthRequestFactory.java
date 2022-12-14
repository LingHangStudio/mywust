package cn.linghang.mywust.core.request.auth;

import cn.linghang.mywust.core.api.UnionAuth;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UnionAuthRequestFactory extends RequestFactory {
    public static HttpRequest unionLoginTGTRequest(String username, String password, String service) {
        Map<String, String> requestForm = new HashMap<>(4);
        requestForm.put("username", username);
        requestForm.put("password", password);
        requestForm.put("service", service);
        requestForm.put("loginType", "");

        String queryString = StringUtil.generateQueryString(requestForm);

        return makeHttpRequest(UnionAuth.UNION_AUTH_API, queryString.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpRequest loginTicketRequest(String redirectUrl, String service) {
        Map<String, String> requestForm = new HashMap<>(1);
        requestForm.put("service", service);

        String queryString = StringUtil.generateQueryString(requestForm);

        return makeHttpRequest(redirectUrl, queryString.getBytes(StandardCharsets.UTF_8));
    }
}
