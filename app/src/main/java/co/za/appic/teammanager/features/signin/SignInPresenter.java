package co.za.appic.teammanager.features.signin;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInPresenter extends BaseFirebaseAuthPresenter  {

    private SignInView signInView;

    public SignInPresenter(SignInView signInView){
        super(signInView);
        this.signInView = signInView;
    }

    public void showLinkedUserOREnterUsername() {
        UserModel currentLinkedUser = getCurrentLinkedUser();

        if(currentLinkedUser != null && currentLinkedUser.getEmail() == null) {
            signInView.setLinkedUserAndPassword(currentLinkedUser);
        }
        else{
            signInView.enterUsernameAndPassword();
        }
    }

    public void SignInUser(String username, String password) {
        if(isBusy)
            return;

        boolean isValidUsername = StringValidationHelper.isValidSurname(username.trim());
        if(!isValidUsername){
            signInView.showInvalidUsername();
            return;
        }

        boolean isValidPassword = !password.isEmpty();
        if(!isValidPassword){
            signInView.showInvalidPassword();
            return;
        }

        signInView.showSigningInDialog();
        signInUserOnFirebase(username, password, (SignInActivity)signInView);
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
        signInView.hideLoader();
        signInView.showSignInError(context.getString(R.string.signin_error), context.getString(R.string.signin_error_message));
    }

    private void fetchCurrentWorker(final String clientId) {
        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("workers").child(clientId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    WorkerModel worker = getWorkerFromDataSnapshot(dataSnapshot);

                    if(worker != null){
                        sharedPrefsHelper.setLinkedUser(worker);
                        signInView.enterAppAsWorker(worker);
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
                        sharedPrefsHelper.setLinkedUser(supervisor);
                        signInView.enterAppAsSupervisor(supervisor);
                    }
                    else {
                        signInView.showSignInError(context.getString(R.string.signin_error), context.getString(R.string.signin_technical_error));
                    }
                }
                catch (Exception e){
                    signInView.showSignInError(context.getString(R.string.signin_error), context.getString(R.string.signin_technical_error));
                }

                signInView.hideLoader();
                UserRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                signInView.hideLoader();
                signInView.showSignInError(context.getString(R.string.signin_error), context.getString(R.string.signin_technical_error));
                UserRef.removeEventListener(this);
            }
        };

        UserRef.keepSynced(false);
        UserRef.addValueEventListener(listener);
    }

}