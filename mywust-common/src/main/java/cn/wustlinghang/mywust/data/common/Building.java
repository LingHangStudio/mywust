package cn.wustlinghang.mywust.data.common;

import java.util.StringJoiner;

public final class Building implements WithIdData {
    public String id;
    public String name;
    public Campus campus;

    public Building() {
    }

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

    public Campus getCampus() {
        return campus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
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
