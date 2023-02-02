package cn.linghang.mywust.core.parser.physics;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.HuangjiahuClassroomNameParser;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.model.global.Classroom;
import cn.linghang.mywust.model.physics.PhysicsCourse;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhysicsCoursePageParser implements Parser<List<PhysicsCourse>> {
    private static final HuangjiahuClassroomNameParser HUANGJIAHU_CLASSROOM_NAME_PARSER = new HuangjiahuClassroomNameParser();

    // 第几周
    private static final Pattern PHYSICS_COURSE_WEEK_PATTERN = Pattern.compile("第(?<week>.*?)周");

    // 星期
    private static final Pattern PHYSICS_COURSE_WEEKDAY_PATTERN = Pattern.compile("星期(?<weekDay>[一二三四五六七日天]?)");

    // 开始和结束时间
    private static final Pattern PHYSICS_COURSE_START_END_PATTERN = Pattern.compile("(?<start>\\d+?)[&\\-|~至](?<end>\\d+)节");

    // 日期时间
    private static final Pattern PHYSICS_COURSE_DATE_PATTERN = Pattern.compile("(?<date>\\d{4}-\\d{2}-\\d{2}?)");

    @Override
    public List<PhysicsCourse> parse(String html) throws ParseException {
        Elements courseElements = Jsoup.parse(html).selectXpath(PhysicsCourseXpath.COURSE_ROWS_XPATH);
        if (courseElements.isEmpty()) {
            throw new ParseException(html);
        }

        List<PhysicsCourse> courses = new ArrayList<>(courseElements.size());

        // 从1开始，跳过表头
        for (int i = 1; i < courseElements.size(); i++) {
            Elements columnContextElements = courseElements.get(i).getElementsByTag("td");
            PhysicsCourse course = new PhysicsCourse();

            // 这里的代码硬编码了，不是很规范，抱歉
            course.setName(columnContextElements.get(1).text());
            course.setTeacher(columnContextElements.get(3).text().replace('\uE863', '䶮'));

            String classroomNumber = columnContextElements.get(5).text();
            Classroom classRoom = HUANGJIAHU_CLASSROOM_NAME_PARSER.parse(classroomNumber);
            course.setClassroom(classRoom);

            String time = columnContextElements.get(4).text();

            Matcher weekMatcher = PHYSICS_COURSE_WEEK_PATTERN.matcher(time);
            if (weekMatcher.find()) {
                // 物理实验，一个只有一节（一周），所以开始周和结束周是一样的
                course.setStartWeek(Integer.parseInt(weekMatcher.group("week")));
                course.setEndWeek(course.getStartWeek());
            }

            Matcher weekDayMatcher = PHYSICS_COURSE_WEEKDAY_PATTERN.matcher(time);
            if (weekDayMatcher.find()) {
                course.setWeekDay(weekDayMatcher.group("weekDay"));
            }

            Matcher startEndMatcher = PHYSICS_COURSE_START_END_PATTERN.matcher(time);
            if (startEndMatcher.find()) {
                course.setStartSection(Integer.parseInt(startEndMatcher.group("start")));
                course.setEndSection(Integer.parseInt(startEndMatcher.group("end")));
            }

            Matcher dateMatcher = PHYSICS_COURSE_DATE_PATTERN.matcher(time);
            if (dateMatcher.find()) {
                course.setDate(dateMatcher.group("date"));
            }

            courses.add(course);
        }

        return courses;
    }
}

final class PhysicsCourseXpath {
    /**
     * 用于获取表格中所有行的xpath
     * */
    public static final String COURSE_ROWS_XPATH = "//*[@id=\"ID_PEE110301_gvpee120101\"]/tbody/tr";

    public static final String COURSE_ROW_NAME_XPATH = "";
    public static final String COURSE_ROW_TEACHER_XPATH = "";
    public static final String COURSE_ROW_TIME_XPATH = "";
    public static final String COURSE_ROW_CLASSROOM_XPATH = "";
}
