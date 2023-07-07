package cn.wustlinghang.mywust.core.parser.physics;

import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.util.JsoupUtil;
import cn.wustlinghang.mywust.data.global.Score;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PhysicsScorePageParser implements Parser<List<Score>> {
    private static final String scoreRowXpath = "//*[@id=\"ID_PEE63_gvpeeLabScore\"]/tbody/tr[@style=\"background-color:White;height:25px;\"]";

    @Override
    public List<Score> parse(String html) throws ParseException {
        Elements scoreRows = Jsoup.parse(html).selectXpath(scoreRowXpath);

        List<Score> scores = new ArrayList<>(scoreRows.size());
        for (Element scoreRow : scoreRows) {
            Elements girds = scoreRow.getElementsByTag("td");
            // 某行格子数少于8个，即到了成绩那部分就没了，就跳过
            if (girds.size() < 8) {
                continue;
            }

            // 有用的信息就这么多
            Score score = new Score();
            score.setId(JsoupUtil.getElementText(girds, 0));
            score.setCourseName(JsoupUtil.getElementText(girds, 1));
            score.setGroupName(JsoupUtil.getElementText(girds, 2));
            score.setFlag(JsoupUtil.getElementText(girds, 6));
            score.setScore(JsoupUtil.getElementText(girds, 7));

            scores.add(score);
        }

        return scores;
    }
}
