package cn.wustlinghang.mywust.data.undergrad.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomCourseGettingParam {
    /**
     * 学期
     */
    private String term;

    /**
     * 上课学院id
     */
    private String collegeId;

    /**
     * 时间模式
     */
    private String timeMode;

    /**
     * 校区id
     */
    private String campusId;

    /**
     * 教学楼id
     */
    private String buildingId;

    /**
     * 教室名
     */
    private String classroomName;
}
