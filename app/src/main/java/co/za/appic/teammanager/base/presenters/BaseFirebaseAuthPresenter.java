package co.za.appic.teammanager.base.presenters;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import co.za.appic.teammanager.base.activities.BaseActivity;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.models.UserModel;

public abstract class BaseFirebaseAuthPresenter extends BaseAsyncPresenter {

    public BaseFirebaseAuthPresenter(BaseView baseView) {
        super(baseView);
    }

    protected void signInUserOnFirebase(String username, String password, BaseActivity baseActivity){
        isBusy = true;
        firebaseAuth = FirebaseAuth.getInstance();

        OnCanceledListener signCancelListener = new OnCanceledListener() {
            @Override
            public void onCanceled() {
                onFirebaseSignInFailure();
                isBusy = false;
            }
        };

        OnFailureListener signInFailListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onFirebaseSignInFailure();
                isBusy = false;
            }
        };

        OnCompleteListener signInCompleteListener = new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    onFirebaseSignInSuccessfull();
                }
                else {
                    onFirebaseSignInFailure();
                }

                isBusy = false;
            }

        };

        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(baseActivity, signInCompleteListener)
                .addOnFailureListener(baseActivity, signInFailListener)
                .addOnCanceledListener(baseActivity, signCancelListener);
    }

    protected void onFirebaseSignInSuccessfull() {}

    protected void onFirebaseSignInFailure() {}


    protected void registerUserOnFireBase(String username, String password, BaseActivity baseActivity) {
        firebaseAuth = FirebaseAuth.getInstance();

        OnCompleteListener registerCompleteListener = new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    onFirebaseRegisterSuccessfull();
                }
                else {
                    onFirebaseRegisterFailure();
                }
            }
        };

        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(baseActivity, registerCompleteListener);
    }

    protected void onFirebaseRegisterSuccessfull() {}
    protected void onFirebaseRegisterFailure() {}

    public void setCurrentLinkedUser(UserModel user){
        sharedPrefsHelper.setLinkedUser(user);
    }

}
