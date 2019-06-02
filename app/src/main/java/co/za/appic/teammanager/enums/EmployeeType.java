package co.za.appic.teammanager.enums;

public enum EmployeeType {
    worker(1, "Worker"),
    supervisor(2, "Supervisor");

    int userId;
    String userType;

    EmployeeType(int userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
