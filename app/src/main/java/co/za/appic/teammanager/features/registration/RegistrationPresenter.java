package co.za.appic.teammanager.features.registration;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.enums.UserType;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class RegistrationPresenter extends BaseFirebaseAuthPresenter implements IRegistrationPresenter {

    private RegistrationView registrationView;
    private UserModel newUser;

    public RegistrationPresenter(RegistrationView registrationView) {
        super(registrationView);
        this.registrationView = registrationView;
    }

    @Override
    public void registerNewUser(String name, String surname, String email, String password, String confirmedPassword) {
        if(isBusy)
            return;

        newUser = new UserModel();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setEmail(email);

        boolean isValidName = StringValidationHelper.isValidName(name.trim());

        if(isValidName){
            registrationView.showInvalidName();
        }

        boolean isValidSurname = StringValidationHelper.isValidName(surname.trim());

        if(isValidSurname){
            registrationView.showInvalidSurname();
        }

        boolean isValidEmail = StringValidationHelper.isValidEmail(email.trim());

        if(isValidEmail){
            registrationView.showInvalidEmail();
        }

        boolean isValidPassword = StringValidationHelper.isValidPassword(password.trim());

        if(isValidPassword){
            registrationView.showInvalidPassword();
        }

        boolean isPasswordConfirmed = StringValidationHelper.isMatchPasswords(password, confirmedPassword);

        if(isPasswordConfirmed){
            registrationView.showInvalidConfirmPassword();
        }

        registrationView.showRegisteringDialog();
        registerUserOnFireBase(email, password, (RegistrationActivity)registrationView);
    }

    @Override
    protected void onFirebaseRegisterSuccessfull() {
        UserType userType = newUser.getUserType();

        switch (userType){
            case worker:
                addWorkerToDB((WorkerModel) newUser);
                break;
            case supervisor:
                addSupervisorToDB((SupervisorModel) newUser);
                break;
        }
    }

    @Override
    protected void onFirebaseRegisterFailure() {
        registrationView.showRegisterError();
    }

    @Override
    public void addSupervisorToDB(SupervisorModel supervisor) {

    }

    @Override
    public void addWorkerToDB(WorkerModel worker) {

    }
}