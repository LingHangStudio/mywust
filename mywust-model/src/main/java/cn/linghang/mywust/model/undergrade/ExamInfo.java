package cn.linghang.mywust.model.undergrade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>考试成绩信息实体类</p>
 * <br>
 * <p>一个对象实体对应着一次考试，也就是系统里边看到的一行成绩</p>
 * <p>决定全都用string存储，交给上层调用者自行处理转换</p>
 * <p>看似浪费内存，但实际上解析出来的就是string，string转int/float然后又转成其他的什么后其实是更耗内存的</p>
 * <p>既然如此不如直接存解析出来的值，具体如何使用就交给上层自行决定</p>
 *
 * @author lensfrex
 * @created 2022-10-26 14:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamInfo {
    /**
     * 序号id，在某些场景下可能会有用
     */
    private String id;

    /**
     * 学期
     */
    private String term;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 成绩
     */
    private String score;

    /**
     * 成绩标识（缺考）
     */
    private String flag;

    /**
     * 学分
     */
    private String credit;

    /**
     * 学时
     */
    private String courseHours;

    /**
     * 绩点
     */
    private String gradePoint;

    /**
     * 考核方式
     */
    private String evaluateMethod;

    /**
     * 考试性质
     */
    private String kind;

    /**
     * 课程性质
     */
    private String courseKind;
}
