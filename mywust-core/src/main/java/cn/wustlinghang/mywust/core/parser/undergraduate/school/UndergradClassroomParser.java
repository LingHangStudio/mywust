package cn.wustlinghang.mywust.core.parser.undergraduate.school;

import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.request.service.undergraduate.school.UndergradClassroomCourseApiService;
import cn.wustlinghang.mywust.exception.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 本科生的教室列表页面解析器，
 * 用于解析从『教室课表查询({@link UndergradClassroomCourseApiService})』中获得的页面，
 * 获取所有的教室名称。
 * </P>
 */
public class UndergradClassroomParser implements Parser<List<String>> {
    private static final String classroomGirdXpath = "//*[@id=\"kbtable\"]/tbody/tr/td[1]";

    @Override
    public List<String> parse(String html) throws ParseException {
        Elements classroomElements = Jsoup.parse(html).selectXpath(classroomGirdXpath);
        List<String> classroomNames = new ArrayList<>(classroomElements.size());
        for (Element element : classroomElements) {
            classroomNames.add(element.text());
        }

        return classroomNames;
    }
}
