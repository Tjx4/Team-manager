package co.za.appic.teammanager.base.presenters;

import com.google.firebase.database.DataSnapshot;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.enums.EmployeeType;
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
        String employeeId = chatSnapshot.child("employeeId").getValue().toString();
        user.setEmployeeId(employeeId);
        String name = chatSnapshot.child("name").getValue().toString();
        user.setName(name);
        String surname = chatSnapshot.child("surname").getValue().toString();
        user.setSurname(surname);
    }
}