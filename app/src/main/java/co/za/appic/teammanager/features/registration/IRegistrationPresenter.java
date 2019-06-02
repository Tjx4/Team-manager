package co.za.appic.teammanager.features.registration;

import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public interface IRegistrationPresenter  {
    void registerNewUser(String name, String surname, String email, String password, String confirmedPassword);
    void addSupervisorToDB(SupervisorModel supervisor);
    void addWorkerToDB(WorkerModel worker);
    void setUserType(EmployeeType employeeType);
}