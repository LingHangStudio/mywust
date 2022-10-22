package cn.linghang.mywust.core.parser.undergraduate.xpath;

/**
 * <p>本科生学生信息接口用到的xpath</p>
 * <p>看着挺唬人的，其实直接浏览器选择元素复制xpath就行了</p>
 * <p>这里的xpath只要网站UI不整什么花活就不会出问题</p>
 *
 * @author lensfrex
 * @create 2022-10-22 22:16
 */
public class StudentInfoXpath {
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
