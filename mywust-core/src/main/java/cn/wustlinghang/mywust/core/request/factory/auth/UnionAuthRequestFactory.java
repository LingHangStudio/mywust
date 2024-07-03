package cn.wustlinghang.mywust.core.request.factory.auth;

import cn.wustlinghang.mywust.data.auth.UnionAuthLoginTicketRequestParam;
import cn.wustlinghang.mywust.urls.UnionAuthUrls;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UnionAuthRequestFactory extends RequestFactory {
    public static HttpRequest loginTicketRequest(UnionAuthLoginTicketRequestParam unionAuthLoginTicketRequestParam) {
        Map<String, String> requestForm = new HashMap<>(4);
        requestForm.put("username", unionAuthLoginTicketRequestParam.getUsername());
        requestForm.put("password", unionAuthLoginTicketRequestParam.getPassword());
        requestForm.put("service", unionAuthLoginTicketRequestParam.getService());
        requestForm.put("loginType", "");
        requestForm.put("id", unionAuthLoginTicketRequestParam.getId());
        requestForm.put("code", unionAuthLoginTicketRequestParam.getCode());

        String queryString = StringUtil.generateQueryString(requestForm);

        return makeHttpRequest(UnionAuthUrls.UNION_AUTH_API, queryString.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpRequest loginCaptchaRequest(String captchaId) {
        long now = System.currentTimeMillis() / 1000;
        String url = String.format(UnionAuthUrls.UNION_AUTH_CAPTCHA_API, now, captchaId);

        return makeHttpRequest(url);
    }
}
