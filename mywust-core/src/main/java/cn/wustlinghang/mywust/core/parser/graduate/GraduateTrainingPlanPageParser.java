package cn.wustlinghang.mywust.core.parser.graduate;

import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.util.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GraduateTrainingPlanPageParser implements Parser<String> {

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

            return head + table + foot;
        }catch (Exception e) {
            // 解析失败就直接返回原网页展示
            return html;
        }
    }
}
