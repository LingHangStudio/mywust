package cn.linghang.mywust.core.request.undergrade;

import cn.linghang.mywust.core.api.Bkjx;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.FormBodyBuilder;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class BkjxRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(Bkjx.BKJX_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest studentInfoRequest(String cookies) {
        return makeHttpRequest(Bkjx.BKJX_STUDENT_INFO_API, null, cookies);
    }

    public static HttpRequest examScoreInfoRequest(String cookies, String time, String courseKind, String courseName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder();
        // 开课时间(学期)
        formBodyBuilder.add("kksj", time);

        // 课程性质
        formBodyBuilder.add("kcxz", courseKind);

        // 课程名称
        formBodyBuilder.add("kcmc", courseName);

        // 显示方式，这里直接选择全部显示
        formBodyBuilder.add("xsfs", "all");

        byte[] postData = formBodyBuilder.buildAndToString().getBytes(StandardCharsets.UTF_8);
        return makeHttpRequest(Bkjx.BKJX_EXAM_INFO_API, postData, cookies);
    }

    public static HttpRequest trainingPlanPageRequest(String cookies) {
        return makeHttpRequest(Bkjx.BKJX_SCHEME_API, null, cookies);
    }

    public static HttpRequest courseTablePageRequest(String term, String cookies) {
        Map<String, String> params = new TreeMap<>(FormBodyBuilder.REPEATABLE_COMPARATOR);
        params.putAll(CourseTableRequestParamFactory.COURSE_TABLE_MAGIC_QUERY_PARAMS);
        params.put("xnxq01id", term);

        byte[] queryData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(Bkjx.BKJX_COURSE_TABLE_API, queryData, cookies);
    }
}
