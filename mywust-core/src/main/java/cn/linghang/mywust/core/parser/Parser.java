package cn.linghang.mywust.core.parser;

import cn.linghang.mywust.exception.ParseException;

public interface Parser<T> {
    public T parse(String html) throws ParseException;
}
