package cn.linghang.mywust.core.parser;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.data.global.Classroom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>黄家湖校区教室编号解析（如12804（恒大楼二区804）,30104（教三楼104）, 11B304（教11B区304）这种）</p>
 * <p>具体的教学楼名称和区域不作生成，只对相应的字段结构进行拆分解析，由调用者自行决定具体名称</p>
 *
 * @author lensfrex
 * @create 2022-10-26 08:56
 */
public class HuangjiahuClassroomNameParser implements Parser<Classroom> {
    private static final Logger log = LoggerFactory.getLogger(HuangjiahuClassroomNameParser.class);

    private static final Pattern CLASSROOM_PATTERN = Pattern.compile("(?<buildingId>\\d)(?<areaId>\\d)(?<room>\\d{3})");

    private static final Pattern BUILDING_11_CLASSROOM_PATTERN = Pattern.compile("11(?<areaId>[A-C])(?<room>\\d{3})");

    @Override
    public Classroom parse(String classroomName) throws ParseException {
        Classroom classRoom = Classroom.builder().campus("黄家湖").build();
        try {
            Matcher matcher = CLASSROOM_PATTERN.matcher(classroomName);
            // 不匹配普通教学楼正则的多半就是教11的教室
            if (matcher.find()) {
                classRoom.setBuilding(matcher.group("buildingId"));
                classRoom.setArea(matcher.group("areaId"));
                classRoom.setRoom(matcher.group("room"));
            } else {
                matcher = BUILDING_11_CLASSROOM_PATTERN.matcher(classroomName);
                if (matcher.find()) {
                    classRoom.setBuilding("11");
                    classRoom.setArea(matcher.group("areaId"));
                    classRoom.setRoom(matcher.group("room"));
                } else {
                    // 解析都不匹配就直接用传进来的编号作为教室
                    classRoom.setBuilding("未知");
                    classRoom.setArea("未知");
                    classRoom.setRoom(classroomName);
                }
            }
        } catch (Exception e) {
            log.warn("解析教室编号失败，教室：{}", classroomName);
            throw new ParseException(classroomName);
        }

        return classRoom;
    }
}
