package co.za.appic.teammanager.enums;

public enum PriorityLevel {
    low(1, "Low priority"),
    medium(1, "Medium priority"),
    high(1, "High priority");

    int id;
    String name;

    PriorityLevel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
