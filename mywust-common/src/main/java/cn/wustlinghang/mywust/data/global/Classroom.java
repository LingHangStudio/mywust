package cn.wustlinghang.mywust.data.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    /**
     * 校区，黄家湖或青山
     */
    private String campus;

    /**
     * 教学楼编号，如1, 2, 3, 11，对应教1楼（恒大楼），教2楼（理学院），教3楼（计院），教11
     */
    private String building;

    /**
     * 教学楼区域编号，如0，1，A，B等，对应0区（没有分区），1区，A区（教11）
     */
    private String area;

    /**
     * 教室名，如301，208，802
     */
    private String room;
}
