package cn.wustlinghang.mywust.core.request.factory.graduate;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.core.api.GraduateUrls;
import cn.wustlinghang.mywust.core.request.factory.RequestFactory;
import cn.wustlinghang.mywust.core.util.PageFormExtractor;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.util.StringUtil;
import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GraduateRequestFactory extends RequestFactory {
    public static HttpRequest captchaRequest() {
        return makeHttpRequest(GraduateUrls.GRADUATE_CAPTCHA_API);
    }

    public static HttpRequest captchaRequest(String cookie) {
        return makeHttpRequest(GraduateUrls.GRADUATE_CAPTCHA_API, null, cookie);
    }

    private static final Map<String, String> LOGIN_CONST_PARAMS = new HashMap<>(5);

    static {
        LOGIN_CONST_PARAMS.put("__VIEWSTATE", "/wEPDwUENTM4MWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgIFEl9jdGwwOkltYWdlQnV0dG9uMQUSX2N0bDA6SW1hZ2VCdXR0b24yocdtCYu4PMj471MTbP5cXU8bY4gtaO8szHfQ9BzPZdU=");
        LOGIN_CONST_PARAMS.put("__EVENTVALIDATION", "/wEdAAZILdedbS+/UZ9vGGceLDGGUDagjadrq+xukJizXKfuf485DjYUnSc4B1y8D5WGXeCaN+cQ7B52HzGj0ueO5HRlbdfASR9MjKgO1uRUmJC5kWf476Bpzok4CsBoBh+4Dc7g7ZIrePIEkbgMpk1NAfZEXHl1KcVqFDpYCaSt9iZO5w==");
        LOGIN_CONST_PARAMS.put("__VIEWSTATEGENERATOR", "496CE0B8");
        LOGIN_CONST_PARAMS.put("_ctl0:ImageButton1.x", "39");
        LOGIN_CONST_PARAMS.put("_ctl0:ImageButton1.y", "10");
    }

    @Deprecated
    public static HttpRequest loginRequest(String username, String password, SolvedImageCaptcha captcha) {
        Map<String, String> params = new HashMap<>(7);
        params.putAll(LOGIN_CONST_PARAMS);
        params.put("_ctl0:txtusername", username);
        params.put("_ctl0:txtpassword", password);
        params.put("_ctl0:txtyzm", captcha.getResult());

        byte[] requestData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(GraduateUrls.GRADUATE_LOGIN_API, requestData, captcha.getBindInfo());
    }

    @Data
    @Builder
    public static class LoginPageParams {
        private final String viewState;
        private final String eventValidation;
        private final String vewStateGenerator;
    }

    public static HttpRequest loginRequest(String username, String password, LoginPageParams loginPageParams, SolvedImageCaptcha captcha) {
        Map<String, String> params = new HashMap<>(7);
        params.putAll(LOGIN_CONST_PARAMS);

        params.put("__VIEWSTATE", loginPageParams.viewState);
        params.put("__EVENTVALIDATION", loginPageParams.eventValidation);
        params.put("__VIEWSTATEGENERATOR", loginPageParams.vewStateGenerator);
        params.put("_ctl0:txtusername", username);
        params.put("_ctl0:txtpassword", password);
        params.put("_ctl0:txtyzm", captcha.getResult());

        byte[] requestData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(GraduateUrls.GRADUATE_LOGIN_API, requestData, captcha.getBindInfo());
    }

    public static HttpRequest loginRequest(String username, String password, String loginPage, SolvedImageCaptcha captcha) {
        Map<String, String> params = new HashMap<>(7);
        params.putAll(LOGIN_CONST_PARAMS);

        Map<String, String> form = PageFormExtractor.searchAllParams(loginPage);

        params.put("__VIEWSTATE", form.get("__VIEWSTATE"));
        params.put("__EVENTVALIDATION", form.get("__EVENTVALIDATION"));
        params.put("__VIEWSTATEGENERATOR", form.get("__VIEWSTATEGENERATOR"));
        params.put("_ctl0:txtusername", username);
        params.put("_ctl0:txtpassword", password);
        params.put("_ctl0:txtyzm", captcha.getResult());

        byte[] requestData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(GraduateUrls.GRADUATE_LOGIN_API, requestData, captcha.getBindInfo());
    }

    public static HttpRequest studentInfoRequest(String cookie) {
        return makeHttpRequest(GraduateUrls.GRADUATE_STUDENT_INFO_API, null, cookie);
    }

    public static HttpRequest courseTableRequest(String cookie) {
        return makeHttpRequest(GraduateUrls.GRADUATE_COURSE_TABLE_API, null, cookie);
    }

    public static HttpRequest examScoreInfoRequest(String cookie) {
        return makeHttpRequest(GraduateUrls.GRADUATE_SCORE_API, null, cookie);
    }

    public static HttpRequest trainingPlanPageRequest(String cookie) {
        return makeHttpRequest(GraduateUrls.GRADUATE_TRAINING_PLAN_PAGE_API, null, cookie);
    }
}
