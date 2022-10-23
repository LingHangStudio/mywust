package cn.linghang.mywust.util;

import com.google.common.base.Joiner;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtil {
    /**
     * 将map转换成url请求表单格式的请求字符串（类似于 user=admin&passwd=123456&time=11111 这种）
     *
     * @param queryParams 请求参数
     * @return 生成的表单请求字符串
     */
    public static String generateQueryString(Map<String, String> queryParams) {
        // 自动对value值进行url编码
        queryParams.forEach((k, v) -> queryParams.put(k, URLEncoder.encode(v, StandardCharsets.UTF_8)));

        return Joiner.on('&')
                .useForNull("")
                .withKeyValueSeparator('=')
                .join(queryParams);
    }

    /**
     * 解析服务器相应的Set-Cookie的header值，并拼成能够直接用于Cookie的header值
     *
     * @param cookieHeaders 服务器响应的Set-Cookie
     * @return 解析后可用的Cookie
     */
    public static String parseCookie(List<String> cookieHeaders) {
        if (cookieHeaders == null || cookieHeaders.isEmpty()) {
            return null;
        }

        List<String> allCookies = new ArrayList<>(cookieHeaders.size());
        cookieHeaders.forEach((cookieHeader) -> allCookies.add(cookieHeader.split(";")[0]));

        return Joiner.on(';')
                .skipNulls()
                .join(allCookies);
    }
}
