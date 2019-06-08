package co.za.appic.teammanager.base.presenters;

import com.google.firebase.database.DataSnapshot;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public abstract class BaseAsyncPresenter extends BasePresenter {

    protected boolean isBusy;

    public BaseAsyncPresenter(BaseView baseView) {
        super(baseView);
    }

    protected WorkerModel getWorkerFromDataSnapshot(DataSnapshot chatSnapshot) {
        try {
            WorkerModel workerModel = new WorkerModel();
            setCommonUserDetails(chatSnapshot, workerModel);
            workerModel.setEmployeeType(EmployeeType.worker);

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
            supervisorModel.setEmployeeType(EmployeeType.supervisor);
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
        UserGender gender = UserGender.values()[Integer.parseInt(chatSnapshot.child(Constants.DB_GENDER).getValue().toString())];
        user.setGender(gender);
        String mobile = chatSnapshot.child(Constants.DB_MOBILE).getValue().toString();
        user.setMobile(mobile);
        String email = chatSnapshot.child(Constants.DB_EMAIL).getValue().toString();
        user.setEmail(email);
        int employeeType = Integer.parseInt(chatSnapshot.child(Constants.DB_EMPLOYEE_TYPE).getValue().toString());
        user.setEmployeeType(EmployeeType.values()[--employeeType]);
    }
}