package cn.linghang.mywust.core.parser.physics;

import cn.linghang.mywust.core.exception.HtmlPageParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.physics.xpath.PhysicsIndexXpath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PhysicsIndexPageParser implements Parser<String> {
    public String parse(String html) throws HtmlPageParseException {
        Document page = Jsoup.parse(html);
        Elements linkElements = page.selectXpath(PhysicsIndexXpath.PHYSICS_LINK_XPATH);
        if (linkElements.isEmpty()) {
            throw new HtmlPageParseException();
        }

        return linkElements.get(0).attr("href");
    }
}
