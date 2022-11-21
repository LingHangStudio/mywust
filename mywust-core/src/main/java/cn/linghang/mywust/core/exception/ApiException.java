package cn.linghang.mywust.core.exception;

public class ApiException extends BasicException {
    private final int code;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "接口调用异常: " + code;
    }

    public static class ApiExceptionCode {
        // 统一认证的异常
        public static final int UNI_LOGIN_PASSWORD_WRONG = 100101;
        public static final int UNI_LOGIN_USER_NOT_EXISTS = 100102;
        public static final int UNI_LOGIN_USER_BANNED = 100103;

        // 共有异常码：cookie无效
        // 放一起了
        public static final int BKJX_COOKIE_INVALID = 110101;
        public static final int LIBRARY_COOKIE_INVALID = 12101;
        public static final int PHYSICS_COOKIE_INVALID = 130101;
        public static final int GRADE_COOKIE_INVALID = 140101;
    }

}