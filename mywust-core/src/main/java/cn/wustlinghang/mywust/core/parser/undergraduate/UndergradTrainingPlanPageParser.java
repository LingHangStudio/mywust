package cn.wustlinghang.mywust.core.parser.undergraduate;

import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.exception.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class UndergradTrainingPlanPageParser implements Parser<String> {

    private static final String HTML_WRAPPER = "<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\">\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "  <title>校历</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "%s" +
            "</body>\n" +
            "\n" +
            "</html>";

    @Override
    public String parse(String html) throws ParseException {
        return this.parse(html, true);
    }

    public String parse(String html, boolean wrapHtml) throws ParseException {
        Elements trainingPlanElement = Jsoup.parse(html).selectXpath("/html/body/div/div/form[1]");
        if (trainingPlanElement.isEmpty()) {
            throw new ParseException("教学方案html解析提取失败，id为dataList的元素不存在", html);
        }

        // 有极少部分19级的学生培养方案页面错乱，中间某部分会被挪到最上边，直接使用id为dataList的表格提取会导致缺失部分信息
        // 在找到更好的解析处理方式之前，此处不对顺序进行处理，直接原样返回
        String contentHtml = trainingPlanElement.get(0).outerHtml();

        return wrapHtml ? String.format(HTML_WRAPPER, contentHtml) : contentHtml;
    }
}
