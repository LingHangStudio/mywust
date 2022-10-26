package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class SchemePageParser implements Parser<String> {

    @Override
    public String parse(String html) throws ParseException {
        Element schemeElement = Jsoup.parse(html).getElementById("dataList");
        if (schemeElement == null) {
            throw new ParseException("教学方案html解析提取失败，id为dataList的元素不存在");
        }

        return schemeElement.outerHtml();
    }
}
