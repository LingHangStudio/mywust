package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class TrainingPlanPageParser implements Parser<String> {

    @Override
    public String parse(String html) throws ParseException {
        Element trainingPlanElement = Jsoup.parse(html).getElementById("dataList");
        if (trainingPlanElement == null) {
            throw new ParseException("教学方案html解析提取失败，id为dataList的元素不存在");
        }

        return trainingPlanElement.outerHtml();
    }
}
