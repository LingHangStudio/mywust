package cn.linghang.mywust.core.parser.undergraduate.global;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.data.global.Classroom;
import cn.linghang.mywust.data.global.Course;
import cn.linghang.mywust.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class GlobalCourseTableParser {

    private static final Pattern weekPattern = Pattern.compile("\\d+");

    protected List<Course> parse(String html, String xpath, Pattern infoPattern) throws ParseException {
        // 直接一步到位，拿到所有的大格子
        Elements bigGirds = Jsoup.parse(html).selectXpath(xpath);
        System.out.println("Girds: " + bigGirds.size());
        if (bigGirds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Course> courses = new ArrayList<>(bigGirds.size());

        int girdCount = 0;
        for (Element bigGird : bigGirds) {
            // 处理完一行，计数器置零，方便后续的计算
            if (girdCount == 42) {
                girdCount = 0;
            }

            // 大格子是空的，没课，跳过就好了
            if (bigGird.text().length() == 0) {
                girdCount++;
                continue;
            }

            // 拿到格子里面所有的课
            Elements courseGirds = bigGird.getElementsByTag("div");

            // 最左边的格子是表的首列，不是课程，拿到的集合是空的，直接跳过，不要计数，以免影响计算节次和星期信息
            if (courseGirds.isEmpty()) {
                continue;
            }

            // 通过计数器计算上课星期和节次信息
            int weekDay = girdCount / 6 + 1;
            int startSection = (girdCount % 6) * 2 + 1;

            Course.CourseBuilder courseBuilder = Course.builder();
            courseBuilder.weekDay(weekDay);
            courseBuilder.startSection(startSection);
            courseBuilder.endSection(startSection + 1);

            // 解析格子里的课
            for (Element courseGird : courseGirds) {
                Matcher courseInfoMatcher = infoPattern.matcher(courseGird.ownText());
                if (!courseInfoMatcher.find()) {
                    continue;
                }

                courseBuilder.name(courseInfoMatcher.group("name"));
                courseBuilder.teacher(courseInfoMatcher.group("teacher"));
                courseBuilder.teachClass(courseInfoMatcher.group("teachClass"));
                courseBuilder.classroom(this.parseClassroom(courseInfoMatcher.group("building")));

                // 解析周次，不使用String.split而是手动分割，是因为系统自带split方法每次调用都需要编译一次切割正则，这里需要执行次数较多，效率不太行
                List<String> weeks = StringUtil.split(courseInfoMatcher.group("weekString"), ',');
                for (String week : weeks) {
                    Matcher weekMatcher = weekPattern.matcher(week);
                    // 周次信息不是数字，这种情况尚未出现过，这里的if判断只是用于消除warming
                    if (!weekMatcher.find()) {
                        continue;
                    }

                    int startWeek = Integer.parseInt(weekMatcher.group());
                    // 再执行一次matcher.find()，如果没有数字匹配说明是单周课程
                    int endWeek = weekMatcher.find() ? Integer.parseInt(weekMatcher.group()) : startWeek;

                    courseBuilder.startWeek(startWeek);
                    courseBuilder.endWeek(endWeek);

                    courses.add(courseBuilder.build());
                }
            }

            // 别忘了这里还有个计数器
            girdCount++;
        }

        return courses;
    }

    // 搬运修改自老项目
    private static final Pattern BUILDING_ROOM_INTERNATION_PATTERN = Pattern.compile("(?<building>.*楼)(?<area>.*区)(?<room>\\d+)\\((国际专用)\\)"); // xx楼x区xxx(国际专用)

    private static final Pattern BUILDING_ROOM_CAMPUS_PATTERN = Pattern.compile("(?<building>.*楼)(?<room>\\d+)\\((?<campus>.*)\\)");  // xx楼xxx(黄家湖)

    private static final Pattern BUILDING_AREA_ROOM_PATTERN = Pattern.compile("(?<building>.*楼)(?<area>.*区)(?<room>\\d+)");   // xx楼x区xxx

    private static final Pattern BUILDING_COLLEGE_ROOM_PATTERN = Pattern.compile("(?<building>.*楼)\\((.*)\\)(?<room>\\d+)");  //xx楼(xxx)xxx

    private static final Pattern BUILDING_ROOM = Pattern.compile("(?<building>.*楼)(?<room>\\d+)");   // xx楼xxx

    /**
     * 解析上课地点
     *
     * @param placeName 上课地点名称
     * @return 解析后的上课地点
     */
    protected Classroom parseClassroom(String placeName) {
        // 用正则一个一个格式匹配，看起来有点不太聪明的样子，但是目前并未想到更好的办法...
        // 下面的顺序是按照频率排好的，概率越高，就放在更前面，减少正则匹配的次数
        Matcher matcher = BUILDING_AREA_ROOM_PATTERN.matcher(placeName);
        if (matcher.find()) {
            return new Classroom("", matcher.group("building"), matcher.group("area"), matcher.group("room"));
        }

        matcher = BUILDING_ROOM.matcher(placeName);
        if (matcher.find()) {
            return new Classroom("", matcher.group("building"), "", matcher.group("room"));
        }

        matcher = BUILDING_ROOM_CAMPUS_PATTERN.matcher(placeName);
        if (matcher.find()) {
            return new Classroom(matcher.group("campus"), matcher.group("building"), "", matcher.group("room"));
        }

        matcher = BUILDING_COLLEGE_ROOM_PATTERN.matcher(placeName);
        if (matcher.find()) {
            return new Classroom("", matcher.group("building"), "", matcher.group("room"));
        }

        matcher = BUILDING_ROOM_INTERNATION_PATTERN.matcher(placeName);
        if (matcher.find()) {
            return new Classroom("", matcher.group("building"), matcher.group("area"), matcher.group("room"));
        }

        return new Classroom("", "", "", placeName);
    }
}
