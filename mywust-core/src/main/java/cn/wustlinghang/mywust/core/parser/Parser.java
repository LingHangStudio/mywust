package cn.wustlinghang.mywust.core.parser;

import cn.wustlinghang.mywust.exception.ParseException;

public interface Parser<T> {
    public T parse(String html) throws ParseException;
}
