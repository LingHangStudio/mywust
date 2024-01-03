package cn.wustlinghang.mywust.data.common;

import java.util.StringJoiner;

public final class Building implements WithIdData {
    public final String id;
    public final String name;
    public final Campus campus;

    public Building(String id, String name, Campus campus) {
        this.id = id;
        this.name = name;
        this.campus = campus;
    }

    public Building(String id, String name) {
        this.id = id;
        this.name = name;
        this.campus = new Campus("", "");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Building.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("campus=" + campus)
                .toString();
    }
}
