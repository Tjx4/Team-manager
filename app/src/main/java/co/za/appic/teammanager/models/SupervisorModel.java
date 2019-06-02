package co.za.appic.teammanager.models;

import co.za.appic.teammanager.enums.EmployeeType;

public class SupervisorModel extends UserModel {

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.supervisor;
    }
}
