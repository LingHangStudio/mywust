package cn.linghang.mywust.core.request.graduate;

import cn.linghang.mywust.captcha.SolvedImageCaptcha;
import cn.linghang.mywust.core.api.Graduate;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GraduateRequestFactory extends RequestFactory {
    public static HttpRequest captchaRequest() {
        return makeHttpRequest(Graduate.GRADUATE_CAPTCHA_API);
    }

    private static final Map<String,String> LOGIN_CONST_PARAMS = new HashMap<>(5);
    static {
        LOGIN_CONST_PARAMS.put("__VIEWSTATE", "/wEPDwUENTM4MWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgIFEl9jdGwwOkltYWdlQnV0dG9uMQUSX2N0bDA6SW1hZ2VCdXR0b24yXnZLY54iSWFQ6B2yKH0EisNqU3/eKWEJPibQUElowzU=");
        LOGIN_CONST_PARAMS.put("__EVENTVALIDATION", "/wEdAAYVkBquZFuFxLpraDgB64v+UDagjadrq+xukJizXKfuf485DjYUnSc4B1y8D5WGXeCaN+cQ7B52HzGj0ueO5HRlbdfASR9MjKgO1uRUmJC5kWf476Bpzok4CsBoBh+4Dc7vLkoP0tXUghu7H8qg++pYHeGok+i2xPFtG5oj0qB2dw==");
        LOGIN_CONST_PARAMS.put("__VIEWSTATEGENERATOR", "496CE0B8");
        LOGIN_CONST_PARAMS.put("_ctl0:ImageButton1.x", "39");
        LOGIN_CONST_PARAMS.put("_ctl0:ImageButton1.y", "10");
    }

    public static HttpRequest loginRequest(String username, String password, SolvedImageCaptcha captcha) {
        Map<String, String> params = new HashMap<>(7);
        params.putAll(LOGIN_CONST_PARAMS);
        params.put("_ctl0:txtusername", username);
        params.put("_ctl0:txtpassword", password);
        params.put("_ctl0:txtyzm", captcha.getResult());

        byte[] requestData = StringUtil.generateQueryString(params).getBytes(StandardCharsets.UTF_8);

        return makeHttpRequest(Graduate.GRADUATE_LOGIN_API, requestData);
    }

    public static HttpRequest studentInfoRequest(String cookie) {
        return makeHttpRequest(Graduate.GRADUATE_STUDENT_INFO_API, null, cookie);
    }

    public static HttpRequest courseTableRequest(String cookie) {
        return makeHttpRequest(Graduate.GRADUATE_COURSE_TABLE_API, null, cookie);
    }

    public static HttpRequest examScoreInfoRequest(String cookie) {
        return makeHttpRequest(Graduate.GRADUATE_SCORE_API, null, cookie);
    }

    public static HttpRequest trainingPlanPageRequest(String cookie) {
        return makeHttpRequest(Graduate.GRADUATE_TRAINING_PLAN_PAGE_API, null, cookie);
    }
}
