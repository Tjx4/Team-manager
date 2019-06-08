package co.za.appic.teammanager.enums;

import co.za.appic.teammanager.constants.Constants;

public enum EmployeeType {
    worker(1, "Worker", Constants.WORKERS_TABLE),
    supervisor(2, "Supervisor", Constants.SUPERVISORS_TABLE);

    int userId;
    String userType;
    String dbTable;

    EmployeeType(int userId, String userType, String dbTable) {
        this.userId = userId;
        this.userType = userType;
        this.dbTable = dbTable;
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

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }
}
