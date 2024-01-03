package cn.wustlinghang.mywust.core.request.factory.undergrade;

import cn.wustlinghang.mywust.urls.UndergradUrls;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.data.common.Campus;
import cn.wustlinghang.mywust.network.entitys.FormBodyBuilder;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BkjxRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(UndergradUrls.BKJX_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest studentInfoRequest(String cookies) {
        return makeHttpRequest(UndergradUrls.BKJX_STUDENT_INFO_API, null, cookies);
    }

    public static HttpRequest examScoreInfoRequest(String cookies, String time, String courseKind, String courseName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(4);
        // 开课时间(学期)
        formBodyBuilder.add("kksj", time);

        // 课程性质
        formBodyBuilder.add("kcxz", courseKind);

        // 课程名称
        formBodyBuilder.add("kcmc", courseName);

        // 显示方式，这里直接选择全部显示
        formBodyBuilder.add("xsfs", "all");

        byte[] postData = formBodyBuilder.buildString().getBytes(StandardCharsets.UTF_8);
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

        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, formBodyBuilder.buildString(), cookies);
    }

    public static HttpRequest creditStatusPageRequest(String cookies, Map<String, String> params) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(2);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            formBodyBuilder.add(key, params.get(key));
        }

        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, formBodyBuilder.buildString(), cookies);
    }

    // 不带参数的学分修读情况获取，正常情况下是待带两个参数的（ndzyd和mjx0301zxjhid）
    // 但是经过实际测试直接post也是可以的，也就是说post什么都是可以的
    public static HttpRequest creditStatusPageRequest(String cookies) {
        return makeStringDataHttpRequest(UndergradUrls.BKJX_CREDIT_STATUS_API, "", cookies);
    }

    public static HttpRequest courseTablePageRequest(String term, String cookies) {
        Map<String, String> params = new HashMap<>(7);
        params.put("jx0404id", "");
        params.put("cj0701id", "");
        params.put("zc", "");
        params.put("demo", "");
        params.put("sfFD", "1");
        params.put("kbjcmsid", "9486203B90F3E3CBE0532914A8C03BE2");
        params.put("xnxq01id", term);

        byte[] queryData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(UndergradUrls.BKJX_COURSE_TABLE_API, queryData, cookies);
    }

    public static HttpRequest singleWeekCoursePageRequest(String date, String cookies) {
        Map<String, String> params = new HashMap<>(7);
        params.put("rq", date);

        byte[] queryData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(UndergradUrls.BKJX_SINGLE_WEEK_COURSE_TABLE_API, queryData, cookies);
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

        return makeStringDataHttpRequest(UndergradUrls.BKJX_EXAM_DELAY_APPLICATION_LIST_API, formBodyBuilder.buildString(), cookie);
    }

    public static HttpRequest buildingListRequest(String campus, String cookie) {
        FormBodyBuilder builder = new FormBodyBuilder(1);
        builder.add("xqid", campus);
        return makeStringDataHttpRequest(UndergradUrls.BKJX_BUILDING_LIST_API, builder.buildString(), cookie);
    }

    public static HttpRequest buildingListRequest(Campus campus, String cookie) {
        return buildingListRequest(campus.id, cookie);
    }

    public static class Legacy {
        public static HttpRequest dataStringRequest() {
            return makeHttpRequest(UndergradUrls.Legacy.BKJX_DATA_STRING_API);
        }

        public static HttpRequest ticketRedirectRequest(String encode, String cookies) {
            Map<String, String> queryParams = new HashMap<>(4);
            queryParams.put("userAccount", "");
            queryParams.put("userPassword", "");
            queryParams.put("encoded", encode);

            String queryString = StringUtil.generateQueryString(queryParams);

            Map<String, String> extendHeaders = new HashMap<>(2);
            extendHeaders.put("Referer", "http://bkjx.wust.edu.cn/");
            extendHeaders.put("Origin", "http://bkjx.wust.edu.cn");

            return makeHttpRequest(UndergradUrls.Legacy.BKJX_SESSION_COOKIE_API, queryString.getBytes(StandardCharsets.UTF_8), cookies)
                    .addHeaders(extendHeaders);
        }
    }
}
