package cn.linghang.mywust.core.request;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.network.entitys.FormBodyBuilder;
import cn.linghang.mywust.network.entitys.HttpRequest;
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

    public static HttpRequest examScoreInfoRequest(String cookies, String time, String courseKind, String courseName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder();
        // 开课时间
        formBodyBuilder.addQueryParam("kksj", time);

        // 课程性质
        formBodyBuilder.addQueryParam("kcxz", courseKind);

        // 课程名称
        formBodyBuilder.addQueryParam("kcmc", courseName);

        // 显示方式，这里直接选择全部显示
        formBodyBuilder.addQueryParam("xsfs", "all");

        byte[] postData = formBodyBuilder.buildAndToString().getBytes(StandardCharsets.UTF_8);
        return makeHttpRequest(Bkjx.BKJX_EXAM_INFO_API, postData, cookies);
    }

    public static HttpRequest schemePageRequest(String cookies) {
        return makeHttpRequest(Bkjx.BKJX_SCHEME_API, null, cookies);
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
