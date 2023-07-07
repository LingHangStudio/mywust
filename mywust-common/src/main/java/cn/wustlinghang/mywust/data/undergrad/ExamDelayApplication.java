package cn.wustlinghang.mywust.data.undergrad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDelayApplication {
    private String term;
    private String courseNumber;
    private String courseName;
    private String courseHours;
    private String credit;
    private String courseType;
    private String examType;
    private String scoreFlag;

    private String delayReason;
    private String status;
    private String auditOpinion;
    private String time;

    private boolean overdue;
}
