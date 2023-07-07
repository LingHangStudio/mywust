package cn.wustlinghang.mywust.core.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 一些需要用到的from参数，一般来说是写死固定的。
 * 别慌，都是脚本自动处理生成的代码，肯定不会是手写的。
 */
public class ConstantParams {
    /**
     * 默认节次模式
     */
    public static final String DEFAULT_TIME_MODEL = "9486203B90F3E3CBE0532914A8C03BE2";

    private static final Map<String, String> collegesTmp = new HashMap<>(27);
    public static final Map<String, String> COLLEGES = Collections.unmodifiableMap(collegesTmp);

    static {
        collegesTmp.put("00001", "资源与环境工程学院");
        collegesTmp.put("00002", "材料与冶金学院");
        collegesTmp.put("00003", "机械自动化学院");
        collegesTmp.put("00004", "信息科学与工程学院（人工智能学院）");
        collegesTmp.put("00005", "管理学院（恒大管理学院）");
        collegesTmp.put("00006", "法学与经济学院");
        collegesTmp.put("00007", "理学院");
        collegesTmp.put("00008", "城市建设学院");
        collegesTmp.put("00009", "医学院");
        collegesTmp.put("00012", "电子技术学院");
        collegesTmp.put("00013", "计算机科学与技术学院");
        collegesTmp.put("00014", "外国语学院");
        collegesTmp.put("00015", "体育学院（恒大足球学院）");
        collegesTmp.put("0D5444D56D8A46EDB75633181B2042A7", "图书馆");
        collegesTmp.put("26057DF4B9354C6EA85860F1357FE8EB", "工程训练中心");
        collegesTmp.put("00018", "生命科学与健康学院");
        collegesTmp.put("00019", "艺术与设计学院");
        collegesTmp.put("00023", "国际学院");
        collegesTmp.put("00024", "化学与化工学院");
        collegesTmp.put("00025", "汽车与交通工程学院");
        collegesTmp.put("00026", "临床学院");
        collegesTmp.put("0W6z90XTY6", "学生工作处");
        collegesTmp.put("jkwHhwCHLl", "马克思主义学院");
        collegesTmp.put("0C4B597EE18B418B9AA5C52FDAB3EC73", "香涛学院");
        collegesTmp.put("379A57BA53DD4F25BA7582CE3180C077", "公共卫生学院");
        collegesTmp.put("9AA8C9B34EE74DEC8E02F4554B1B5125", "研究生院");
        collegesTmp.put("86AC446406A94EF09DE1F8B7C17F994A", "全校");

        // 手动补充两个，虽然在这里没啥用，查不出什么东西出来
        collegesTmp.put("34EFAEAE2B6A470781B9BCFF364D3980", "党委宣传部");
        collegesTmp.put("9C6FD49D03D74209B062A632A860FECA", "创新创业学院");

    }

    private static final Map<String, String[]> collegePartsTmp = new HashMap<>();
    public static final Map<String, String[]> COLLEGE_PARTS = Collections.unmodifiableMap(collegePartsTmp);

