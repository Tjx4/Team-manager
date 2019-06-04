package co.za.appic.teammanager.enums;

public enum TaskStatus {
    pending(1, "Pending"),
    inprogress(2, "Inprogress"),
    completed(3, "Completed");

    int id;
    String name;

    TaskStatus(int id, String name) {
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
