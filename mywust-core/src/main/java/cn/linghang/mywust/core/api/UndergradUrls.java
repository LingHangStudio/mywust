package cn.linghang.mywust.core.api;

import lombok.Getter;

import java.util.*;

/**
 * <p>Bkjx（本科教学）系统对应的API常量列表（拼音咱就别吐槽了吧...）</p>
 * <p>其实是本科生用的教务处系统</p>
 */
@Getter
public class UndergradUrls {
    /**
     * 登录cookie获取url
     */
    public static final String BKJX_SESSION_COOKIE_API = "http://bkjx.wust.edu.cn/jsxsd/sso.jsp?ticket=%s";

    /**
     * cookie测试url（空白页）
     */
    public static final String BKJX_TEST_API = "http://bkjx.wust.edu.cn/jsxsd/framework/blankPage.jsp";

    /**
     * 学生信息url
     */
    public static final String BKJX_STUDENT_INFO_API = "http://bkjx.wust.edu.cn/jsxsd/grxx/xsxx";

    /**
     * 成绩url
     */
    public static final String BKJX_SCORE_API = "http://bkjx.wust.edu.cn/jsxsd/kscj/cjcx_list";

    /**
     * 培养计划url
     */
    public static final String BKJX_TRAINING_PLAN_API = "http://bkjx.wust.edu.cn/jsxsd/pyfa/topyfamx";

    /**
     * 课表url
     */
    public static final String BKJX_COURSE_TABLE_API = "http://bkjx.wust.edu.cn/jsxsd/xskb/xskb_list.do";

    /**
     * 学分修读计划首页url
     */
    public static final String BKJX_CREDIT_STATUS_INDEX_API = "http://bkjx.wust.edu.cn/jsxsd/xxwcqk/xxwcqk_idxOnzh.do";

    /**
     * 学分修读计划url
     */
    public static final String BKJX_CREDIT_STATUS_API = "http://bkjx.wust.edu.cn/jsxsd/xxwcqk/xxwcqkOnzh.do";

    /**
     * 考试活动列表url
     */
    public static final String BKJX_EXAM_ACTIVITY_LIST_API = "http://bkjx.wust.edu.cn/jsxsd/kscj/hksq_query_ajax?&xnxq01id=%s";

    /**
     * 教学楼信息获取url
     */
    public static final String BKJX_BUILDING_LIST_API = "http://bkjx.wust.edu.cn/jsxsd/kbcx/getJxlByAjax";

    /**
     * 缓考申请url
     */
    public static final String BKJX_EXAM_DELAY_APPLICATION_LIST_API = "http://bkjx.wust.edu.cn/jsxsd/kscj/hksq_list";

    // -----------------------------------------------

    /**
     * 教室课表url
     */
    public static final String BKJX_CLASSROOM_COURSE_API = "http://bkjx.wust.edu.cn/jsxsd/kbcx/kbxx_classroom_ifr";

    /**
     * 课程课表url
     */
    public static final String BKJX_ALL_COURSE_SCHEDULE_API = "http://bkjx.wust.edu.cn/jsxsd/kbcx/kbxx_kc_ifr";

    /**
     * 教师课表url
     */
    public static final String BKJX_TEACHER_COURSE_API = "http://bkjx.wust.edu.cn/jsxsd/kbcx/kbxx_teacher_ifr";

    /**
     * 班级课表url
     */
    public static final String BKJX_CLASS_COURSE_API = "http://bkjx.wust.edu.cn/jsxsd/kbcx/kbxx_xzb_ifr";

    public static class Legacy {
        public static final String BKJX_INDEX = "http://bkjx.wust.edu.cn";
        public static final String BKJX_DATA_STRING_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon&flag=sess";
        public static final String BKJX_SESSION_COOKIE_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon";
    }
}
