package co.za.appic.teammanager.models;

import java.util.List;

import co.za.appic.teammanager.enums.EmployeeType;

public class WorkerModel extends UserModel {

    private List<String> teams;

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.worker;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }
}
