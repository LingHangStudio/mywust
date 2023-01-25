package cn.linghang.mywust.core.request.undergrade;

import cn.linghang.mywust.core.api.UndergradUrls;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.model.global.Campus;
import cn.linghang.mywust.network.entitys.FormBodyBuilder;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class BkjxRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(UndergradUrls.BKJX_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest studentInfoRequest(String cookies) {
        return makeHttpRequest(UndergradUrls.BKJX_STUDENT_INFO_API, null, cookies);
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
        return makeHttpRequest(UndergradUrls.BKJX_SCORE_API, postData, cookies);
    }

    public static HttpRequest trainingPlanPageRequest(String cookies) {
        return makeHttpRequest(UndergradUrls.BKJX_TRAINING_PLAN_API, null, cookies);
    }

    public static HttpRequest creditStatusIndexPageRequest(String cookies) {
        return makeHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_INDEX_API, null, cookies);
    }

    public static HttpRequest creditStatusPageRequest(String cookies, String majorId, String teachPlainId) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(2);
        // “年度专业代码”（猜的）和“教学0301执行计划id”的参数
        formBodyBuilder.add("ndzydm", majorId).add("jx0301zxjhid", teachPlainId);

        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, formBodyBuilder.buildAndToString(), cookies);
    }

    public static HttpRequest creditStatusPageRequest(String cookies, Map<String , String> params) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(2);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            formBodyBuilder.add(key, params.get(key));
        }

        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, formBodyBuilder.buildAndToString(), cookies);
    }

    // 不带参数的学分修读情况获取，正常情况下是待带两个参数的（ndzyd和mjx0301zxjhid）
    // 但是经过实际测试直接post也是可以的，也就是说post什么都是可以的
    public static HttpRequest creditStatusPageRequest(String cookies) {
        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, "", cookies);
    }

    public static HttpRequest courseTablePageRequest(String term, String cookies) {
        Map<String, String> params = new TreeMap<>(FormBodyBuilder.REPEATABLE_COMPARATOR);
        params.putAll(CourseTableRequestParamFactory.COURSE_TABLE_MAGIC_QUERY_PARAMS);
        params.put("xnxq01id", term);

        byte[] queryData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(UndergradUrls.BKJX_COURSE_TABLE_API, queryData, cookies);
    }

    public static HttpRequest examActivityListRequest(String term, String cookie) {
        String url = String.format(UndergradUrls.BKJX_EXAM_ACTIVITY_LIST_API, term);
        return makeHttpRequest(url, null, cookie);
    }

    public static HttpRequest examDelayApplicationListRequest(String term, String activityId, String cookie) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(4);
        formBodyBuilder.add("xnxqid", term);
        formBodyBuilder.add("cj0701id", activityId);
        formBodyBuilder.add("kch", "");
        formBodyBuilder.add("iswfmes", "");

        return makeStringDataHttpRequest(UndergradUrls.BKJX_EXAM_DELAY_APPLICATION_LIST_API, formBodyBuilder.buildAndToString(), cookie);
    }

    public static HttpRequest buildingListRequest(String campus, String cookie) {
        return null;
    }

    public static HttpRequest buildingListRequest(Campus campus, String cookie) {
        return buildingListRequest(campus.id, cookie);
    }

    public static class Legacy {
        public static HttpRequest dataStringRequest() {
            return makeHttpRequest(UndergradUrls.Legacy.BKJX_DATA_STRING_API);
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

            return makeHttpRequest(UndergradUrls.Legacy.BKJX_SESSION_COOKIE_API, queryString.getBytes(StandardCharsets.UTF_8))
                    .addHeaders(extendHeaders);
        }
    }
}
