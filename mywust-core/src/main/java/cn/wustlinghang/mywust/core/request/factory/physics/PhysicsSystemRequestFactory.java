package cn.wustlinghang.mywust.core.request.factory.physics;

import cn.wustlinghang.mywust.core.api.PhysicsSystemUrls;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.core.util.PageFormExtractor;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class PhysicsSystemRequestFactory extends RequestFactory {
    public static HttpRequest loginIndexRequest() {
        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_LOGIN_INDEX);
    }

    public static HttpRequest loginCookiesRequest(String username, String password, String loginIndexHtml) {
        Map<String, String> params = PageFormExtractor.searchAllParams(loginIndexHtml);
        String viewState = params.get("__VIEWSTATE");

        String queryData = StringUtil.generateQueryString(makeLoginQueryParam(username, password, viewState));

        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_LOGIN_COOKIES_API, queryData);
    }

    public static HttpRequest mainIndexRequest(String cookies) {
        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_MAIN_INDEX_URL, null, cookies);
    }

    public static HttpRequest physicsSystemIndexRequest(String redirect, String cookies) {
        return makeHttpRequest(redirect, null, cookies);
    }

    public static HttpRequest physicsSystemIndexRequest(String cookies) {
        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_SYSTEM_INDEX_URL, null, cookies);
    }

    public static HttpRequest physicsCourseRequest(String cookies) {
        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_COURSE_API, null, cookies);
    }

    public static HttpRequest physicsScoreListRequest(String cookies) {
        return makeHttpRequest(PhysicsSystemUrls.PHYSICS_SCORE_LIST_URL, null, cookies);
    }

    public static HttpRequest physicsScoreRequest(String cookies, String courseId, Map<String, String> params) {
        String data = StringUtil.generateQueryString(makeScoreQueryParam(courseId, params));
        return makeStringDataHttpRequest(PhysicsSystemUrls.PHYSICS_SCORE_URL, data, cookies);
    }

    private static Map<String, String> makeScoreQueryParam(String courseId, Map<String, String> params) {
        Map<String, String> queryParams = new HashMap<>(17);

        String viewState = params.get("__VIEWSTATE");
        String student = params.get("ID_PEE63$hidStudentID");
        // 可能是写死的，但不好说
        String server = params.get("ID_PEE63$hidPG");
        String term = params.get("ID_PEE63$ddlxq");

        queryParams.put("smLabManage", "ID_PEE63$upn140201|" + courseId);
        queryParams.put("__EVENTTARGET", courseId);
        queryParams.put("__EVENTARGUMENT", "");
        queryParams.put("__LASTFOCUS", "");
        queryParams.put("__VIEWSTATE", viewState);
        queryParams.put("__VIEWSTATEGENERATOR", "4B5F03FE");
        queryParams.put("hidRoleType", "");
        queryParams.put("ID_PEE63$hidStudentID", student);
        queryParams.put("ID_PEE63$hidCourseID", "");
        queryParams.put("ID_PEE63$hidColumnNum", "");
        queryParams.put("ID_PEE63$hidPG", server);
        // 这个需要手动补充
        queryParams.put("ID_PEE63$ddlxq", term);
        queryParams.put("__ASYNCPOST", "true");

        return queryParams;
    }

    private static Map<String, String> makeLoginQueryParam(String username, String password, String viewState) {
        Map<String, String> queryParams = new HashMap<>(12);

        queryParams.put("__EVENTTARGET", "");
        queryParams.put("__EVENTARGUMENT", "");
        queryParams.put("__VIEWSTATE", viewState);
        queryParams.put("__VIEWSTATEGENERATOR", "F42971E6");
        queryParams.put("hidUserID", "");
        queryParams.put("hidUserPassWord", "");
        queryParams.put("txtUserID", "请输入职工号或学号");
        queryParams.put("txtUsername", username);
        queryParams.put("txtPassword", password);
        queryParams.put("btnLogin.x", "83");
        queryParams.put("btnLogin.y", "29");
        queryParams.put("cbSaveInfo", "on");

        return queryParams;
    }
}
