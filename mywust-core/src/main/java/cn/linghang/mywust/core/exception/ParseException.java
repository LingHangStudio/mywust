package cn.linghang.mywust.core.exception;

public class ParseException extends BasicException {
    public ParseException() {
        super("解析数据失败");
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
