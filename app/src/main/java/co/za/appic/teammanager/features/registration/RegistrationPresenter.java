package co.za.appic.teammanager.features.registration;

import com.google.firebase.database.DatabaseReference;
import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.helpers.StringHelper;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class RegistrationPresenter extends BaseFirebaseAuthPresenter  {

    private RegistrationView registrationView;
    private UserModel newUser;

    public RegistrationPresenter(RegistrationView registrationView) {
        super(registrationView);
        this.registrationView = registrationView;
        newUser = new UserModel();
    }

    public void setUserType(EmployeeType employeeType) {
        newUser.setEmployeeType(employeeType);
    }

    public void setGender(char gender) {
        newUser.setGender(gender);
    }

    public void registerNewUser(String name, String surname, String mobile, String email, String password, String confirmedPassword) {
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

        boolean isValidGender =  newUser.getGender() == 'm' || newUser.getGender() == 'f';
        if(!isValidGender){
            registrationView.showInvalidGender();
            return;
        }

        boolean isValidMobile = StringValidationHelper.isValidMobileNo(mobile);
        if(!isValidMobile){
            registrationView.showInvalidMobile();
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
        newUser.setMobile(mobile);

        registrationView.showRegisteringDialog();
        registerUserOnFireBase(email, password, (RegistrationActivity)registrationView);
    }

    @Override
    protected void onFirebaseRegisterSuccessfull() {
        isBusy = false;

        EmployeeType employeeType = newUser.getEmployeeType();

        switch (employeeType){
            case worker:
                addWorkerToDB();
                break;
            case supervisor:
                addSupervisorToDB();
                break;
        }

        setCurrentLinkedUser(newUser);
        registrationView.showRegisterSuccessDialog(newUser.getName());
    }

    @Override
    protected void onFirebaseRegisterFailure() {
        isBusy = false;
        registrationView.hideLoaderAndShowRegisterError();
    }

    private void addSupervisorToDB() {
        String supervisorEmployeeId = StringHelper.getSupervisorEmployeeId();
        newUser.setEmployeeId(supervisorEmployeeId);

        SupervisorModel supervisor = new SupervisorModel();
        setBasicUserDetails(supervisor);

        DatabaseReference clientDbRef = firebaseDatabase.getReference(Constants.SUPERVISORS_TABLE);
        DatabaseReference user = clientDbRef.child(supervisor.getFbId());
        user.setValue(supervisor.getFbId());
        addCommonData(supervisor, user);
    }

    private void addWorkerToDB() {
        String workerEmployeeId = StringHelper.getWorkerEmployeeId();
        newUser.setEmployeeId(workerEmployeeId);

        WorkerModel worker = new WorkerModel();
        setBasicUserDetails(worker);

        DatabaseReference clientDbRef = firebaseDatabase.getReference(Constants.WORKERS_TABLE);
        DatabaseReference user = clientDbRef.child(worker.getFbId());
        user.setValue(worker.getFbId());
        addCommonData(worker, user);

        DatabaseReference teams = user.child(Constants.DB_TEAMS);
        teams.setValue(true);
    }

    private void setBasicUserDetails(UserModel user) {
        String fbid = firebaseUser.getUid();
        user.setFbId(fbid);
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setGender(newUser.getGender());
        user.setMobile(newUser.getMobile());
        user.setEmail(newUser.getEmail());
        user.setEmployeeType(newUser.getEmployeeType());
    }

    private void addCommonData(UserModel newUser, DatabaseReference dbUserRef) {
        DatabaseReference employeeId = dbUserRef.child(Constants.DB_EMPLOYEE_ID);
        employeeId.setValue(newUser.getEmployeeId());
        DatabaseReference name = dbUserRef.child(Constants.DB_NAME);
        name.setValue(newUser.getName());
        DatabaseReference surname = dbUserRef.child(Constants.DB_SURNAME);
        surname.setValue(newUser.getSurname());
        DatabaseReference gender = dbUserRef.child(Constants.DB_GENDER);
        gender.setValue(newUser.getGender()+"");
        DatabaseReference mobile = dbUserRef.child(Constants.DB_MOBILE);
        mobile.setValue(newUser.getMobile());
        DatabaseReference email = dbUserRef.child(Constants.DB_EMAIL);
        email.setValue(newUser.getEmail());
        DatabaseReference employeeType = dbUserRef.child(Constants.DB_EMPLOYEE_TYPE);
        employeeType.setValue(newUser.getEmployeeType().getUserId());
    }
}