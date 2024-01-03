package cn.wustlinghang.mywust.data.common;

import java.util.StringJoiner;

public final class Campus implements WithIdData {
    public final String id;
    public final String name;

    public Campus(String id, String name) {
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
        return new StringJoiner(", ", Campus.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
