package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.model.global.StudentInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UndergradStudentInfoPageParser implements Parser<StudentInfo> {

    public StudentInfo parse(String html) throws ParseException {
        Document page = Jsoup.parse(html);
        Element table = page.getElementById("xjkpTable");
        if (table == null) {
            throw new ParseException(html);
        }

        Elements studentElements = table.selectXpath(StudentInfoXpath.STUDENT_NUMBER);
        String studentNumber = studentElements.isEmpty() ? null : studentElements.get(0).text().replace("学号：", "");

        Elements collegeElements = table.selectXpath(StudentInfoXpath.COLLEGE);
        String college = collegeElements.isEmpty() ? null : collegeElements.get(0).text().replace("院系：", "");

        Elements majorElements = table.selectXpath(StudentInfoXpath.MAJOR);
        String major = majorElements.isEmpty() ? null : majorElements.get(0).text().replace("专业：", "");

        Elements classElements = table.selectXpath(StudentInfoXpath.CLASS);
        String clazz = classElements.isEmpty() ? null : classElements.get(0).text().replace("班级：", "");

        Elements nameElements = table.selectXpath(StudentInfoXpath.NAME);
        String name = nameElements.isEmpty() ? null : nameElements.get(0).text();

        Elements sexElements = table.selectXpath(StudentInfoXpath.SEX);
        String sex = sexElements.isEmpty() ? null : sexElements.get(0).text();

        Elements birthdayElements = table.selectXpath(StudentInfoXpath.BIRTHDAY);
        String birthday = birthdayElements.isEmpty() ? null : birthdayElements.get(0).text();

        Elements hometownElements = table.selectXpath(StudentInfoXpath.HOMETOWN);
        String hometown = hometownElements.isEmpty() ? null : hometownElements.get(0).text();

        Elements nationalityElements = table.selectXpath(StudentInfoXpath.NATIONALITY);
        String nationality = nationalityElements.isEmpty() ? null : nationalityElements.get(0).text();

        Elements idNumberElements = table.selectXpath(StudentInfoXpath.ID_NUMBER);
        String idNumber = idNumberElements.isEmpty() ? null : idNumberElements.get(0).text();

        return StudentInfo.builder()
                .studentNumber(studentNumber)
                .college(college)
                .major(major)
                .clazz(clazz)
                .name(name)
                .sex(sex)
                .birthday(birthday)
                .hometown(hometown)
                .nationality(nationality)
                .idNumber(idNumber)
                .build();
    }


}
/**
 * <p>本科生学生信息接口用到的xpath</p>
 * <p>看着挺唬人的，其实直接浏览器选择元素复制xpath就行了</p>
 * <p>这里的xpath只要网站UI不整什么花活就不会出问题</p>
 *
 * @author lensfrex
 * @create 2022-10-22 22:16
 */
final class StudentInfoXpath {
    public static final String STUDENT_NUMBER = "//*[@id=\"xjkpTable\"]/tbody/tr[3]/td[5]";

    public static final String COLLEGE = "//*[@id=\"xjkpTable\"]/tbody/tr[3]/td[1]";

    public static final String MAJOR = "//*[@id=\"xjkpTable\"]/tbody/tr[3]/td[2]";

    public static final String CLASS = "//*[@id=\"xjkpTable\"]/tbody/tr[3]/td[4]";

    public static final String NAME = "//*[@id=\"xjkpTable\"]/tbody/tr[4]/td[2]";

    public static final String SEX = "//*[@id=\"xjkpTable\"]/tbody/tr[4]/td[4]";

    public static final String BIRTHDAY = "//*[@id=\"xjkpTable\"]/tbody/tr[5]/td[2]";

    public static final String HOMETOWN = "//*[@id=\"xjkpTable\"]/tbody/tr[7]/td[2]";

    public static final String NATIONALITY = "//*[@id=\"xjkpTable\"]/tbody/tr[8]/td[4]";

    public static final String ID_NUMBER = "//*[@id=\"xjkpTable\"]/tbody/tr[50]/td[4]";
}