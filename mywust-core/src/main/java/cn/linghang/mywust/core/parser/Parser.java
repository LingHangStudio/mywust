package cn.linghang.mywust.core.parser;

import cn.linghang.mywust.core.exception.ParseException;

public interface Parser<T> {
    public T parse(String html) throws ParseException;
}
