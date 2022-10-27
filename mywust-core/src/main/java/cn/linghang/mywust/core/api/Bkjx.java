package cn.linghang.mywust.core.api;

import lombok.Getter;

/**
 * <p>Bkjx（本科教学）系统对应的API常量列表（拼音咱就别吐槽了吧...）</p>
 * <p>其实是本科生用的教务处系统</p>
 */
@Getter
public class Bkjx {
    public static final String BKJX_SESSION_COOKIE_API = "http://bkjx.wust.edu.cn/jsxsd/sso.jsp?ticket=%s";

    public static final String BKJX_TEST_API = "http://bkjx.wust.edu.cn/jsxsd/xxwcqk/xxwcqk_idxOnzh.do";

    public static final String BKJX_STUDENT_INFO_API = "http://bkjx.wust.edu.cn/jsxsd/grxx/xsxx";

    public static final String BKJX_EXAM_INFO_API = "http://bkjx.wust.edu.cn/jsxsd/kscj/cjcx_list";

    public static final String BKJX_SCHEME_API = "http://bkjx.wust.edu.cn/jsxsd/pyfa/topyfamx";

    public static final String BKJX_COURSE_TABLE_API = "http://bkjx.wust.edu.cn/jsxsd/xskb/xskb_list.do";

    public static class Legacy {
        public static final String BKJX_INDEX = "http://bkjx.wust.edu.cn";
        public static final String BKJX_DATA_STRING_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon&flag=sess";
        public static final String BKJX_SESSION_COOKIE_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon";
    }
}
