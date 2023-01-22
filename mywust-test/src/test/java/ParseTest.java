import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.ExamInfoParser;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ParseTest {
    public static void main(String[] args) throws IOException, ParseException {
        ExamInfoParser parser = new ExamInfoParser();
        System.out.println(parser.parse(Jsoup.connect("http://127.0.0.1/a.html").get().toString()));
    }
}