    static {
        collegePartsTmp.put("00001", new String[]{"0101", "0105", "0106", "0107", "0150", "0151", "0152", "0153", "0198",});
        collegePartsTmp.put("00002", new String[]{"0201", "0202", "0203", "0204", "0205", "0206", "0250", "0251", "0252", "0253", "0254", "0255", "0298", "200099",});
        collegePartsTmp.put("00003", new String[]{"0302", "0304", "0305", "0307", "0308", "0309", "0350", "0398",});
        collegePartsTmp.put("00004", new String[]{"0401", "0402", "0403", "0404", "0405", "0406", "0450", "0452", "0498",});
        collegePartsTmp.put("00005", new String[]{"0501", "0502", "0503", "0504", "0505", "0506", "0507", "0550", "0598",});
        collegePartsTmp.put("00006", new String[]{"0602", "0603", "0604", "0607", "0609", "0610", "0650", "0698",});
        collegePartsTmp.put("00007", new String[]{"0701", "0702", "0703", "0750", "0751", "0752", "0753", "0754", "0798",});
        collegePartsTmp.put("00008", new String[]{"0802", "0803", "0806", "0807", "0811", "0850", "0851", "0852", "FB5251B1BD064A178FFB96C64DDAE87A", "0898",});
        collegePartsTmp.put("00009", new String[]{"0924", "0925", "0926", "0927", "0928", "0950", "0951", "0952", "0953", "0954", "0955", "0998",});
        collegePartsTmp.put("00012", new String[]{"200096", "200114",});
        collegePartsTmp.put("00013", new String[]{"1302", "1303", "1304", "1305", "1306", "1350", "1351", "1398",});
        collegePartsTmp.put("00014", new String[]{"1401", "1402", "1403", "1404", "1405", "1450", "1498",});
        collegePartsTmp.put("00015", new String[]{"1501", "1502", "1598",});
        collegePartsTmp.put("0D5444D56D8A46EDB75633181B2042A7", new String[]{"1601", "1698",});
        collegePartsTmp.put("26057DF4B9354C6EA85860F1357FE8EB", new String[]{"1701", "1798",});
        collegePartsTmp.put("00018", new String[]{"1801", "1850", "1898",});
        collegePartsTmp.put("00019", new String[]{"1904", "1906", "1908", "1950", "1998",});
        collegePartsTmp.put("00023", new String[]{"2101", "2102", "2103", "2104", "2105", "2198",});
        collegePartsTmp.put("00024", new String[]{"2202", "2203", "2204", "2206", "2250", "2251", "2252", "2253", "2254", "2298",});
        collegePartsTmp.put("00025", new String[]{"2301", "2302", "2303", "2350", "2351", "2352", "2398",});
        collegePartsTmp.put("00026", new String[]{"2401", "2402", "2403", "2404", "2405", "2406", "2407", "2408", "2410", "2411", "2412", "2413", "2498",});
        collegePartsTmp.put("0W6z90XTY6", new String[]{"2501", "2502", "2503", "2504", "2598",});
        collegePartsTmp.put("jkwHhwCHLl", new String[]{"5101", "5102", "5103", "5104", "5105", "5106", "5198",});
        collegePartsTmp.put("0C4B597EE18B418B9AA5C52FDAB3EC73", new String[]{});
        collegePartsTmp.put("379A57BA53DD4F25BA7582CE3180C077", new String[]{});
        collegePartsTmp.put("34EFAEAE2B6A470781B9BCFF364D3980", new String[]{"C53EBF84BD4240A5B320BEC25F90B9E5",});
        collegePartsTmp.put("9AA8C9B34EE74DEC8E02F4554B1B5125", new String[]{"6001", "6098",});
        collegePartsTmp.put("9C6FD49D03D74209B062A632A860FECA", new String[]{"9A52123E0AC842C1A0FEFB2E24359904",});
        collegePartsTmp.put("86AC446406A94EF09DE1F8B7C17F994A", new String[]{"0756C43634094A0FA92FE3FE0E1F0503",});
    }

    private static final Map<String, String> campusTmp = new HashMap<>(3);
    public static final Map<String, String> CAMPUS = Collections.unmodifiableMap(campusTmp);

    static {
        campusTmp.put("00001", "青山校区");
//            campusTmp.put("00002", "洪山校区");
//            campusTmp.put("00003", "医学院");
//            campusTmp.put("00004", "城市学院");
//            campusTmp.put("00005", "东湖教学区");
        campusTmp.put("00006", "黄家湖校区");
        campusTmp.put("00007", "附属医院");
//            campusTmp.put("00008", "武钢医院");
    }

