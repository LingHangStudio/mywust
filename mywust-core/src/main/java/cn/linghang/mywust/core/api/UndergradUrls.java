package cn.linghang.mywust.core.api;

import cn.linghang.mywust.model.global.Building;
import cn.linghang.mywust.model.global.Campus;
import cn.linghang.mywust.model.global.College;
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
     * 缓考申请url
     */
    public static final String BKJX_EXAM_DELAY_APPLICATION_LIST_API = "http://bkjx.wust.edu.cn/jsxsd/kscj/hksq_list";

    public static class Legacy {
        public static final String BKJX_INDEX = "http://bkjx.wust.edu.cn";
        public static final String BKJX_DATA_STRING_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon&flag=sess";
        public static final String BKJX_SESSION_COOKIE_API = "http://bkjx.wust.edu.cn/Logon.do?method=logon";
    }

    /**
     * 一些需要用到的from参数，一般来说是写死固定的。
     */
    // 别慌，大部分都是脚本自动生成的代码，肯定不会是手写的。
    public static class ConstantParams {
        /**
         * 默认节次模式
         */
        public static final String DEFAULT_TIME_MODEL = "9486203B90F3E3CBE0532914A8C03BE2";

        private static final List<College> collegesTmp = new ArrayList<>(27);
        public static final List<College> COLLEGES = Collections.unmodifiableList(collegesTmp);

        static {
            collegesTmp.add(new College("00001", "[01]资源与环境工程学院"));
            collegesTmp.add(new College("00002", "[02]材料与冶金学院"));
            collegesTmp.add(new College("00003", "[03]机械自动化学院"));
            collegesTmp.add(new College("00004", "[04]信息科学与工程学院（人工智能学院）"));
            collegesTmp.add(new College("00005", "[05]管理学院（恒大管理学院）"));
            collegesTmp.add(new College("00006", "[06]法学与经济学院"));
            collegesTmp.add(new College("00007", "[07]理学院"));
            collegesTmp.add(new College("00008", "[08]城市建设学院"));
            collegesTmp.add(new College("00009", "[09]医学院"));
            collegesTmp.add(new College("00012", "[12]电子技术学院"));
            collegesTmp.add(new College("00013", "[13]计算机科学与技术学院"));
            collegesTmp.add(new College("00014", "[14]外国语学院"));
            collegesTmp.add(new College("00015", "[15]体育学院（恒大足球学院）"));
            collegesTmp.add(new College("0D5444D56D8A46EDB75633181B2042A7", "[16]图书馆"));
            collegesTmp.add(new College("26057DF4B9354C6EA85860F1357FE8EB", "[17]工程训练中心"));
            collegesTmp.add(new College("00018", "[18]生命科学与健康学院"));
            collegesTmp.add(new College("00019", "[19]艺术与设计学院"));
            collegesTmp.add(new College("00023", "[21]国际学院"));
            collegesTmp.add(new College("00024", "[22]化学与化工学院"));
            collegesTmp.add(new College("00025", "[23]汽车与交通工程学院"));
            collegesTmp.add(new College("00026", "[24]临床学院"));
            collegesTmp.add(new College("0W6z90XTY6", "[25]学生工作处"));
            collegesTmp.add(new College("jkwHhwCHLl", "[51]马克思主义学院"));
            collegesTmp.add(new College("0C4B597EE18B418B9AA5C52FDAB3EC73", "[52]香涛学院"));
            collegesTmp.add(new College("379A57BA53DD4F25BA7582CE3180C077", "[54]公共卫生学院"));
            collegesTmp.add(new College("9AA8C9B34EE74DEC8E02F4554B1B5125", "[60]研究生院"));
            collegesTmp.add(new College("86AC446406A94EF09DE1F8B7C17F994A", "[98]全校"));
        }

        private static final List<Campus> campusTmp = new ArrayList<>(3);
        public static final List<Campus> CAMPUS = Collections.unmodifiableList(campusTmp);

        static {
            campusTmp.add(new Campus("00001", "青山校区"));
//            tmp.add(new Campus("00002", "洪山校区");
//            tmp.add(new Campus("00003", "医学院");
//            tmp.add(new Campus("00004", "城市学院");
//            tmp.add(new Campus("00005", "东湖教学区");
            campusTmp.add(new Campus("00006", "黄家湖校区"));
            campusTmp.add(new Campus("00007", "附属医院"));
//            tmp.add(new Campus("00008", "武钢医院");
        }

        /**
         * 教室，目前只做了黄家湖、青山和附属医学院的数据，其他校区在制作时相关数据都是空的，
         * 需要更多信息请请求相关接口获取
         */
        private static final Map<Campus, List<Building>> buildingTmp = new HashMap<>(3);
        public static final Map<Campus, List<Building>> BUILDINGS = Collections.unmodifiableMap(buildingTmp);

        static {
            Campus qingshan = CAMPUS.get(0);
            List<Building> qingshanBuildings = new ArrayList<>(10);
            qingshanBuildings.add(new Building("FE88C3D75F9C4C8CA50B82ACDF83530B", "科大雅苑C栋", qingshan));
            qingshanBuildings.add(new Building("3B76302812FD4262B8BD703814D0D8C3", "科大雅苑A栋", qingshan));
            qingshanBuildings.add(new Building("90C06D274E254A2CADC88DF1CEBD8396", "教十楼", qingshan));
            qingshanBuildings.add(new Building("00001", "主楼", qingshan));
            qingshanBuildings.add(new Building("00002", "教一楼", qingshan));
            qingshanBuildings.add(new Building("00003", "教二楼", qingshan));
            qingshanBuildings.add(new Building("00004", "教三楼", qingshan));
            qingshanBuildings.add(new Building("00005", "教四楼", qingshan));
            qingshanBuildings.add(new Building("00006", "教五楼", qingshan));
            qingshanBuildings.add(new Building("00007", "教六楼", qingshan));
            qingshanBuildings.add(new Building("00008", "图书馆", qingshan));
            qingshanBuildings.add(new Building("00009", "本部东院体育场", qingshan));
            qingshanBuildings.add(new Building("00010", "机械厂", qingshan));
            qingshanBuildings.add(new Building("00024", "本部西院体育场", qingshan));
            qingshanBuildings.add(new Building("00025", "本部北院体育场", qingshan));
            qingshanBuildings.add(new Building("00029", "教七楼", qingshan));
            qingshanBuildings.add(new Building("00031", "本部教二楼化学实验区", qingshan));
            qingshanBuildings.add(new Building("00032", "学院实验室", qingshan));
            qingshanBuildings.add(new Building("00051", "校本部停用教室", qingshan));
            buildingTmp.put(qingshan, qingshanBuildings);

            Campus huangjiahu = CAMPUS.get(1);
            List<Building> huangjiahuBuildings = new ArrayList<>(10);
            huangjiahuBuildings.add(new Building("D1521DE0E4CF4641AB4C49CA39EF5E47", "教八楼(管理学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("FBD19C52EC3B4A93A6268B815EFA9A5A", "教九楼(汽车学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00034", "恒大楼一区", huangjiahu));
            huangjiahuBuildings.add(new Building("00035", "恒大楼二区", huangjiahu));
            huangjiahuBuildings.add(new Building("00036", "恒大楼三区", huangjiahu));
            huangjiahuBuildings.add(new Building("00037", "黄家湖校区体育场", huangjiahu));
            huangjiahuBuildings.add(new Building("00041", "黄家湖校区体育馆", huangjiahu));
            huangjiahuBuildings.add(new Building("00042", "教二楼(理学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00043", "教三楼(计算机学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00045", "教四楼二区(外国语学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00046", "教四楼一区(文法学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00048", "教五楼一区", huangjiahu));
            huangjiahuBuildings.add(new Building("00049", "教五楼二区", huangjiahu));
            huangjiahuBuildings.add(new Building("00050", "教六楼(医学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("00052", "黄家湖校区停用教室", huangjiahu));
            huangjiahuBuildings.add(new Building("D29468E2AD8241769F47D82913C537EA", "黄家湖校区工程训练中心", huangjiahu));
            huangjiahuBuildings.add(new Building("F9A375E92D814DD3BC2EF30E33C4907B", "教七楼(艺术学院)", huangjiahu));
            huangjiahuBuildings.add(new Building("DFC73608380C4079A44D4215BD81EB88", "教十一楼", huangjiahu));
            buildingTmp.put(huangjiahu, huangjiahuBuildings);

            Campus medicineSchool = CAMPUS.get(2);
            List<Building> medicineSchoolBuildings = new ArrayList<>(1);
            medicineSchoolBuildings.add(new Building("00047", "附属医院教学楼", medicineSchool));
            buildingTmp.put(medicineSchool, medicineSchoolBuildings);
        }
    }
}
