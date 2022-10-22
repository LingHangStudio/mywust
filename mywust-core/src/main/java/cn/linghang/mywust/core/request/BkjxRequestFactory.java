package cn.linghang.mywust.core.request;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BkjxRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(Bkjx.BKJX_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest studentInfoRequest(String cookies) {
        return makeHttpRequest(Bkjx.BKJX_STUDENT_INFO_API, null, cookies);
    }

    public static class Legacy {
        public static HttpRequest dataStringRequest() {
            return makeHttpRequest(Bkjx.Legacy.BKJX_DATA_STRING_API);
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

            return makeHttpRequest(Bkjx.Legacy.BKJX_SESSION_COOKIE_API, queryString.getBytes(StandardCharsets.UTF_8))
                    .addHeaders(extendHeaders);
        }
    }
}
