package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.xpath.ExamInfoXpath;
import cn.linghang.mywust.model.global.ExamInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
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
            throw new ParseException(html);
        }

        List<ExamInfo> examInfos = new ArrayList<>(rows.size());

        try {
            for (Element row : rows) {
                // 提取出当前行的所有格子
                Elements girds = row.getElementsByTag("td");

                // 如果这行格子数少于6个，即到了“成绩”的那个格子就没了，那就没啥意义了，直接跳过，不理了
                if (girds.size() < 6) {
                    continue;
                }

                ExamInfo examInfo = new ExamInfo();

                // 这段看着震撼，但其实很丑
                examInfo.setId(girds.get(0).text());
                examInfo.setTerm(girds.get(1).text());
                examInfo.setCourseNumber(girds.get(2).text());
                examInfo.setCourseName(girds.get(3).text());
                examInfo.setGroupName(girds.get(4).text());
                examInfo.setScore(girds.get(5).text());
                examInfo.setFlag(girds.get(6).text());
                examInfo.setCredit(girds.get(7).text());
                examInfo.setCourseHours(girds.get(8).text());
                examInfo.setGradePoint(girds.get(9).text());
                examInfo.setEvaluateMethod(girds.get(11).text());
                examInfo.setKind(girds.get(12).text());
                examInfo.setCourseKind(girds.get(13).text());

                examInfos.add(examInfo);
            }
        } catch (Exception e) {
            log.warn("解析成绩页面时发生错误：{}", e.getMessage());
            log.warn("终止解析，返回已解析数据");
        }

        return examInfos;
    }
}
