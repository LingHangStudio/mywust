package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.model.global.ClassRoom;
import cn.linghang.mywust.model.global.Course;
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

    private static final Pattern WEEK_RANGE_REGEX = Pattern.compile("(?<startWeek>\\d+)-(?<endWeek>\\d+)");

    private static final Pattern SINGLE_WEEK_REGEX = Pattern.compile("(?<week>\\d+)");

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

            // 遍历每个格子，使用girdCount计数格子来计算节次信息
            int girdCount = 0;
            for (Element gird : girds) {
                girdCount++;

                // 将分隔符替换成标签，方便解析
                String girdHtml = gird.outerHtml().replace(COURSE_SPLIT_STR, COURSE_SPLIT_TAG_STR);
                Elements courseElements = Jsoup.parse(girdHtml).getElementsByTag("div");
                for (Element courseElement : courseElements) {
                    Course.CourseBuilder courseBuilder = Course.builder();

                    // 格子文本为空，说明这个格子没课，直接跳过这个格子就行了
                    // 注意，使用这个条件判断时对jsoup版本有要求，在比较旧的版本下gird.ownText()空格子其实并不空，而是有一个空格的
                    // 在某个版本之后（至少是1.10到1.15之间的某个版本）会自动剔除多余空格（trim()），所以直接这样判断就行了
                    // 只不过需要注意一下jsoup的版本，太旧的话可能不会起作用，如确需在旧版本上使用请手动trim或加条件
                    String courseName = courseElement.ownText();
                    if ("".equals(courseName)) {
                        continue;
                    }

                    courseBuilder.name(courseName);

                    // 直接获取格子里所有课程的关键字段，每个下表对应格子里相应的课程
                    Elements classElements = courseElement.getElementsByAttributeValue("title", "课堂名称");
                    Elements teacherElements = courseElement.getElementsByAttributeValue("title", "老师");
                    Elements timeElements = courseElement.getElementsByAttributeValue("title", "周次(节次)");
                    Elements classroomElements = courseElement.getElementsByAttributeValue("title", "教室");

                    courseBuilder.teachClass(classElements.isEmpty() ? "" : classElements.get(0).text());
                    courseBuilder.teacher(teacherElements.isEmpty() ? "" : teacherElements.get(0).text());

                    ClassRoom classRoom = new ClassRoom();
                    classRoom.setRoom(classroomElements.isEmpty() ? "" : classroomElements.get(0).text());
                    courseBuilder.classroom(classRoom);

                    int weekDay = girdCount % 7;
                    courseBuilder.weekDay(weekDay == 0 ? 7 : weekDay);

                    // 靠行位置来确定节次和星期，而不是靠time字段的数据确定（因为太不好处理了）
                    // 对于只有一个小节的课程，这类课程多数是在线课程，这里一律按照两小节大课处理
                    // 具体算法就是行索引x2 + 1就是开始的节次（索引从0开始）
                    int lineIndex = (int) (girdCount * 0.142);
                    courseBuilder.startSection(lineIndex * 2 + 1);
                    courseBuilder.endSection(lineIndex * 2 + 2);

                    // 提取周次信息，可能会有用","分成两段的周次信息文本
                    // 去除后面不需要的节次信息，以免对正则提取产生影响
                    // 这样做理论上有点浪费性能了，但还行
                    String timeText = timeElements.isEmpty() ? "" : timeElements.get(0).text().split("\\[")[0];
                    String[] times = timeText.split(",");
                    for (String time : times) {
                        int startWeek = 0;
                        int endWeek = 0;

                        Matcher matcher = WEEK_RANGE_REGEX.matcher(time);
                        if (matcher.find()) {
                            startWeek = Integer.parseInt(matcher.group("startWeek"));
                            endWeek = Integer.parseInt(matcher.group("endWeek"));
                        } else {
                            // 普通匹配不到的话多半就是只有一周的课程
                            matcher = SINGLE_WEEK_REGEX.matcher(time);
                            if (matcher.find()) {
                                startWeek = Integer.parseInt(matcher.group("week"));
                                endWeek = Integer.parseInt(matcher.group("week"));
                            }
                        }

                        courseBuilder.startWeek(startWeek).endWeek(endWeek);
                        courses.add(courseBuilder.build());
                    }
                }
            }

            return courses;
        } catch (Exception e) {
            log.warn("解析课表时出现问题：{}", e.getMessage(), e);
            throw new ParseException(html);
        }

    }

}
