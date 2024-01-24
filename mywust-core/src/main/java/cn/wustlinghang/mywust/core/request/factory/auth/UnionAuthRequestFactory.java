package cn.wustlinghang.mywust.core.request.factory.auth;

import cn.wustlinghang.mywust.urls.UnionAuthUrls;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UnionAuthRequestFactory extends RequestFactory {
    public static HttpRequest loginTicketRequest(String username, String password, String service) {
        Map<String, String> requestForm = new HashMap<>(4);
        requestForm.put("username", username);
        requestForm.put("password", password);
        requestForm.put("service", service);
        requestForm.put("loginType", "");

        String queryString = StringUtil.generateQueryString(requestForm);

        return makeHttpRequest(UnionAuthUrls.UNION_AUTH_API, queryString.getBytes(StandardCharsets.UTF_8));
    }
}
