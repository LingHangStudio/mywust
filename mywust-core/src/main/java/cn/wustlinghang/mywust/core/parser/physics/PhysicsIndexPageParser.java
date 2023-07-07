package cn.wustlinghang.mywust.core.parser.physics;

import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PhysicsIndexPageParser implements Parser<String> {
    public String parse(String html) throws ParseException {
        Document page = Jsoup.parse(html);
        Elements linkElements = page.selectXpath(PhysicsIndexXpath.PHYSICS_LINK_XPATH);
        if (linkElements.isEmpty()) {
            throw new ParseException(html);
        }

        return linkElements.get(0).attr("href");
    }
}

final class PhysicsIndexXpath {
    public static final String PHYSICS_LINK_XPATH = "//*[@id=\"rolemenu\"]/tbody/tr[1]/td[1]/a[2]";
}

