package cn.wustlinghang.mywust.data.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Classroom implements WithIdData {
    private String id;

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

    public Classroom(String campus, String building, String area, String room) {
        this.campus = campus;
        this.building = building;
        this.area = area;
        this.room = room;
    }

    public Classroom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return (campus == null ? "" : campus) +
                (building == null ? "" : building) +
                (area == null ? "" : area) +
                (room == null ? "" : room);
    }

    @Override
    public String getId() {
        if (id != null) {
            return id;
        }

        String info = (campus == null ? "" : campus) +
                (building == null ? "" : building) +
                (area == null ? "" : area) +
                (room == null ? "" : room);

        this.id = DigestUtils.md5Hex(info);
        return this.id;
    }
}
