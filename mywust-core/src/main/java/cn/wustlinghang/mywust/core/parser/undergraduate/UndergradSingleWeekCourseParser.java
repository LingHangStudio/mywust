package cn.wustlinghang.mywust.core.parser.undergraduate;

import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.data.common.Classroom;
import cn.wustlinghang.mywust.data.common.Course;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>单周课程页面解析</p>
 */
public class UndergradSingleWeekCourseParser implements Parser<List<Course>> {
    private static final Logger log = LoggerFactory.getLogger(UndergradSingleWeekCourseParser.class);

    private static final String COURSE_ELEMENT_XPATH = "//*[@id=\"tab1\"]/tbody/tr/td/p";

    private static final Pattern COURSE_INFO_REGEX = Pattern.compile("名称：(?<name>.*?)<.*?第(?<week>.*?)周.*?星期(?<weekday>.*?) \\[(?<section>.*?)]节<.*?地点：(?<place>.*)");

    private static final Pattern NUMBER_REGEX = Pattern.compile("\\d+");

    /**
     * 解析单个周的课程
     *
     * @param html 原页面html
     * @return 解析好的课程List
     * @throws ParseException 解析课表时出现任何问题
     */
    @Override
    public List<Course> parse(String html) throws ParseException {
        Elements courseElements = Jsoup.parse(html).selectXpath(COURSE_ELEMENT_XPATH);
        List<Course> courses = new ArrayList<>(courseElements.size());

        for (Element element : courseElements) {
            String title = element.attr("title");
            Matcher matcher = COURSE_INFO_REGEX.matcher(title);
            if (matcher.find()) {
                Course course = new Course();
                course.setWeekDay(matcher.group("weekday"));
                course.setName(matcher.group("name"));
                course.setClassroom(new Classroom(matcher.group("place")));

                // 单周课程拿不到教师和教学班信息
                course.setTeacher("");
                course.setTeachClass("");

                // 因为是单个周的课表，所以开始结束都是同一周
                int week = StringUtil.parseInt(matcher.group("week"), 1);
                course.setStartWeek(week);
                course.setEndWeek(week);

                Matcher sectionMatcher = NUMBER_REGEX.matcher(matcher.group("section"));
                if (sectionMatcher.find()) {
                    course.setStartSection(StringUtil.parseInt(sectionMatcher.group(), 1));
                }
                while (sectionMatcher.find()) {
                    course.setEndSection(StringUtil.parseInt(sectionMatcher.group(), 2));
                }

                courses.add(course);
            }
        }

        return courses;
    }
}
