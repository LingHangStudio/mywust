package cn.linghang.mywust.model.global;

import java.util.StringJoiner;

public final class Building {
    public final String id;
    public final String name;
    public final Campus campus;

    public Building(String id, String name, Campus campus) {
        this.id = id;
        this.name = name;
        this.campus = campus;
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
