package cn.linghang.mywust.core.exception;

public class PasswordWornException extends BasicException {
    public PasswordWornException() {
    }

    public PasswordWornException(String message) {
        super(message);
    }

    public PasswordWornException(String message, Throwable cause) {
        super(message, cause);
    }
}
