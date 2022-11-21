package cn.linghang.mywust.model.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>学生数据实体类</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    private String studentNumber;
    private String name;
    private String college;
    private String major;
    private String clazz;
    private String birthday;
    private String sex;
    private String nationality;
    private String hometown;
    private String idNumber;
}
