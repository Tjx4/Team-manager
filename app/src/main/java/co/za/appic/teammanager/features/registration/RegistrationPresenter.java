package co.za.appic.teammanager.features.registration;

import com.google.firebase.database.DatabaseReference;
import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.helpers.StringHelper;
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
        newUser = new UserModel();
    }

    @Override
    public void setUserType(EmployeeType employeeType) {
        newUser.setEmployeeType(employeeType);
    }

    public void registerNewUser(String name, String surname, String email, String password, String confirmedPassword) {
        if(isBusy)
            return;

        boolean isValidName = StringValidationHelper.isValidName(name);

        if(!isValidName){
            registrationView.showInvalidName();
            return;
        }

        boolean isValidSurname = StringValidationHelper.isValidName(surname);

        if(!isValidSurname){
            registrationView.showInvalidSurname();
            return;
        }

        boolean isValidEmail = StringValidationHelper.isValidEmail(email);

        if(!isValidEmail){
            registrationView.showInvalidEmail();
            return;
        }

        boolean isValidPassword = StringValidationHelper.isValidPassword(password);

        if(!isValidPassword){
            registrationView.showInvalidPassword();
            return;
        }

        boolean isPasswordConfirmed = StringValidationHelper.isMatchPasswords(password, confirmedPassword);

        if(!isPasswordConfirmed){
            registrationView.showInvalidConfirmPassword();
            return;
        }

        boolean isValidUserType = newUser.getEmployeeType() != null;

        if(!isValidUserType){
            registrationView.showInvalidUserType();
            return;
        }

        isBusy = true;

        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setEmail(email);

        registrationView.showRegisteringDialog();
        registerUserOnFireBase(email, password, (RegistrationActivity)registrationView);
    }

    @Override
    protected void onFirebaseRegisterSuccessfull() {
        isBusy = false;

        EmployeeType employeeType = newUser.getEmployeeType();

        switch (employeeType){
            case worker:
                addWorkerToDB((WorkerModel) newUser);
                break;
            case supervisor:
                addSupervisorToDB((SupervisorModel) newUser);
                break;
        }

        registrationView.showRegisterSuccessDialog(newUser.getName());
    }

    @Override
    protected void onFirebaseRegisterFailure() {
        isBusy = false;

        registrationView.showRegisterError();
    }

    @Override
    public void addSupervisorToDB(SupervisorModel supervisor) {
        String supervisorEmployeeId = StringHelper.getSupervisorEmployeeId();
        supervisor.setEmployeeId(supervisorEmployeeId);

        DatabaseReference clientDbRef =  firebaseDatabase.getReference("supervisors");
        DatabaseReference user = clientDbRef.child(supervisorEmployeeId);
        user.setValue(supervisorEmployeeId);

        addCommonData(supervisor, user);
    }

    @Override
    public void addWorkerToDB(WorkerModel worker) {
        String workerEmployeeId = StringHelper.getWorkerEmployeeId();
        worker.setEmployeeId(workerEmployeeId);

        DatabaseReference clientDbRef =  firebaseDatabase.getReference("supervisors");
        DatabaseReference user = clientDbRef.child(workerEmployeeId);
        user.setValue(workerEmployeeId);

        addCommonData(worker, user);

        DatabaseReference name = user.child("teams");
        name.setValue(true);
    }

    private void addCommonData(UserModel newUser, DatabaseReference dbUserRef) {
        DatabaseReference name = dbUserRef.child("name");
        name.setValue(newUser.getName());
        DatabaseReference surname = dbUserRef.child("surname");
        surname.setValue(newUser.getSurname());
        DatabaseReference email = dbUserRef.child("email");
        email.setValue(newUser.getEmail());
    }
}