    /**
     * 教室，目前只做了黄家湖、青山和附属医学院的数据，其他校区在制作时相关数据都是空的，
     * 需要更多信息请请求相关接口获取
     */
    private static final Map<String, String> qingshanBuildingTmp = new HashMap<>(10);
    public static final Map<String, String> QINGSHAN_BUILDINGS = Collections.unmodifiableMap(qingshanBuildingTmp);

    private static final Map<String, String> huangjiahuBuildingTmp = new HashMap<>(10);
    public static final Map<String, String> huangjiahu_BUILDINGS = Collections.unmodifiableMap(huangjiahuBuildingTmp);

    private static final Map<String, String> medicineSchoolBuildingTmp = new HashMap<>(10);
    public static final Map<String, String> medicineSchool_BUILDINGS = Collections.unmodifiableMap(medicineSchoolBuildingTmp);

    static {
        qingshanBuildingTmp.put("FE88C3D75F9C4C8CA50B82ACDF83530B", "科大雅苑C栋");
        qingshanBuildingTmp.put("3B76302812FD4262B8BD703814D0D8C3", "科大雅苑A栋");
        qingshanBuildingTmp.put("90C06D274E254A2CADC88DF1CEBD8396", "教十楼");
        qingshanBuildingTmp.put("00001", "主楼");
        qingshanBuildingTmp.put("00002", "教一楼");
        qingshanBuildingTmp.put("00003", "教二楼");
        qingshanBuildingTmp.put("00004", "教三楼");
        qingshanBuildingTmp.put("00005", "教四楼");
        qingshanBuildingTmp.put("00006", "教五楼");
        qingshanBuildingTmp.put("00007", "教六楼");
        qingshanBuildingTmp.put("00008", "图书馆");
        qingshanBuildingTmp.put("00009", "本部东院体育场");
        qingshanBuildingTmp.put("00010", "机械厂");
        qingshanBuildingTmp.put("00024", "本部西院体育场");
        qingshanBuildingTmp.put("00025", "本部北院体育场");
        qingshanBuildingTmp.put("00029", "教七楼");
        qingshanBuildingTmp.put("00031", "本部教二楼化学实验区");
        qingshanBuildingTmp.put("00032", "学院实验室");
        qingshanBuildingTmp.put("00051", "校本部停用教室");

        huangjiahuBuildingTmp.put("D1521DE0E4CF4641AB4C49CA39EF5E47", "教八楼(管理学院)");
        huangjiahuBuildingTmp.put("FBD19C52EC3B4A93A6268B815EFA9A5A", "教九楼(汽车学院)");
        huangjiahuBuildingTmp.put("00034", "恒大楼一区");
        huangjiahuBuildingTmp.put("00035", "恒大楼二区");
        huangjiahuBuildingTmp.put("00036", "恒大楼三区");
        huangjiahuBuildingTmp.put("00037", "黄家湖校区体育场");
        huangjiahuBuildingTmp.put("00041", "黄家湖校区体育馆");
        huangjiahuBuildingTmp.put("00042", "教二楼(理学院)");
        huangjiahuBuildingTmp.put("00043", "教三楼(计算机学院)");
        huangjiahuBuildingTmp.put("00045", "教四楼二区(外国语学院)");
        huangjiahuBuildingTmp.put("00046", "教四楼一区(文法学院)");
        huangjiahuBuildingTmp.put("00048", "教五楼一区");
        huangjiahuBuildingTmp.put("00049", "教五楼二区");
        huangjiahuBuildingTmp.put("00050", "教六楼(医学院)");
        huangjiahuBuildingTmp.put("00052", "黄家湖校区停用教室");
        huangjiahuBuildingTmp.put("D29468E2AD8241769F47D82913C537EA", "黄家湖校区工程训练中心");
        huangjiahuBuildingTmp.put("F9A375E92D814DD3BC2EF30E33C4907B", "教七楼(艺术学院)");
        huangjiahuBuildingTmp.put("DFC73608380C4079A44D4215BD81EB88", "教十一楼");

        medicineSchoolBuildingTmp.put("00047", "附属医院教学楼");
    }
}
