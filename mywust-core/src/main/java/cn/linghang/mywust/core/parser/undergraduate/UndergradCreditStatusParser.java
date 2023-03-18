package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class UndergradCreditStatusParser implements Parser<String> {
    @Override
    public String parse(String html) throws ParseException {
        // 此处只提取核心内容，内容调整由调用者自行处理
        Elements mainTable = Jsoup.parse(html).selectXpath("/html/body/div/div/table[2]");
        if (mainTable.isEmpty()) {
            throw new ParseException("学分修读情况html解析提取失败", html);
        }

        return mainTable.outerHtml();
    }
}
