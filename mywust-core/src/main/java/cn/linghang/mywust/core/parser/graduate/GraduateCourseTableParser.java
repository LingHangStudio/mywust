package cn.linghang.mywust.core.parser.graduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.data.global.Classroom;
import cn.linghang.mywust.data.global.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraduateCourseTableParser implements Parser<List<Course>> {

    private static final Pattern COURSE_TABLE_REGEX = Pattern.compile("课程:(?<name>.*?)<br>班级:(?<class>.*?)<br>\\((?<classRoom>.*?)\\)<br>(?<week>.*?) (?<weekdaySection>.*?)<br>主讲教师:(?<teacher>.*?)<br>");

    private static final Pattern DIGITAL_REGEX = Pattern.compile("\\d+");

    private static final Pattern WEEKDAY_SECTION_REGEX = Pattern.compile("星期(?<weekDay>[一二三四五六七日天])<br>(?<sectionText>([上下晚]\\d,?)+)");

    private static final Pattern SECTION_REGEX = Pattern.compile("(?<dayTime>[上下晚])(?<section>\\d)");

    private static final String courseGirdXpath = "//*[@id=\"DataGrid1\"]/tbody/tr/td[@rowspan]";

    @Override
    public List<Course> parse(String html) throws ParseException {
        Document document = Jsoup.parse(html);
        Element table = document.getElementById("DataGrid1");
        if (table == null) {
            throw new ParseException("解析研究生课表失败：关键元素不存在...", html);
        }

        Elements courseGirds = table.selectXpath(courseGirdXpath);

        List<Course> courses = new ArrayList<>(courseGirds.size());
        Course.CourseBuilder courseBuilder = Course.builder();
        for (Element courseGird : courseGirds) {
            String girdHtml = courseGird.html();

            // 正则提取每一段课程文本
            Matcher matcher = COURSE_TABLE_REGEX.matcher(girdHtml);
            while (matcher.find()) {
                String name = matcher.group("name");
                courseBuilder.name(name);

                String teachClass = matcher.group("class");
                courseBuilder.teachClass(teachClass);

                String classroom = matcher.group("classRoom");
                courseBuilder.classroom(new Classroom("", "", "", classroom));

                String teacher = matcher.group("teacher");
                courseBuilder.teacher(teacher);

                String weekStr = matcher.group("week");
                this.parseWeek(weekStr, courseBuilder);

                this.parseWeekdaySectionAndFillCourse(matcher.group("weekdaySection"), courseBuilder, courses);
            }

        }

        return courses;
    }

    /**
     * 解析周次信息文本
     *
     * @param weekText 周次文本，如“3-14周:连续周 星期三”
     * @param builder  Course的builder
     */
    private void parseWeek(String weekText, Course.CourseBuilder builder) {
        Matcher matcher = DIGITAL_REGEX.matcher(weekText);
        if (matcher.find()) {
            String startWeek = matcher.group();

            // 一直匹配搜寻到最后一个数字，即为结束周次，第一次就匹配不到就是单周课
            // 实际上单周课写的也是"3-3"这样子，但是这样写兼容性比较好
            String endWeek = startWeek;
            while (matcher.find()) {
                endWeek = matcher.group();
            }

            builder.startWeek(Integer.parseInt(startWeek));
            builder.endWeek(Integer.parseInt(endWeek));
        }
    }

    /**
     * 解析时间并填充最后的解析结果
     *
     * @param timeText 时间字段，如 "星期五&lt;br&gt;上3,上4", "星期六&lt;br&gt;上1,上2,上3,上4"
     * @param builder  已经解析好其他必要数据的courseBuilder
     * @param courses  解析结果List
     */
    private void parseWeekdaySectionAndFillCourse(String timeText, Course.CourseBuilder builder, List<Course> courses) {
        Matcher timeMatcher = WEEKDAY_SECTION_REGEX.matcher(timeText);
        // 解析星期和节次，一直匹配，匹配到一次就是一次连续课
        while (timeMatcher.find()) {
            // 解析星期
            builder.weekDay(Course.getWeekDayNumber(timeMatcher.group("weekDay")));

            // 解析节次
            Matcher sectionMatcher = SECTION_REGEX.matcher(timeMatcher.group("sectionText"));
            if (sectionMatcher.find()) {
                int startSection = getSection(sectionMatcher);

                // todo 这段可以稍微优化一下
                // 一直匹配，最后的那个就是结束节次，如果第一次就不匹配的话就是单节课
                int endSection = startSection;
                while (sectionMatcher.find()) {
                    endSection = getSection(sectionMatcher);
                }

                builder.startSection(startSection);
                builder.endSection(endSection);
            }

            courses.add(builder.build());
        }
    }

    /**
     * 将上1，下2，晚1这种相对的节次格式转换为相应的绝对节次
     *
     * @param dateTime    上，下这种早上晚上的文本
     * @param sectionText 1，2，3这种相对的节次数文本
     * @return 相应的绝对节次
     */
    private int getSection(String dateTime, String sectionText) {
        int section = Integer.parseInt(sectionText);
        switch (dateTime.charAt(0)) {
            case '上':
                return section;
            case '下':
                return section + 4;
            case '晚':
                return section + 8;
            default:
                return 1;
        }
    }

    /**
     * 通过正则匹配器计算节次
     *
     * @param matcher 以及匹配好的matcher
     * @return 相应的绝对节次
     */
    private int getSection(Matcher matcher) {
        return getSection(matcher.group("dayTime"), matcher.group("section"));
    }
}
