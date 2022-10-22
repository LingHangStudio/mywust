package cn.linghang.mywust.core.parser.undergraduate;

import cn.linghang.mywust.core.exception.HtmlPageParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.xpath.StudentInfoXpath;
import cn.linghang.mywust.model.undergrade.StudentInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StudentInfoPageParser implements Parser<StudentInfo> {

    public StudentInfo parse(String html) throws HtmlPageParseException {
        Document page = Jsoup.parse(html);
        Element table = page.getElementById("xjkpTable");
        if (table == null) {
            throw new HtmlPageParseException();
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
