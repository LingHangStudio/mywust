package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.util.JsoupUtil;
import cn.linghang.mywust.data.global.Score;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UndergradScoreParser implements Parser<List<Score>> {
    private static final Logger log = LoggerFactory.getLogger(UndergradScoreParser.class);

    @Override
    public List<Score> parse(String html) throws ParseException {
        Elements rows = Jsoup.parse(html).selectXpath(UndergradScoreXpath.EXAM_INFO_ROWS_XPATH);
        if (rows.isEmpty()) {
            throw new ParseException(html);
        }

        List<Score> scores = new ArrayList<>(rows.size());

        try {
            for (Element row : rows) {
                // 提取出当前行的所有格子
                Elements girds = row.getElementsByTag("td");

                // 如果这行格子数少于6个，即到了“成绩”的那个格子就没了，那就没啥意义了，直接跳过，不理了
                if (girds.size() < 6) {
                    continue;
                }

                Score score = new Score();

                // 这段看着震撼，但其实很丑
                score.setId(JsoupUtil.getElementText(girds, 0));

                score.setTerm(JsoupUtil.getElementText(girds, 1));
                score.setCourseNumber(JsoupUtil.getElementText(girds, 2));

                score.setCourseName(JsoupUtil.getElementText(girds,3));
                score.setGroupName(JsoupUtil.getElementText(girds, 4));

                score.setScore(JsoupUtil.getElementText(girds, 5));
                score.setFlag(JsoupUtil.getElementText(girds, 6));
                score.setCredit(JsoupUtil.getElementText(girds, 7));
                score.setCourseHours(JsoupUtil.getElementText(girds, 8));
                score.setGradePoint(JsoupUtil.getElementText(girds, 9));

                score.setEvaluateMethod(JsoupUtil.getElementText(girds, 11));
                score.setKind(JsoupUtil.getElementText(girds, 12));
                score.setCourseKind(JsoupUtil.getElementText(girds, 13));

                scores.add(score);
            }
        } catch (Exception e) {
            log.warn("解析成绩页面时发生错误：{}", e.getMessage());
            log.warn("终止解析，返回已解析数据");
        }

        return scores;
    }
}

final class UndergradScoreXpath {
    public static final String EXAM_INFO_ROWS_XPATH = "//*[@id=\"dataList\"]/tbody/tr";
}