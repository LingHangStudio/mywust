package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.xpath.ExamInfoXpath;
import cn.linghang.mywust.core.util.JsoupUtil;
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
                examInfo.setId(JsoupUtil.getElementContext(girds.get(0)));

                examInfo.setTerm(JsoupUtil.getElementContext(girds.get(1)));
                examInfo.setCourseNumber(JsoupUtil.getElementContext(girds.get(2)));

                examInfo.setCourseName(JsoupUtil.getElementContext(girds.get(3)));
                examInfo.setGroupName(JsoupUtil.getElementContext(girds.get(4)));

                examInfo.setScore(JsoupUtil.getElementContext(girds.get(5)));
                examInfo.setFlag(JsoupUtil.getElementContext(girds.get(6)));
                examInfo.setCredit(JsoupUtil.getElementContext(girds.get(7)));
                examInfo.setCourseHours(JsoupUtil.getElementContext(girds.get(8)));
                examInfo.setGradePoint(JsoupUtil.getElementContext(girds.get(9)));

                examInfo.setEvaluateMethod(JsoupUtil.getElementContext(girds.get(11)));
                examInfo.setKind(JsoupUtil.getElementContext(girds.get(12)));
                examInfo.setCourseKind(JsoupUtil.getElementContext(girds.get(13)));

                examInfos.add(examInfo);
            }
        } catch (Exception e) {
            log.warn("解析成绩页面时发生错误：{}", e.getMessage());
            log.warn("终止解析，返回已解析数据");
        }

        return examInfos;
    }
}
