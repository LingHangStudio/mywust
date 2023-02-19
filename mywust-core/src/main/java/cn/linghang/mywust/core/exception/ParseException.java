package cn.linghang.mywust.core.exception;

import java.util.StringJoiner;

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

    @Override
    public String toString() {
        return new StringJoiner(", ", ParseException.class.getSimpleName() + "[", "]")
                .add("message='" + getMessage() + "'")
                .add("rawData='" + (rawData.length() > 32 ? rawData.substring(0, 32) : rawData) + "'")
                .toString();
    }
}
