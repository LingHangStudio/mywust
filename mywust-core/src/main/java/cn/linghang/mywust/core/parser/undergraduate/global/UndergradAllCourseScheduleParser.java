package cn.linghang.mywust.core.parser.undergraduate.global;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.data.global.Course;

import java.util.List;
import java.util.regex.Pattern;


public class UndergradAllCourseScheduleParser extends GlobalCourseTableParser implements Parser<List<Course>> {
    private static final String courseGirdsXPath = "//*[@id=\"kbtable\"]/tbody/tr/td";

    // name组的如果使用.*?匹配而不是.*贪婪匹配的话虽然可以大大减少匹配次数，但是对于课程名中有空格的课程，可能会导致解析错误，
    // 不过对于没有老师且课程名中含有空格的课程（都是一些在线课程），虽然能匹配，但是字段会有偏差错误，不过影响不大就是了，在线课程不用太过于纠结
    private static final Pattern pattern = Pattern.compile("(?<name>.*) (?<teachClass>.*?) (?<teacher>.*?) \\((?<weekString>.*?)周\\) ?(?<building>.*)");

    @Override
    public List<Course> parse(String html) throws ParseException {
        return super.parse(html, courseGirdsXPath, pattern);
    }
}