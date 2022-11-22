package cn.linghang.mywust.core.parser.postgraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.model.global.ExamInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GraduateExamInfoParser implements Parser<List<ExamInfo>> {
    @Override
    public List<ExamInfo> parse(String html) throws ParseException {
        Document document = Jsoup.parse(html);
        Elements scoreElements = document.getElementsByClass("GridViewRowStyle");

        List<ExamInfo> examInfos = new ArrayList<>(scoreElements.size());

        for (Element scoreElement : scoreElements) {
            Elements infoGirds = scoreElement.getElementsByTag("td");
            if (infoGirds.size() < 4) {
                continue;
            }

            ExamInfo examInfo = new ExamInfo();

            examInfo.setCourseName(infoGirds.get(0).text());
            examInfo.setCredit(infoGirds.get(1).text());
            examInfo.setTerm(infoGirds.get(2).text());
            examInfo.setScore(infoGirds.get(3).text());

            examInfos.add(examInfo);
        }

        return examInfos;
    }
}
