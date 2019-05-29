package co.za.appic.teammanager.models;

import co.za.appic.teammanager.enums.UserType;

public class SupervisorModel extends UserModel {

    @Override
    public UserType getUserType() {
        return UserType.supervisor;
    }
}
