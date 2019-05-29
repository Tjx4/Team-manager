package co.za.appic.teammanager.features.registration;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.enums.UserType;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public class RegistrationPresenter extends BaseFirebaseAuthPresenter implements IRegistrationPresenter {

    private RegistrationView registrationView;
    private UserType userType;

    public RegistrationPresenter(RegistrationView registrationView) {
        super(registrationView);
        this.registrationView = registrationView;
    }

    @Override
    public void registerNewUser(String name, String surname, String email, String password, String confirmedPassword) {
        if(isBusy)
            return;


        boolean isValidName = StringValidationHelper.isValidName(name.trim());
        boolean isValidPassword = StringValidationHelper.isValidPassword(password.trim());

        if(isValidName){
            if(isValidPassword){
                registrationView.showRegisteringDialog();
                registerUserOnFireBase(email, password, (RegistrationActivity)registrationView);
            }
            else {
                registrationView.showInvalidPassword();
            }
        }
        else {
            registrationView.showInvalidName();
        }

    }


    @Override
    protected void onFirebaseRegisterSuccessfull() {

    }

    @Override
    protected void onFirebaseRegisterFailure() {

    }

    @Override
    public void addSupervisorToDB(SupervisorModel supervisor) {

    }

    @Override
    public void addWorkerToDB(WorkerModel worker) {

    }
}