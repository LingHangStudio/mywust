package cn.linghang.mywust.core.parser.postgraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.util.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GraduateTrainingPlanPageParser implements Parser<String> {

    private static final String HTML_PREFIX = "<!DOCTYPE html><html lang=\"zh\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>培养计划</title></head><body>";
    private static final String HTML_SUFFIX = "</body></html>";

    @Override
    public String parse(String html) throws ParseException {
        try {
            Document document = Jsoup.parse(html);
            Element fullTable = document.getElementById("Fin_Context");
            if (fullTable == null) {
                throw new ParseException("解析研究生培养计划失败，元素Fin_Context不存在", html);
            }

            String head = fullTable.getElementsByClass("pagestopl").get(0).outerHtml();
            String table = JsoupUtil.getOuterHtml(fullTable.getElementById("_ctl0_MainWork_dgData"));
            String foot = JsoupUtil.getOuterHtml(fullTable.getElementById("Table4"));

            return HTML_PREFIX + head + table + foot + HTML_SUFFIX;
        }catch (Exception e) {
            // 解析失败就直接返回原网页展示
            return html;
        }
    }
}
