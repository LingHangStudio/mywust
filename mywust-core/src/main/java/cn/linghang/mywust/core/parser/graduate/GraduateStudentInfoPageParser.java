package cn.linghang.mywust.core.parser.graduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.util.JsoupUtil;
import cn.linghang.mywust.model.global.StudentInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class GraduateStudentInfoPageParser implements Parser<StudentInfo> {

    private static final String INFO_TABLE_XPATH = "//*[@id=\"Form1\"]/table/tbody/tr[2]/td/table/tbody/tr[2]/td/table";

    @Override
    public StudentInfo parse(String html) throws ParseException {
        Element table = Jsoup.parse(html).selectXpath(INFO_TABLE_XPATH).get(0);

        StudentInfo student = new StudentInfo();

        Element studentNumberElement = table.getElementById("txtxh");
        student.setStudentNumber(JsoupUtil.getAttr(studentNumberElement, "value"));

//        Element gradeElement = table.getElementById("txtnj");
//        int grade = Integer.parseInt(JsoupUtil.getAttr(gradeElement, "value"));

        Element idNumberElement = table.getElementById("txtsfzh");
        student.setIdNumber(JsoupUtil.getAttr(idNumberElement, "value"));

        Element nationalityElement = table.getElementById("drpmz");
        student.setNationality(JsoupUtil.getSelectContent(nationalityElement));

        Element hometownElement = table.getElementById("txtjg");
        student.setHometown(JsoupUtil.getAttr(hometownElement, "value"));

        Element nameElement = table.getElementById("txtxm");
        student.setName(JsoupUtil.getAttr(nameElement, "value"));

        Element birthdayElement = table.getElementById("lblcsrq");
        student.setBirthday(JsoupUtil.getText(birthdayElement));

        Element collegeElement = table.getElementById("droyx");
        student.setCollege(JsoupUtil.getSelectContent(collegeElement));

        Element majorElement = table.getElementById("drozy");
        student.setMajor(JsoupUtil.getSelectContent(majorElement));

        Element genderElement = table.getElementById("droxb");

        student.setSex(JsoupUtil.getSelectContent(genderElement));

        // 研究生没有班级
        student.setClazz("");

        return student;
    }
}
