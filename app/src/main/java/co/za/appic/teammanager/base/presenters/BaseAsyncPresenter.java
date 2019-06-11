package co.za.appic.teammanager.base.presenters;

import com.google.firebase.database.DataSnapshot;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public abstract class BaseAsyncPresenter extends BasePresenter {

    protected boolean isBusy;

    public BaseAsyncPresenter(BaseView baseView) {
        super(baseView);
    }

    protected UserModel getUserFromDataSnapshot(DataSnapshot chatSnapshot) {
        try {
            UserModel userModel = new UserModel();
            setCommonUserDetails(chatSnapshot, userModel);
            return userModel;
        }
        catch (Exception e){
            return null;
        }
    }

    protected WorkerModel getWorkerFromDataSnapshot(DataSnapshot chatSnapshot) {
        try {
            WorkerModel workerModel = new WorkerModel();
            setCommonUserDetails(chatSnapshot, workerModel);
            // Set teams
            return workerModel;
        }
        catch (Exception e){
            return null;
        }
    }

    protected SupervisorModel getSupervisorFromDataSnapshot(DataSnapshot chatSnapshot) {
        try {
            SupervisorModel supervisorModel = new SupervisorModel();
            setCommonUserDetails(chatSnapshot, supervisorModel);
            return supervisorModel;
        }
        catch (Exception e){
            return null;
        }
    }

    protected void setCommonUserDetails(DataSnapshot chatSnapshot, UserModel user) {
        String fbId = chatSnapshot.getKey();
        user.setFbId(fbId);
        String employeeId = chatSnapshot.child(Constants.DB_EMPLOYEE_ID).getValue().toString();
        user.setEmployeeId(employeeId);
        String name = chatSnapshot.child(Constants.DB_NAME).getValue().toString();
        user.setName(name);
        String surname = chatSnapshot.child(Constants.DB_SURNAME).getValue().toString();
        user.setSurname(surname);
        String profilePic = chatSnapshot.child(Constants.DB_PROFILE_PIC).getValue().toString();
        user.setProfilePic(profilePic);
        int gender = Integer.parseInt(chatSnapshot.child(Constants.DB_GENDER).getValue().toString());
        user.setGender(UserGender.values()[--gender]);
        String mobile = chatSnapshot.child(Constants.DB_MOBILE).getValue().toString();
        user.setMobile(mobile);
        String email = chatSnapshot.child(Constants.DB_EMAIL).getValue().toString();
        user.setEmail(email);
        int employeeType = Integer.parseInt(chatSnapshot.child(Constants.DB_EMPLOYEE_TYPE).getValue().toString());
        user.setEmployeeType(EmployeeType.values()[--employeeType]);
    }

    protected TaskModel getTaskFromDataSnapsho(DataSnapshot chatSnapshot) {

        TaskModel currentTask = new TaskModel();

        String id =  chatSnapshot.getKey();
        currentTask.setId(id);

        String description = chatSnapshot.child(Constants.DB_TASK_DESCRIPTION).getValue().toString();
        currentTask.setDescription(description);

        String worker = chatSnapshot.child(Constants.DB_WORKER).getValue().toString();
        currentTask.setWorker(worker);

        String supervisor = chatSnapshot.child(Constants.DB_SUPERVISOR).getValue().toString();
        currentTask.setSupervisor(supervisor);

        int priorityId = Integer.parseInt(chatSnapshot.child(Constants.DB_TASK_PRIORITY).getValue().toString());
        PriorityLevel priority = PriorityLevel.values()[--priorityId];
        currentTask.setPriority(priority);

        String dueDate = chatSnapshot.child(Constants.DB_TASK_DUE_DATE).getValue().toString();
        currentTask.setDueDateTime(dueDate);

        Object completionDateObject = chatSnapshot.child(Constants.DB_TASK_COMPLETION_DATE).getValue();
        String completionDate = (completionDateObject != null)? completionDateObject.toString() : "";
        currentTask.setCompletionDateTime(completionDate);

        int taskStatusId = Integer.parseInt(chatSnapshot.child(Constants.DB_TASK_STATUS).getValue().toString());
        TaskStatus taskStatus = TaskStatus.values()[--taskStatusId];
        currentTask.setTaskStatus(taskStatus);

        return currentTask;
    }

}