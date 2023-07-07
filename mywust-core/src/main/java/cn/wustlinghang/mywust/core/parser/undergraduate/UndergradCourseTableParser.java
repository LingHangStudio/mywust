package cn.wustlinghang.mywust.core.parser.undergraduate;

import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.util.JsoupUtil;
import cn.wustlinghang.mywust.data.global.Classroom;
import cn.wustlinghang.mywust.data.global.Course;
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

public class UndergradCourseTableParser implements Parser<List<Course>> {
    private static final Logger log = LoggerFactory.getLogger(UndergradCourseTableParser.class);

    private static final String COURSE_SPLIT_STR = "---------------------";

    private static final String COURSE_SPLIT_TAG_STR = "</div><div>";

    // 用来匹配数字的，位数不限
    private static final Pattern DIGITAL_PATTERN = Pattern.compile("\\d+");

    // 例：1-17(周)[03-04节]; 1-2,4-7(周)[01-02节]
    // 容易看一点的：(?<week>.*?)(周)[(?<section>.*?)节]
    // 提取出来后：week: 1-17, section: 03-04; week: 1-2,4-7, section: 01-02
    private static final Pattern WEEK_SECTION_REGEX = Pattern.compile("(?<week>.*?)\\(周\\)\\[(?<section>.*?)节]");

    /**
     * 解析课程，可能会有重复的课程，调用者需要手动去重
     *
     * @param html 原页面html
     * @return 解析好的课程List
     * @throws ParseException 解析课表时出现任何问题
     */
    @Override
    public List<Course> parse(String html) throws ParseException {
        try {
            // 用css选择器获取所有格子（不包括标题格和左侧格，也就是说拿到的所有元素都是课程信息的格子）
            // 不过需要注意的是，通过.kbcontent1:not(.sykb1)获取到的是没有老师的，是精简过的
            // 效果和使用xpath是一样的
            // 实际上可以解析name=jx0415zbdiv_1的input表单value获得对应的table id
            // 通过这个id直接拿到对应的内容，相对于css选择器能更好的防止脑抽改class
            // 但是class都改了估计整个页面逻辑也会改了（ui换了），用什么办法多少都得要去改
            // 按照这系统的尿性，并且为简单起见，直接用css选择器得了
            Elements girds = Jsoup.parse(html).select(".kbcontent:not(.sykb2)");

            List<Course> courses = new ArrayList<>(girds.size());

            // 遍历每个格子，使用girdCount计数格子来计算星期
            int girdCount = 0;
            for (Element gird : girds) {
                girdCount++;

                // 将分隔符替换成标签，方便重新解析格子
                String girdHtml = gird.outerHtml().replace(COURSE_SPLIT_STR, COURSE_SPLIT_TAG_STR);
                Elements courseElements = Jsoup.parse(girdHtml).getElementsByTag("div");
                for (Element courseElement : courseElements) {
                    // 格子文本为空，说明这个格子没课，直接跳过这个格子就行了
                    // 注意，使用这个条件判断时对jsoup版本有要求，在比较旧的版本下gird.ownText()空格子其实并不空，而是有一个空格的
                    // 在某个版本之后（至少是1.10到1.15之间的某个版本）会自动剔除多余空格（trim()），所以直接这样判断就行了
                    // 只不过需要注意一下jsoup的版本，太旧的话可能不会起作用，如确需在旧版本上使用请手动trim或加条件
                    String courseName = courseElement.ownText();
                    if ("".equals(courseName)) {
                        continue;
                    }

                    Course.CourseBuilder courseBuilder = Course.builder();

                    courseBuilder.name(courseName);

                    // 直接获取格子里的关键信息
                    Elements classElements = courseElement.getElementsByAttributeValue("title", "课堂名称");
                    Elements teacherElements = courseElement.getElementsByAttributeValue("title", "老师");
                    Elements timeElements = courseElement.getElementsByAttributeValue("title", "周次(节次)");
                    Elements classroomElements = courseElement.getElementsByAttributeValue("title", "教室");

                    courseBuilder.teachClass(JsoupUtil.getElementText(classElements));
                    courseBuilder.teacher(JsoupUtil.getElementText(teacherElements));

                    Classroom classRoom = new Classroom();
                    classRoom.setRoom(JsoupUtil.getElementText(classroomElements));
                    courseBuilder.classroom(classRoom);

                    int weekDay = girdCount % 7;
                    courseBuilder.weekDay(weekDay == 0 ? 7 : weekDay);

                    String timeText = JsoupUtil.getElementText(timeElements, 0);
                    this.parseTime(timeText, courseBuilder, courses);
                }
            }

            return courses;

        } catch (Exception e) {
            log.warn("解析课表时出现问题：{}", e.getMessage(), e);
            throw new ParseException("解析课表时出现问题：" + e, html);
        }
    }

    /**
     * 解析周次和节次时间，主要使用正则解析
     *
     * @param timeText      周次节次文本，如：1-17(周)[03-04节]; 1-2,4-7(周)[01-02节]
     * @param courseBuilder courseBuilder
     * @param courses       课程解析结果列表
     */
    private void parseTime(String timeText, Course.CourseBuilder courseBuilder, List<Course> courses) {
        Matcher timeMatcher = WEEK_SECTION_REGEX.matcher(timeText);
        if (timeMatcher.find()) {
            // 解析节次，这种方法相比于通过定位格子来确定节次更准确，但是可能会出现重复的课程
            String sectionString = timeMatcher.group("section");
            Matcher sectionMatcher = DIGITAL_PATTERN.matcher(sectionString);
            if (sectionMatcher.find()) {
                int startSection = Integer.parseInt(sectionMatcher.group());

                // 不断匹配下一个数字，直到最后一个，即为结束节次数字，如果第一次就不匹配，则为单节课
                int endSection = startSection;
                while (sectionMatcher.find()) {
                    endSection = Integer.parseInt(sectionMatcher.group());
                }

                courseBuilder.startSection(startSection);
                courseBuilder.endSection(endSection);
            }

            String weekString = timeMatcher.group("week");

            // 切割连续周信息，如"1-2,4-6(周)"这样两段的连续周(1-3周和5-10周)
            // 不直接使用String.split而是手动分割，是因为系统自带split方法每次调用都需要编译一次切割正则，效率不太行，但是有一说一，其实可以忽略
            List<String> weekTexts = StringUtil.split(weekString, ',');
            for (String weekText : weekTexts) {
                Matcher weekMatcher = DIGITAL_PATTERN.matcher(weekText);
                // 周次信息不是数字，这种情况尚未出现过，这里的if判断只是用于消除warming
                if (!weekMatcher.find()) {
                    continue;
                }

                // 第二次matcher.find()匹配结束周，如果没有数字匹配说明是单周课程
                int startWeek = Integer.parseInt(weekMatcher.group());
                int endWeek = weekMatcher.find() ? Integer.parseInt(weekMatcher.group()) : startWeek;
                courseBuilder.startWeek(startWeek).endWeek(endWeek);

                courses.add(courseBuilder.build());
            }
        }
    }
}
