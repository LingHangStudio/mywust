package cn.linghang.mywust.network.entitys;

import cn.linghang.mywust.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FormBodyBuilder {
    private final Map<String, String> queryParams;

    public FormBodyBuilder(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public FormBodyBuilder() {
        this.queryParams = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public FormBodyBuilder(int initSize) {
        this.queryParams = new HashMap<>(initSize);
    }

    public FormBodyBuilder addQueryParams(Map<String, String> params) {
        this.queryParams.putAll(params);
        return this;
    }

    public FormBodyBuilder addQueryParam(String key, String value) {
        this.queryParams.put(key, value);
        return this;
    }

    public Map<String, String> build() {
        return this.queryParams;
    }

    public String buildAndToString() {
        return StringUtil.generateQueryString(this.queryParams);
    }
}
