package cn.wustlinghang.mywust.core.parser.undergraduate.school;

import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.data.common.Course;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class CourseTableParserBase implements Parser<List<Course>> {

    private static final PlaceNameParser placeNameParser = new PlaceNameParser();

    static {
        placeNameParser.registerRule(PlaceNameParser.ParserRules.huangjiahuBuildingWithAreaRule);
        placeNameParser.registerRule(PlaceNameParser.ParserRules.buildingWithRoomRule);
        placeNameParser.registerRule(PlaceNameParser.ParserRules.buildingWithCampusRule);
        placeNameParser.registerRule(PlaceNameParser.ParserRules.buildingWithCollegeRule);
    }

    private final Pattern regex;
    private final String courseGirdsXPath;

    CourseTableParserBase(Pattern regex, String courseGirdsXPath) {
        this.regex = regex;
        this.courseGirdsXPath = courseGirdsXPath;
    }

    public static void registerPlaceNameParserRule(PlaceNameParser.Rule rule) {
        placeNameParser.registerRule(rule);
    }

    public List<Course> parse(String html) throws ParseException {
        // 直接一步到位，拿到所有的大格子
        Elements bigGirds = Jsoup.parse(html).selectXpath(this.courseGirdsXPath);
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
                this.parseGird(courseGird, courseBuilder, courses);
            }

            // 别忘了这里还有个计数器
            girdCount++;
        }

        return courses;
    }

    private static final Pattern weekPattern = Pattern.compile("\\d+");

    private void parseGird(Element courseGird, Course.CourseBuilder courseBuilder, List<Course> resultList) {
        Matcher courseInfoMatcher = this.regex.matcher(courseGird.ownText());
        if (!courseInfoMatcher.find()) {
            return;
        }

        courseBuilder.name(courseInfoMatcher.group("name"));
        courseBuilder.teacher(courseInfoMatcher.group("teacher"));
        courseBuilder.teachClass(courseInfoMatcher.group("teachClass"));
        courseBuilder.classroom(placeNameParser.parse(courseInfoMatcher.group("building")));

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

            resultList.add(courseBuilder.build());
        }
    }
}

