package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TrainingPlanPageParser implements Parser<String> {

    @Override
    public String parse(String html) throws ParseException {
        Elements trainingPlanElement = Jsoup.parse(html).selectXpath("/html/body/div/div/form[1]");
        if (trainingPlanElement.isEmpty()) {
            throw new ParseException("教学方案html解析提取失败，id为dataList的元素不存在", html);
        }

        // 有极少部分19级的学生培养方案页面错乱，中间某部分会被挪到最上边，直接使用id为dataList的表格提取会导致缺失部分信息
        // 在找到更好的解析处理方式之前，此处不对顺序进行处理，直接原样返回
        return trainingPlanElement.get(0).outerHtml();
    }
}
