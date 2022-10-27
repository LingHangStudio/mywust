package cn.linghang.mywust.model.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
     * 课程名称
     */
    private String name;

    /**
     * 教师名称
     */
    private String teacher;

    /**
     * 教学班
     */
    private String teachClass;

    /**
     * 开始周
     */
    private int startWeek;

    /**
     * 结束周
     */
    private int endWeek;

    /**
     * 星期几
     */
    private int weekDay;

    /**
     * 开始时间
     */
    private int startSection;

    /**
     * 结束时间
     */
    private int endSection;

    private ClassRoom classroom;

    public static final Map<String, Integer> WEEKDAY_MAP = makeWeekdayMap();

    private static Map<String, Integer> makeWeekdayMap() {
        HashMap<String, Integer> map = new HashMap<>(7 + 2);

        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("日", 7);
        map.put("天", 7);

        return map;
    }
}
