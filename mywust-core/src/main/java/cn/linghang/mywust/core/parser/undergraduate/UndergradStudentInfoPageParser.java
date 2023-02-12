package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.util.JsoupUtil;
import cn.linghang.mywust.data.global.StudentInfo;
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
        String studentNumber = JsoupUtil.getElementText(studentElements).replace("学号：", "");

        Elements collegeElements = table.selectXpath(StudentInfoXpath.COLLEGE);
        String college = JsoupUtil.getElementText(collegeElements).replace("院系：", "");

        Elements majorElements = table.selectXpath(StudentInfoXpath.MAJOR);
        String major = JsoupUtil.getElementText(majorElements).replace("专业：", "");

        Elements classElements = table.selectXpath(StudentInfoXpath.CLASS);
        String clazz = JsoupUtil.getElementText(classElements).replace("班级：", "");

        Elements nameElements = table.selectXpath(StudentInfoXpath.NAME);
        String name = JsoupUtil.getElementText(nameElements);

        Elements sexElements = table.selectXpath(StudentInfoXpath.SEX);
        String sex = JsoupUtil.getElementText(sexElements);

        Elements birthdayElements = table.selectXpath(StudentInfoXpath.BIRTHDAY);
        String birthday = JsoupUtil.getElementText(birthdayElements);

        Elements hometownElements = table.selectXpath(StudentInfoXpath.HOMETOWN);
        String hometown = JsoupUtil.getElementText(hometownElements);

        Elements nationalityElements = table.selectXpath(StudentInfoXpath.NATIONALITY);
        String nationality = JsoupUtil.getElementText(nationalityElements);

        Elements idNumberElements = table.selectXpath(StudentInfoXpath.ID_NUMBER);
        String idNumber = JsoupUtil.getElementText(idNumberElements);

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