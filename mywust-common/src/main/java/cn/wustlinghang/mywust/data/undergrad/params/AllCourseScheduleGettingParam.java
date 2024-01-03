package cn.wustlinghang.mywust.data.undergrad.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllCourseScheduleGettingParam {
    /**
     * 学期
     */
    private String term;

    /**
     * 时间模式
     */
    private String timeMode;

    /**
     * 子学院id
     */
    private String subCollegeId;

    /**
     * 课程名
     */
    private String courseName;
}
