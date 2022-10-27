package cn.linghang.mywust.core.request.undergrade;

import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.FormBodyBuilder;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>课表请求参数生成</p>
 * <p>由于请求参数生成部分过于庞大和丑陋，不适合放在总的Factory，因此单独拎出来了</p>
 *
 * @author lensfrex
 * @create 2022-10-27 10:17
 */
public class CourseTableRequestParamFactory {
    // 课表请求的谜之参数，貌似是固定的
    // 其实都可以靠解析html中id为Form1的表单获得(input和select两种所有元素的值)，这样可以防止系统作妖改掉这些值
    // 但为了快速起见，免去重复解析，直接使用也是没有什么大问题的，估计在很长的一段时间内这部分都是不会改的
    protected static final Map<String, String> COURSE_TABLE_MAGIC_QUERY_PARAMS = generateMagicQueryParam();

    private static final String MAGIC_PARAM_PREFIX_0 = "7DF471C4FF954CFA9C691580B8214B36-";
    private static final String MAGIC_PARAM_PREFIX_1 = "EEBED1036A7B4991BF01CBFD47908031-";
    private static final String MAGIC_PARAM_PREFIX_2 = "711BA240F1B0462C9359E3CC28D57EBE-";
    private static final String MAGIC_PARAM_PREFIX_3 = "2A9E45FD425B477AB74272EF70478E1A-";
    private static final String MAGIC_PARAM_PREFIX_4 = "91826EDF3A594498A9F761D685EEAE96-";
    private static final String MAGIC_PARAM_PREFIX_5 = "ACC171586F9245B09C86C589102423B4-";

    private static Map<String, String> generateMagicQueryParam() {
        Map<String, String> magicParams = new TreeMap<>(FormBodyBuilder.REPEATABLE_COMPARATOR);
        for (int i = 0; i < 6; i++) {
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_0 + i + "-1");
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_1 + i + "-1");
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_2 + i + "-1");
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_3 + i + "-1");
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_4 + i + "-1");
            magicParams.put("jx0415zbdiv_1", MAGIC_PARAM_PREFIX_5 + i + "-1");

            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_0 + i + "-2");
            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_1 + i + "-2");
            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_2 + i + "-2");
            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_3 + i + "-2");
            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_4 + i + "-2");
            magicParams.put("jx0415zbdiv_2", MAGIC_PARAM_PREFIX_5 + i + "-2");
        }

        magicParams.put("jx0404id", "");
        magicParams.put("cj0701id", "");
        magicParams.put("zc", "");
        magicParams.put("demo", "");
        magicParams.put("sfFD", "1");
        magicParams.put("kbjcmsid", "9486203B90F3E3CBE0532914A8C03BE2");

        return magicParams;
    }
}
