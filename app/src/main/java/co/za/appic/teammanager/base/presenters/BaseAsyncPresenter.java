package co.za.appic.teammanager.base.presenters;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import co.za.appic.teammanager.base.activities.BaseActivity;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public abstract class BaseAsyncPresenter extends BasePresenter {

    public BaseAsyncPresenter(BaseView baseView) {
        super(baseView);
    }

    protected void signInUserOnFirebase(String username, String password, BaseActivity baseActivity){
        firebaseAuth = FirebaseAuth.getInstance();

        OnCompleteListener signInCompleteListener = new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    onFirebaseSignInSuccessfull();
                }
                else {
                    onFirebaseSignInFailure();
                }
            }
        };

        if( StringValidationHelper.isValidEmail(username) ){
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(baseActivity, signInCompleteListener);
        }
    }

    protected void onFirebaseSignInSuccessfull() {

    }

    protected void onFirebaseSignInFailure() {

    }

    protected WorkerModel getWorkerFromDataSnapshot(DataSnapshot chatSnapshot) {
        try {
            WorkerModel workerModel = new WorkerModel();
            setCommonUserDetails(chatSnapshot, workerModel);

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
       if(user == null)
           return;

        String name = chatSnapshot.child("name").getValue().toString();
        user.setName(name);
        String surname = chatSnapshot.child("surname").getValue().toString();
        user.setSurname(surname);
    }
}