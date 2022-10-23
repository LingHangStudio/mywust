package cn.linghang.mywust.core.request;

import cn.linghang.mywust.core.api.PhysicsSystem;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PhysicsSystemRequestFactory extends RequestFactory {
    public static HttpRequest loginIndexRequest() {
        return makeHttpRequest(PhysicsSystem.PHYSICS_LOGIN_INDEX);
    }

    public static HttpRequest loginCookiesRequest(String username, String password, String cookies) {
        byte[] queryData = StringUtil.generateQueryString(makeLoginQueryParam(username, password)).getBytes(StandardCharsets.UTF_8);
        return makeHttpRequest(PhysicsSystem.PHYSICS_LOGIN_COOKIES_API, queryData, cookies);
    }

    public static HttpRequest mainIndexRequest(String cookies) {
        return makeHttpRequest(PhysicsSystem.PHYSICS_MAIN_INDEX_URL, null, cookies);
    }

    public static HttpRequest physicsSystemIndexRequest(String redirect, String cookies) {
        return makeHttpRequest(redirect, null, cookies);
    }

    private static Map<String, String> makeLoginQueryParam(String username, String password) {
        // 这几个算是是写死了的，也能用
        // 但其实最好还是从首页动态获取某些关键字段（"__VIEWSTATE"）
        // 这种办法等后面有时间了再实现
        Map<String, String> queryParams = new HashMap<>(12);
        queryParams.put("__EVENTTARGET", "");
        queryParams.put("__EVENTARGUMENT", "");
        queryParams.put("__VIEWSTATE", VIEW_STATE);
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

    private static final String VIEW_STATE =
            "/wEPDwULLTEwNTgzMzY4NTgPZBYCZg9kFgICCQ9kFgJmD2QWBgINDw9kFgIeBVN0" +
                    "eWxlBZsBbGluZS1oZWlnaHQ6NThweDtwYWRkaW5nLWxlZnQ6NDVweDtwYWRkaW5n" +
                    "LXRvcDowcHg7d2lkdGg6MzE1cHg7aGVpZ2h0OjU0cHg7YmFja2dyb3VuZDp1cmwo" +
                    "Li4vLi4vUmVzb3VyY2UvQmFzaWNJbmZvL25ld0JHL+eUqOaIt+WQjS5wbmcpIG5v" +
                    "LXJlcGVhdCAwcHggMHB4OztkAg8PD2QWAh8ABZsBbGluZS1oZWlnaHQ6NThweDtw" +
                    "YWRkaW5nLWxlZnQ6NDVweDtwYWRkaW5nLXRvcDowcHg7d2lkdGg6MzE1cHg7aGVp" +
                    "Z2h0OjU0cHg7YmFja2dyb3VuZDp1cmwoLi4vLi4vUmVzb3VyY2UvQmFzaWNJbmZv" +
                    "L25ld0JHL+Wvhueggeepui5wbmcpIG5vLXJlcGVhdCAwcHggMHB4OztkAhkPDxYC" +
                    "HgRUZXh0BRvns7vnu5/orr/pl67kurrmrKHvvJozNjc2MDJkZBgBBR5fX0NvbnRy" +
                    "b2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WBQULaW1nQnRuQ2xvc2UFCGxidGJTYXZl" +
                    "BQpsYnRuQ2FuY2VsBQhidG5Mb2dpbgUKY2JTYXZlSW5mb6K8UayJuWe2OSRVqLDo" +
                    "2J4wMKAT";
}
