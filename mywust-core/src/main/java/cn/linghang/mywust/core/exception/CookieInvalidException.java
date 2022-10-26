package cn.linghang.mywust.core.exception;

public class CookieInvalidException extends BasicException {
    public CookieInvalidException() {
    }

    public CookieInvalidException(String message) {
        super(message);
    }

    public CookieInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
