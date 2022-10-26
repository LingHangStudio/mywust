package cn.linghang.mywust.core.exception;

public class BasicException extends Exception {
    public BasicException() {
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }
}
