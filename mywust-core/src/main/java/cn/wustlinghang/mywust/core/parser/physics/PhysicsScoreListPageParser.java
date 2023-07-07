package cn.wustlinghang.mywust.core.parser.physics;

import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.util.JsoupUtil;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PhysicsScoreListPageParser implements Parser<PhysicsScoreListPageParser.PhysicsScoreListPageParseResult> {
    private static final String scorePageLinkXpath = "//*[@id=\"ID_PEE63_gvpeeCourseScore\"]/tbody/tr/td/a";

    @Override
    public PhysicsScoreListPageParseResult parse(String html) throws ParseException {
        List<String> scoreIds = new ArrayList<>(2);
        Document document = Jsoup.parse(html);
        Elements links = document.selectXpath(scorePageLinkXpath);
        for (Element link : links) {
            String href = link.attr("href");
            scoreIds.add(href.replaceAll("javascript:__doPostBack\\('(.*?)',''\\)", "$1"));
        }

        Element termSelect = document.getElementById("ID_PEE63_ddlxq");
        String term = JsoupUtil.getSelectValue(termSelect);

        return new PhysicsScoreListPageParseResult(scoreIds, term);
    }

    @Data
    public static class PhysicsScoreListPageParseResult {
        private final List<String> courseIds;
        private final String term;
    }
}
