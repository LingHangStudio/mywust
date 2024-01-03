package cn.wustlinghang.mywust.core.parser.undergraduate.school;

import cn.wustlinghang.mywust.core.parser.Parser;
import cn.wustlinghang.mywust.core.request.service.undergraduate.school.UndergradClassroomCourseApiService;
import cn.wustlinghang.mywust.data.common.Classroom;
import cn.wustlinghang.mywust.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 本科生的教室列表页面解析器，
 * 用于解析从『教室课表查询({@link UndergradClassroomCourseApiService})』中获得的页面，
 * 获取所有的教室。
 * </P>
 */
public class UndergradClassroomParser implements Parser<List<Classroom>> {
    @Override
    public List<Classroom> parse(String html) throws ParseException {
        return new ArrayList<>();
    }
}
