package co.za.appic.teammanager.models;

import java.util.List;

import co.za.appic.teammanager.enums.EmployeeType;

public class WorkerModel extends UserModel {

    private List<String> teams;

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }
}
