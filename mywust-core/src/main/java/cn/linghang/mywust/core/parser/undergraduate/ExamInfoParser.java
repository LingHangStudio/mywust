package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.xpath.ExamInfoXpath;
import cn.linghang.mywust.model.undergrade.ExamInfo;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExamInfoParser implements Parser<List<ExamInfo>> {
    private static final Logger log = LoggerFactory.getLogger(ExamInfoParser.class);

    @Override
    public List<ExamInfo> parse(String html) throws ParseException {
        Elements rows = Jsoup.parse(html).selectXpath(ExamInfoXpath.EXAM_INFO_ROWS_XPATH);
        if (rows.isEmpty()) {
            throw new ParseException();
        }

        List<ExamInfo> examInfos = new ArrayList<>(rows.size());

        try {
            for (int i = 1; i < rows.size(); i++) {
                Elements columns = rows.get(i).getElementsByTag("td");

                ExamInfo examInfo = new ExamInfo();

                // 这段看着震撼，但其实很丑
                examInfo.setId(columns.get(0).text());
                examInfo.setTerm(columns.get(1).text());
                examInfo.setCourseNumber(columns.get(2).text());
                examInfo.setCourseName(columns.get(3).text());
                examInfo.setGroupName(columns.get(4).text());
                examInfo.setScore(columns.get(5).text());
                examInfo.setFlag(columns.get(6).text());
                examInfo.setCredit(columns.get(7).text());
                examInfo.setCourseHours(columns.get(8).text());
                examInfo.setGradePoint(columns.get(9).text());
                examInfo.setEvaluateMethod(columns.get(11).text());
                examInfo.setKind(columns.get(12).text());
                examInfo.setCourseKind(columns.get(13).text());

                examInfos.add(examInfo);
            }
        } catch (Exception e) {
            log.warn("解析成绩页面时发生错误：{}", e.getMessage());
            log.warn("终止解析，返回已解析数据");
        }

        return examInfos;
    }
}
