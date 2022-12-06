package cn.linghang.mywust.core.exception;

public class ApiException extends BasicException {
    private final Code code;

    public ApiException(Code code) {
        this.code = code;
    }

    public ApiException(Code code, String message) {
        super(message);
        this.code = code;
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
        return "接口调用异常: " + code;
    }

    public enum Code {
        /**
         * 未知的API异常
         */
        UNKNOWN_EXCEPTION(-1, "未知错误(开发又有活干啦)"),

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

        // --------------------------------
        // 共有异常码：cookie无效

        /**
         * Cookie无效
         */
        COOKIE_INVALID(100101, "Cookie无效"),

        // --------------------------------
        // 本科生API异常代码

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

        // --------------------------------
        // 研究生API异常代码

        /**
         * 研究生密码错误
         */
        GRADUATE_PASSWORD_WRONG(140100, "研究生登录: 密码错误"),
        ;

        // --------------------------------
        // 图书馆API异常代码

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