package cn.linghang.mywust.core.parser.postgraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.model.global.ClassRoom;
import cn.linghang.mywust.model.global.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraduateCourseTablePageParser implements Parser<List<Course>> {

    private static final Pattern COURSE_TABLE_REGEX = Pattern.compile("课程:(?<name>.*?)<br>班级:(?<class>.*?)<br>\\((?<classRoom>.*?)\\)<br>(?<week>.*?)<br>(?<section>.*?)<br>主讲教师:(?<teacher>.*?)<br>");

    private static final Pattern WEEK_REGEX = Pattern.compile("(?<startWeek>\\d+)-(?<endWeek>\\d+)周.*?星期(?<weekDay>[一二三四五六七日天])");

    @Override
    public List<Course> parse(String html) throws ParseException {
        Document document = Jsoup.parse(html);
        Element table = document.getElementById("DataGrid1");
        if (table == null) {
            throw new ParseException("解析研究生课表失败：关键元素不存在...", html);
        }

        // 初步拿到所有的课程格子
        Elements girds = table.getElementsByAttribute("rowspan");
        String girdsHtml = girds.outerHtml();

        List<Course> courses = new ArrayList<>(girds.size());
        Course.CourseBuilder courseBuilder = Course.builder();

        // 正则提取每一段课程文本
        Matcher matcher = COURSE_TABLE_REGEX.matcher(girdsHtml);
        while (matcher.find()) {
            String name = matcher.group("name");
            courseBuilder.name(name);

            String teachClass = matcher.group("class");
            courseBuilder.teachClass(teachClass);

            String classroom = matcher.group("classRoom");
            courseBuilder.classroom(new ClassRoom("", "", "", classroom));

            String teacher = matcher.group("teacher");
            courseBuilder.teacher(teacher);

            String weekStr = matcher.group("week");
            this.parseWeek(weekStr, courseBuilder);

            String section = matcher.group("section");
            this.fillCourseList(section, courseBuilder, courses);
        }

        return courses;
    }

    /**
     * 解析周次信息文本
     *
     * @param weekText 周次文本，如“3-14周:连续周 星期三”
     * @param builder  Lesson的builder
     */
    private void parseWeek(String weekText, Course.CourseBuilder builder) {
        Matcher matcher = WEEK_REGEX.matcher(weekText);
        if (matcher.find()) {
            String startWeek = matcher.group("startWeek");
            String endWeek = matcher.group("endWeek");
            String weekDay = matcher.group("weekDay");

            builder.startWeek(Integer.parseInt(startWeek));
            builder.endWeek(Integer.parseInt(endWeek));
            builder.weekDay(Course.getWeekDayNumber(weekDay));
        }
    }

    /**
     * 解析节次，并将解析出来的完整的节次放入List中
     *
     * @param sectionText 提取出来的节次文本，如“上1,上2,上3,上4,下1,下2”
     * @param builder     LessonImpl中的builder
     * @param courses     存放Lesson的List
     */
    private void fillCourseList(String sectionText, Course.CourseBuilder builder, List<Course> courses) {
        String[] sections = sectionText.split(",");
        for (int i = 0; i < sections.length / 2; i += 2) {
            int startSection = this.getSection(sections[i]);
            int endSection = this.getSection(sections[i + 1]);

            builder.startSection(startSection);
            builder.endSection(endSection);

            courses.add(builder.build());
        }
    }

    /**
     * 将上1，下2，晚1这种相对的节次格式转换为相应的绝对节次
     *
     * @param time 类似于上1，下2，晚1这种相对的节次格式文本
     * @return 相应的绝对节次
     */
    private int getSection(String time) {
        int i = time.charAt(1) - 48;
        switch (time.charAt(0)) {
            case '上':
                return i;
            case '下':
                return i + 4;
            case '晚':
                return i + 8;
            default:
                return 1;
        }
    }
}
