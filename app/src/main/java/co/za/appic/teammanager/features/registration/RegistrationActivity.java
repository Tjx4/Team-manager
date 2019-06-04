package co.za.appic.teammanager.features.registration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseActionBarActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerRegistrationComponent;
import co.za.appic.teammanager.di.modules.RegistrationModule;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public class RegistrationActivity extends BaseActionBarActivity implements RegistrationView {

    @Inject
    RegistrationPresenter registrationPresenter;

    private EditText nameTxt;
    private EditText passwordTxt;
    private EditText mobileNumberTxt;
    private EditText emailTxt;
    private EditText surNameTxt;
    private EditText confirmPasswordTxt;

    private TextView nameErrorTv;
    private TextView surnameErrorTv;
    private TextView genderErrorTv;
    private TextView mobileNumberErrorTv;
    private TextView emailErrorTv;
    private TextView passwordErrorTv;
    private TextView confirmPasswordErrorTv;
    private TextView employeeTypeErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        currentActionBar.setTitle(" "+getString(R.string.create_account_message));
        currentActionBar.setLogo(R.drawable.ic_register_light);
        currentActionBar.setDisplayUseLogoEnabled(true);
        currentActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_registration);
        initViews();
    }

    @Override
    protected void initViews() {
        nameTxt = findViewById(R.id.txtName);
        surNameTxt = findViewById(R.id.txtSurname);
        passwordTxt = findViewById(R.id.txtPassword);
        mobileNumberTxt = findViewById(R.id.txtMobileNumber);
        emailTxt = findViewById(R.id.txtEmail);
        confirmPasswordTxt = findViewById(R.id.txtConfirmPassword);

        nameErrorTv = findViewById(R.id.tvNameError);
        surnameErrorTv = findViewById(R.id.tvSurnameNameError);
        genderErrorTv = findViewById(R.id.tvGenderError);
        mobileNumberErrorTv = findViewById(R.id.tvMobileNumberError);
        emailErrorTv = findViewById(R.id.tvEmailError);
        passwordErrorTv = findViewById(R.id.tvPasswordError);
        confirmPasswordErrorTv = findViewById(R.id.tvConfirmPasswordError);
        employeeTypeErrorTv = findViewById(R.id.tvEmployeeTypeError);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerRegistrationComponent.builder().appComponent(appComponent)
                .registrationModule(new RegistrationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRegisterButtonClicked(View view) {
        hideValidationLabels();

        String name = nameTxt.getText().toString();
        String surName = surNameTxt.getText().toString();
        String mobile = mobileNumberTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String confirmedPassword = confirmPasswordTxt.getText().toString();

        getPresenter().registerNewUser(name, surName, mobile, email, password, confirmedPassword);
    }

    @Override
    public void hideValidationLabels() {
        nameErrorTv.setVisibility(View.INVISIBLE);
        surnameErrorTv.setVisibility(View.INVISIBLE);
        mobileNumberErrorTv.setVisibility(View.INVISIBLE);
        emailErrorTv.setVisibility(View.INVISIBLE);
        passwordErrorTv.setVisibility(View.INVISIBLE);
        confirmPasswordErrorTv.setVisibility(View.INVISIBLE);
        employeeTypeErrorTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public RegistrationPresenter getPresenter() {
        return registrationPresenter;
    }

    @Override
    public void showRegisteringDialog() {
        showLoadingDialog(getString(R.string.creating_acount));
    }

    @Override
    public void showInvalidName() {
        showValidationError(nameErrorTv, getString(R.string.invalid_name_message));
    }

    @Override
    public void showInvalidSurname() {
        showValidationError(surnameErrorTv, getString(R.string.invalid_surname_message));
    }

    @Override
    public void showInvalidGender() {
        showValidationError(genderErrorTv, getString(R.string.invalid_gender));
    }

    @Override
    public void showInvalidMobile() {
        showValidationError(mobileNumberErrorTv, getString(R.string.invalid_mobile_message));
    }

    @Override
    public void showInvalidEmail() {
        showValidationError(emailErrorTv, getString(R.string.invalid_email_message));
    }

    @Override
    public void showInvalidPassword() {
        showValidationError(passwordErrorTv, getString(R.string.invalid_password_message));
    }

    @Override
    public void showInvalidConfirmPassword() {
        showValidationError(confirmPasswordErrorTv, getString(R.string.invalid_password_confirm_message));
    }

    @Override
    public void showInvalidUserType() {
        showValidationError(employeeTypeErrorTv, getString(R.string.invalid_usertype_message));
    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }

    @Override
    public void hideLoaderAndShowRegisterError() {
        hideLoader();
        NotificationHelper.showErrorDialog(this, getString(R.string.register_error), getString(R.string.register_error_message));
    }

    @Override
    public void showRegisterSuccessDialog(String name) {
        hideLoader();

        AlertDialog.Builder ab = NotificationHelper.getAlertDialogMessage(this, getString(R.string.register_success), getString(R.string.register_success_message, name), "Sign In");

        ab.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                proceedToLogin();
            }
        });

        NotificationHelper.showAlertMessage(this, ab);
    }

    @Override
    public void onWorkerClicked(View view) {
        getPresenter().setUserType(EmployeeType.worker);
    }

    @Override
    public void onSupervisorClicked(View view) {
        getPresenter().setUserType(EmployeeType.supervisor);
    }

    @Override
    public void onMaleClicked(View view) {
        getPresenter().setGender('m');
    }

    @Override
    public void onFemaleClicked(View view) {
        getPresenter().setGender('f');
    }

    @Override
    public void proceedToLogin() {
        NavigationHelper.goToActivityWithNoPayload(this , SignInActivity.class, TransitionHelper.fadeInActivity());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registration_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_quit_reg:
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}