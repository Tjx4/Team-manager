package co.za.appic.teammanager.helpers;

import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class ConverterHelper {

    public static SupervisorModel getSupervisorFromUser (UserModel userModel){
        SupervisorModel supervisorModel = new SupervisorModel();
        supervisorModel.setEmployeeId(userModel.getEmployeeId());
        supervisorModel.setName(userModel.getName());
        supervisorModel.setSurname(userModel.getSurname());
        supervisorModel.setGender(userModel.getGender());
        supervisorModel.setEmployeeType(userModel.getEmployeeType());
        return supervisorModel;
    }

    public static WorkerModel getWorkerFromUser(UserModel userModel){
        WorkerModel workerModel = new WorkerModel();
        workerModel.setEmployeeId(userModel.getEmployeeId());
        workerModel.setName(userModel.getName());
        workerModel.setSurname(userModel.getSurname());
        workerModel.setGender(userModel.getGender());
        workerModel.setEmployeeType(userModel.getEmployeeType());
        return workerModel;
    }
}