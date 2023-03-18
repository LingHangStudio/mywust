package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class UndergradCreditStatusIndexParser implements Parser<Map<String, String>> {

    private static final String FROM_PARAM_XPATH = "//*[@id=\"Form1\"]/table/tbody/tr/td[3]";
    @Override
    public Map<String, String> parse(String html) throws ParseException {
        Elements paramElement = Jsoup.parse(html).selectXpath(FROM_PARAM_XPATH);
        if (paramElement.isEmpty()) {
            throw new ParseException("学分修读情况首页解析失败，关键元素不存在...", html);
        }

        Element trimmedElement = paramElement.get(0);
        Elements paramElements = trimmedElement.getElementsByAttribute("name");
        Map<String, String> targetMap = new HashMap<>(2);
        paramElements.forEach(element -> targetMap.put(element.attr("name"), element.attr("value")));

        return targetMap;
    }
}
