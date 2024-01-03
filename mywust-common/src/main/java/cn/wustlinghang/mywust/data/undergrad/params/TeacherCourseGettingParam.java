package cn.wustlinghang.mywust.data.undergrad.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseGettingParam {
    /**
     * 学期
     */
    private String term;

    /**
     * 时间模式
     */
    private String timeMode;

    /**
     * 开课学院id，使用子学院id
     */
    private String collegeId;

    /**
     * 教师名
     */
    private String teacherName;
}
