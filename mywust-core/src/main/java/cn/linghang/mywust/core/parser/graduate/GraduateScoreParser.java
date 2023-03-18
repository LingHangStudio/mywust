package cn.linghang.mywust.core.parser.graduate;

import cn.linghang.mywust.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.data.global.Score;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GraduateScoreParser implements Parser<List<Score>> {
    @Override
    public List<Score> parse(String html) throws ParseException {
        Document document = Jsoup.parse(html);
        Elements scoreElements = document.getElementsByClass("GridViewRowStyle");

        List<Score> scores = new ArrayList<>(scoreElements.size());

        for (Element scoreElement : scoreElements) {
            Elements infoGirds = scoreElement.getElementsByTag("td");
            if (infoGirds.size() < 4) {
                continue;
            }

            scoreElements.removeIf(element -> element.hasClass("pagestopr"));

            Score score = new Score();

            score.setCourseName(infoGirds.get(0).text());
            score.setCredit(infoGirds.get(1).text());
            score.setTerm(infoGirds.get(2).text());
            score.setScore(infoGirds.get(3).text());

            scores.add(score);
        }

        return scores;
    }
}
