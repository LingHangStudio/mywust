package cn.wustlinghang.mywust.core.parser.undergraduate.school;

import cn.wustlinghang.mywust.data.common.Course;
import cn.wustlinghang.mywust.exception.ParseException;

import java.util.List;
import java.util.regex.Pattern;


public class UndergradTeacherCourseParser extends CourseTableParserBase {
    private static final String courseGirdsXPath = "//*[@id=\"kbtable\"]/tbody/tr/td";

    // 还有教学班名称格式为20xx寒假课堂xx的线上课和教学班类似于“国贸[1901-1903]班,国贸1901（香涛）班”的这种较为复杂的教学班字段是匹配不到的，但是数量极少，大约只有几个，可以忽略不计
    // 其实主要是教学班和课程名称和在一起了，不好分离，这里解析出来的字段有些是不怎么准确的，推荐使用课程课表来查询（但是得到的课貌似比这里的少（可能是线上课？））
    private static final Pattern pattern = Pattern.compile("(?<name>.*?[^ ]) ?(?<teachClass>教学班\\d+|\\d+班|临班\\d+|\\[\\d+-\\d+]班) (?<teacher>.*?) \\((?<weekString>.*?)周\\) ?(?<building>.*)");

    public UndergradTeacherCourseParser() {
        super(pattern, courseGirdsXPath);
    }

    @Override
    public List<Course> parse(String html) throws ParseException {
        return super.parse(html);
    }
}