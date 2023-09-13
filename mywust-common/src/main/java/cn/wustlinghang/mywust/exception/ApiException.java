package cn.wustlinghang.mywust.exception;

public class ApiException extends BasicException {
    private final Code code;

    public ApiException(Code code) {
        super(code.toString());
        this.code = code;
    }

    public ApiException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public static ApiException create(Code code, String message) {
        return new ApiException(code, message);
    }

    public static ApiException create(Code code) {
        return new ApiException(code);
    }

    public Code getCode() {
        return code;
    }

    public int getCodeValue() {
        return code.value;
    }

    public String getCodeDescribe() {
        return code.describe;
    }

    @Override
    public String toString() {
        return "接口调用异常: " + code + ";" + ((getMessage() == null && code == Code.UNKNOWN_EXCEPTION) ? "" : getMessage());
    }

    public enum Code {
        /**
         * 未知的API异常
         */
        UNKNOWN_EXCEPTION(-1, "未知错误(开发又有活干啦)"),

        /**
         * 网络异常
         */
        NETWORK_EXCEPTION(-2, "网络错误..."),

        /**
         * 输入参数错误
         */
        PARAM_WRONG_EXCEPTION(-3, "输入参数错误..."),

        // --------------------------------
        // 统一认证的异常(本科生、图书馆)

        /**
         * 密码错误
         */
        UNI_LOGIN_PASSWORD_WRONG(100100, "统一认证登录: 密码错误"),

        /**
         * 用户不存在
         */
        UNI_LOGIN_USER_NOT_EXISTS(100101, "统一认证登录: 用户不存在"),

        /**
         * 用户登录被封禁
         */
        UNI_LOGIN_USER_BANNED(100102, "统一认证登录: 用户被封禁"),

        /**
         * 用户账号停用
         */
        UNI_LOGIN_USER_DISABLED(100103, "统一认证登录: 用户已停用"),

        /**
         * 用户账号需要更改密码
         */
        UNI_LOGIN_NEED_CHANGE_PASSWORD(100104, "统一认证登录: 用户账号密码需要修改"),

        // 下面的几个几乎不会遇到，但是从官网源码来看是有可能的

        /**
         * 用户账号不唯一
         */
        UNI_LOGIN_USER_NOT_ONLY(100105, "统一认证登录: 用户不唯一，请联系学校处理"),

        /**
         * 用户未注册
         */
        UNI_LOGIN_NO_REGISTER(100106, "统一认证登录: 用户未注册"),

        /**
         * 用户账号设置了TFA验证，不能直接用密码账号登录
         */
        UNI_LOGIN_NEED_TFA(100107, "统一认证登录: 用户账号需要TFA二步验证"),

        /**
         * 专属选课时间段账号被禁用
         */
        UNDERGRAD_BANNED_IN_EXCLUSIVE_TIME(100108, "本科生登录：专属选课时间段账号被禁用"),

        /**
         * 用户名信息不存在（新生信息未录入或者老生删档）
         */
        UNDERGRAD_USERINFO_NOT_EXISTS(100109, "本科生登录：用户名信息（新生信息未录入或者老生删档）"),

        // --------------------------------
        // 共有异常码：cookie无效

        /**
         * Cookie无效
         */
        COOKIE_INVALID(100101, "Cookie无效"),

        // --------------------------------
        // 本科生API异常代码

        /**
         *
         */
        BKJX_LEGACY_LOGIN_PASSWORD_WRONG(110101, "本科教学系统-旧版登录方式：密码错误"),

        /**
         * 需要评教
         */
        BKJX_COURSE_NEED_EVALUATE(110102, "需要评教"),

        // --------------------------------
        // 物理实验系统API异常代码

        /**
         * 物理实验系统密码错误
         */
        PHYSICS_PASSWORD_WRONG(130100, "物理实验系统登录: 密码错误"),

        /**
         * 物理实验系统用户不存在于当前学期
         */
        PHYSICS_NOT_CURRENT_TERM(130101, "物理实验系统登录: 当前用户不存在于当前学期"),

        // --------------------------------
        // 研究生API异常代码

        /**
         * 研究生密码错误
         */
        GRADUATE_PASSWORD_WRONG(140100, "研究生登录: 密码错误"),

        /**
         * 研究生验证码错误
         */
        GRADUATE_CAPTCHA_WRONG(140101, "研究生登录: 验证码错误"),

        // --------------------------------
        // 图书馆API异常代码

        // --------------------------------
        // 内部API异常代码
        INTERNAL_EXCEPTION(160100, "内部服务出错...后端要背锅"),
        ;

        private final int value;
        private final String describe;

        Code(int value, String describe) {
            this.value = value;
            this.describe = describe;
        }

        @Override
        public String toString() {
            return String.format("%s(%d)", describe, value);
        }
    }
}