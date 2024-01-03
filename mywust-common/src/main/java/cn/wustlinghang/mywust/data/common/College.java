package cn.wustlinghang.mywust.data.common;

import java.util.StringJoiner;

public final class College implements WithIdData {
    public final String id;
    public final String name;

    public College(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", College.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
