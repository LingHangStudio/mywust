package cn.linghang.mywust.core.exception;

public class ParseException extends BasicException {
    private final String rawData;

    public ParseException(String rawData) {
        super("解析数据失败");
        this.rawData = rawData;
    }

    public ParseException(String message, String rawData) {
        super(message);
        this.rawData = rawData;
    }

    public ParseException(String message, Throwable cause, String rawData) {
        super(message, cause);
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }
}
