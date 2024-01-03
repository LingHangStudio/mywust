package cn.wustlinghang.mywust.network.entitys;

import cn.wustlinghang.mywust.util.RepeatableComparator;
import cn.wustlinghang.mywust.util.StringUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FormBodyBuilder {
    public static final Comparator<String> REPEATABLE_COMPARATOR = new RepeatableComparator();

    private final Map<String, String> queryParams;

    public FormBodyBuilder(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public FormBodyBuilder() {
        this(false);
    }

    public FormBodyBuilder(boolean repeatable) {
        if (repeatable) {
            this.queryParams = new TreeMap<>(REPEATABLE_COMPARATOR);
        } else {
            this.queryParams = new HashMap<>();
        }
    }

    public FormBodyBuilder(int initSize) {
        this.queryParams = new HashMap<>(initSize);
    }

    public FormBodyBuilder add(Map<String, String> params) {
        this.queryParams.putAll(params);
        return this;
    }

    public FormBodyBuilder add(String key, String value) {
        this.queryParams.put(key, value == null ? "" : value);
        return this;
    }

    public Map<String, String> buildMap() {
        return this.queryParams;
    }

    public String buildString() {
        return StringUtil.generateQueryString(this.queryParams);
    }

}