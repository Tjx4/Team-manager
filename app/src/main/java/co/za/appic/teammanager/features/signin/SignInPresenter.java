package co.za.appic.teammanager.features.signin;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInPresenter extends BaseAsyncPresenter implements ISignInPresenter {

    private SignInView signInView;

    public SignInPresenter(SignInView signInView){
        super(signInView);
        this.signInView = signInView;
    }

    @Override
    public void SignInUser(String username, String password) {
        boolean isValidUsername = StringValidationHelper.isValidSurname(username.trim());
        boolean isValidPassword = !password.trim().isEmpty();

        if(isValidUsername){
            if(isValidPassword){
                signInUserOnFirebase(username, password, (SignInActivity)signInView);
            }
            else {
                signInView.showInvalidPassword(context.getString(R.string.signin_error));
            }
        }
        else {
            signInView.showInvalidUsername(context.getString(R.string.signin_error));
        }
    }
    @Override
    protected void onFirebaseSignInSuccessfull() {
        super.onFirebaseSignInSuccessfull();
        String userId = firebaseAuth.getInstance().getUid();
        fetchCurrentWorker(userId);
    }

    @Override
    protected void onFirebaseSignInFailure() {
        super.onFirebaseSignInFailure();
        showSignIndError(context.getString(R.string.signin_error), context.getString(R.string.signin_error_message));
    }

    @Override
    public void showSignIndError(String title, String message) {

    }

    private void fetchCurrentWorker(final String clientId) {
        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("workers").child(clientId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    WorkerModel worker = getWorkerFromDataSnapshot(dataSnapshot);

                    if(worker != null){
                        cacheHelper.setWorker(worker);
                        signInView.enterAppAsWorker(worker);
                        signInView.hideLoader();
                    }
                    else {
                        fetchCurrentSupervisor(clientId);
                    }
                }
                catch (Exception e){
                    fetchCurrentSupervisor(clientId);
                }

                UserRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchCurrentSupervisor(clientId);
                UserRef.removeEventListener(this);
            }
        };

        UserRef.keepSynced(false);
        UserRef.addValueEventListener(listener);
    }

    private void fetchCurrentSupervisor(String stylistId) {

        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("supervisors").child(stylistId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    SupervisorModel supervisor = getSupervisorFromDataSnapshot(dataSnapshot);

                    if(supervisor != null){
                        cacheHelper.setSupervisor(supervisor);
                        signInView.enterAppAsSupervisor(supervisor);
                        signInView.hideLoader();
                    }
                    else {
                        showSignIndError("SignIn error", "Sorry we are experiencing technical problems");
                    }
                }
                catch (Exception e){
                    showSignIndError("SignIn error", "Sorry we are experiencing technical problems");
                }

                UserRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showSignIndError("SignIn error", "Connection error");
                UserRef.removeEventListener(this);
            }
        };

        UserRef.keepSynced(false);
        UserRef.addValueEventListener(listener);
    }

}
