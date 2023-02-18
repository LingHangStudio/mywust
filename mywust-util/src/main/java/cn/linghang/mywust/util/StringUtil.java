package cn.linghang.mywust.util;

import cn.hutool.core.util.URLUtil;
import com.google.common.base.Joiner;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StringUtil {
    private static final RepeatableComparator REPEATABLE_COMPARATOR = new RepeatableComparator();

    /**
     * 将map转换成url请求表单格式的请求字符串（类似于 user=admin&passwd=123456&time=11111 这种）
     *
     * @param queryParams 请求参数
     * @return 生成的表单请求字符串
     */
    public static String generateQueryString(Map<String, String> queryParams) {
        // 自动对value值进行url编码
        Map<String, String> urlEncodedQueryParams = new TreeMap<>(REPEATABLE_COMPARATOR);
        queryParams.forEach((k, v) -> urlEncodedQueryParams.put(URLUtil.encodeAll(k), URLUtil.encodeAll(v)));

        return Joiner.on('&')
                .useForNull("")
                .withKeyValueSeparator('=')
                .join(urlEncodedQueryParams);
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

    /**
     * 生成参数签名
     *
     * @param appId     appId
     * @param secretKey secretKey
     * @return 生成得到的签名sign字段
     */
    public static String generateSignText(String appId, String secretKey, Map<String, String> params) {
        StringBuilder rawText = new StringBuilder(appId + secretKey);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            rawText
                    .append(key)
                    .append('=')
                    .append(params.get(key));
        }

        return Base64.encodeBase64String(rawText.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static String NoneNullString(String str) {
        return str == null ? "" : str;
    }

    public static String getTermString(Date date, boolean autumn) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return getTermString(calendar, autumn);
    }

    public static String getTermString(Calendar calendar, boolean autumn) {
        int year = calendar.get(Calendar.YEAR);
        int nextYear = year + 1;

        // 秋季期，就是第一个学期
        return String.format("%d-%d-%s", year, nextYear, autumn ? "1" : "2");
    }

    public static String getCurrentTermString() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);

        // 一般八月到第二年二月算是是秋季期
        return getTermString(now, month >= 8 || month < 2);
    }

    static public List<String> split(String source, char gap) {
        List<String> result = new ArrayList<>(4);
        char[] sourceChars = source.toCharArray();

        int startIndex = 0;
        for (int index = -1; ++index != sourceChars.length; ) {
            if (sourceChars[index] != gap) {
                continue;
            }
            result.add(source.substring(startIndex, index));
            startIndex = index + 1;
        }

        result.add(source.substring(startIndex, sourceChars.length));

        return result;
    }
}
