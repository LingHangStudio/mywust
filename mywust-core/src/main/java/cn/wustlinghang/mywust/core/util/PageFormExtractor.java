package cn.wustlinghang.mywust.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html页面表单参数提取，用于简化部分繁琐的表单数据生成，模拟网页表单数据的请求参数
 */
public class PageFormExtractor {
    public static void extractToMap(String html, String formId, Map<String, String> targetMap) {
        Document page = Jsoup.parse(html);
        Element form = page.getElementById(formId);
        if (form == null) {
            throw new NullPointerException("The Form Doesn't exist.");
        }

        Elements formElements = form.getElementsByAttribute("name");

        for (Element element : formElements) {
            targetMap.put(element.attr("name"), element.attr("value"));
        }
    }

    private static final Pattern formPattern = Pattern.compile("name=\"(?<name>.*?)\"(.*?)value=\"(?<value>.*?)\"");

    public static Map<String, String> searchAllParams(String wholeHtml) {
        Map<String, String> params = new HashMap<>(16);
        Matcher matcher = formPattern.matcher(wholeHtml);
        while (matcher.find()) {
            params.put(matcher.group("name"), matcher.group("value"));
        }

        return params;
    }
}
