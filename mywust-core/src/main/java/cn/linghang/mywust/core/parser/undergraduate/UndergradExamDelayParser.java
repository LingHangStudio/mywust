package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.util.JsoupUtil;
import cn.linghang.mywust.model.undergrad.ExamDelayApplication;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UndergradExamDelayParser implements Parser<List<ExamDelayApplication>> {

    private static final String rowXpath = "//*[@id=\"Form1\"]/table/tbody/tr";

    @Override
    public List<ExamDelayApplication> parse(String html) throws ParseException {
        Elements rows = Jsoup.parse(html).selectXpath(rowXpath);
        // 列表为空，应该是换ui了，要修改了
        if (rows.isEmpty()) {
            throw new ParseException("解析缓考申请时发生错误：未找到相关匹配元素", html);
        }

        // 只有一行，就是只有表头，不用废话了，直接返回
        if (rows.size() == 1) {
            return new ArrayList<>();
        }

        // i = 1 跳过表头
        List<ExamDelayApplication> applications = new ArrayList<>(rows.size());
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements girds = row.getElementsByTag("td");

            // 成绩标识为空的话说明和缓考没啥关系
            if ("".equals(JsoupUtil.getElementText(girds, 8))) {
                continue;
            }

            ExamDelayApplication application = new ExamDelayApplication();
            application.setTerm(JsoupUtil.getElementText(girds, 1));
            application.setCourseNumber(JsoupUtil.getElementText(girds, 2));
            application.setCourseName(JsoupUtil.getElementText(girds, 3));
            application.setCourseHours(JsoupUtil.getElementText(girds, 4));
            application.setCredit(JsoupUtil.getElementText(girds, 5));
            application.setCourseType(JsoupUtil.getElementText(girds, 6));
            application.setExamType(JsoupUtil.getElementText(girds, 7));
            application.setScoreFlag(JsoupUtil.getElementText(girds, 8));

            application.setDelayReason(JsoupUtil.getElementText(girds, 9));
            application.setStatus(JsoupUtil.getElementText(girds, 10));
            application.setAuditOpinion(JsoupUtil.getElementText(girds, 11));
            application.setTime(JsoupUtil.getElementText(girds, 13));

            String overdueString = JsoupUtil.getElementText(girds, 14);
            application.setOverdue(overdueString.contains("是") || overdueString.contains("已") || overdueString.contains("超"));

            applications.add(application);
        }

        return applications;
    }
}
