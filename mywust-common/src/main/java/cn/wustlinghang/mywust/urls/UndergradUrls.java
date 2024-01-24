package cn.wustlinghang.mywust.urls;

import lombok.Getter;

/**
 * <p>Bkjx（本科教学）系统对应的API常量列表（拼音咱就别吐槽了吧...）</p>
 * <p>其实是本科生用的教务处系统</p>
 */
@Getter
public class UndergradUrls {

    public static final String BKJX_BASE_URL = "https://bkjx.wust.edu.cn";

    public static final String BKJX_INDEX_URL = BKJX_BASE_URL;

    /**
     * 登录cookie获取url
     */
    public static final String BKJX_SESSION_COOKIE_API = BKJX_BASE_URL + "/jsxsd/?ticket=%s";
    public static final String BKJX_SESSION_COOKIE_BASE_URL = BKJX_BASE_URL + "/jsxsd/";

    /**
     * cookie测试url（空白页）
     */
    public static final String BKJX_TEST_API = BKJX_BASE_URL + "/jsxsd/framework/blankPage.jsp";

    /**
     * 学生信息url
     */
    public static final String BKJX_STUDENT_INFO_API = BKJX_BASE_URL + "/jsxsd/grxx/xsxx";

    /**
     * 成绩url
     */
    public static final String BKJX_SCORE_API = BKJX_BASE_URL + "/jsxsd/kscj/cjcx_list";

    /**
     * 培养计划url
     */
    public static final String BKJX_TRAINING_PLAN_API = BKJX_BASE_URL + "/jsxsd/pyfa/topyfamx";

    /**
     * 课表url
     */
    public static final String BKJX_COURSE_TABLE_API = BKJX_BASE_URL + "/jsxsd/xskb/xskb_list.do";

    /**
     * 单周课表url
     */
    public static final String BKJX_SINGLE_WEEK_COURSE_TABLE_API = BKJX_BASE_URL + "/jsxsd/framework/main_index_loadkb.jsp";

    /**
     * 学分修读计划首页url
     */
    public static final String BKJX_CREDIT_STATUS_INDEX_API = BKJX_BASE_URL + "/jsxsd/xxwcqk/xxwcqk_idxOnzh.do";

    /**
     * 学分修读计划url
     */
    public static final String BKJX_CREDIT_STATUS_API = BKJX_BASE_URL + "/jsxsd/xxwcqk/xxwcqkOnzh.do";

    /**
     * 考试活动列表url
     */
    public static final String BKJX_EXAM_ACTIVITY_LIST_API = BKJX_BASE_URL + "/jsxsd/kscj/hksq_query_ajax?&xnxq01id=%s";

    /**
     * 教学楼信息获取url
     */
    public static final String BKJX_BUILDING_LIST_API = BKJX_BASE_URL + "/jsxsd/kbcx/getJxlByAjax";

    /**
     * 缓考申请url
     */
    public static final String BKJX_EXAM_DELAY_APPLICATION_LIST_API = BKJX_BASE_URL + "/jsxsd/kscj/hksq_list";

    // -----------------------------------------------

    /**
     * 教室课表url
     */
    public static final String BKJX_CLASSROOM_COURSE_API = BKJX_BASE_URL + "/jsxsd/kbcx/kbxx_classroom_ifr";

    /**
     * 课程课表url
     */
    public static final String BKJX_ALL_COURSE_SCHEDULE_API = BKJX_BASE_URL + "/jsxsd/kbcx/kbxx_kc_ifr";

    /**
     * 教师课表url
     */
    public static final String BKJX_TEACHER_COURSE_API = BKJX_BASE_URL + "/jsxsd/kbcx/kbxx_teacher_ifr";

    /**
     * 班级课表url
     */
    public static final String BKJX_CLASS_COURSE_API = BKJX_BASE_URL + "/jsxsd/kbcx/kbxx_xzb_ifr";

    public static class Legacy {
        public static final String BKJX_INDEX = BKJX_BASE_URL;
        public static final String BKJX_DATA_STRING_API = BKJX_BASE_URL + "/Logon.do?method=logon&flag=sess";
        public static final String BKJX_SESSION_COOKIE_API = BKJX_BASE_URL + "/Logon.do?method=logon";
    }
}
