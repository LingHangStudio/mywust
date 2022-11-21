package cn.linghang.mywust.core.parser.postgraduate;

import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
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
        student.setStudentNumber(this.getAttr(studentNumberElement, "value"));

//        Element gradeElement = table.getElementById("txtnj");
//        int grade = Integer.parseInt(this.getAttr(gradeElement, "value"));

        Element idNumberElement = table.getElementById("txtsfzh");
        student.setIdNumber(this.getAttr(idNumberElement, "value"));

        Element nationalityElement = table.getElementById("drpmz");
        student.setNationality(this.getSelectContent(nationalityElement));

        Element hometownElement = table.getElementById("txtjg");
        student.setHometown(this.getAttr(hometownElement, "value"));

        Element nameElement = table.getElementById("txtxm");
        student.setName(this.getAttr(nameElement, "value"));

        Element birthdayElement = table.getElementById("lblcsrq");
        student.setBirthday(this.getText(birthdayElement));

        Element collegeElement = table.getElementById("droyx");
        student.setCollege(this.getSelectContent(collegeElement));

        Element majorElement = table.getElementById("drozy");
        student.setMajor(this.getSelectContent(majorElement));

        Element genderElement = table.getElementById("droxb");

        student.setSex(this.getSelectContent(genderElement));

        // 研究生没有班级
        student.setClazz("");

        return student;
    }

    /**
     * 从Element中拿到指定的标签值
     * @param element 元素对象
     * @param key 标签值的key
     * @return 相应的值，若element为空则返回空字符串
     */
    private String getAttr(Element element, String key) {
        if (element == null) {
            return "";
        } else {
            return element.attr(key);
        }
    }

    /**
     * 从Element中拿到指定的文本内容
     * @param element 元素对象
     * @return 相应的值，若element为空则返回空字符串
     */
    public String getText(Element element) {
        if (element == null) {
            return "";
        } else {
            return element.ownText();
        }
    }

    /**
     * 从select类型的Element中拿取到已选中的选项值
     * @param element 元素对象
     * @return 相应的值，若element为空则返回空字符串
     */
    public String getSelectContent(Element element) {
        if (element == null) {
            return "";
        } else {
            return element.getElementsByAttributeValue("selected", "selected")
                    .get(0)
                    .ownText();
        }
    }
}
