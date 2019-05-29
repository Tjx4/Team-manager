package co.za.appic.teammanager.features.registration;

import android.os.Bundle;
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
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;

public class RegistrationActivity extends BaseActionBarActivity implements RegistrationView {

    @Inject
    IRegistrationPresenter registrationPresenter;

    private EditText nameTxt;
    private EditText passwordTxt;
    private EditText emailTxt;
    private EditText surNameTxt;
    private EditText confirmPasswordTxt;

    private TextView nameErrorTv;
    private TextView surnameErrorTv;
    private TextView emailErrorTv;
    private TextView passwordErrorTv;
    private TextView confirmPasswordErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        currentActionBar.setTitle(" "+context.getString(R.string.create_account_message));
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
        emailTxt = findViewById(R.id.txtEmail);
        confirmPasswordTxt = findViewById(R.id.txtConfirmPassword);

        nameErrorTv = findViewById(R.id.tvNameError);
        surnameErrorTv = findViewById(R.id.tvSurnameNameError);
        emailErrorTv = findViewById(R.id.tvEmailError);
        passwordErrorTv = findViewById(R.id.tvPasswordError);
        confirmPasswordErrorTv = findViewById(R.id.tvConfirmPasswordError);
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
        String name = nameTxt.getText().toString();
        String surName = surNameTxt.getText().toString();
        String email = passwordTxt.getText().toString();
        String password = emailTxt.getText().toString();
        String confirmedPassword = confirmPasswordTxt.getText().toString();

        getPresenter().registerNewUser(name, surName, email, password, confirmedPassword);
    }

    @Override
    public RegistrationPresenter getPresenter() {
        return (RegistrationPresenter)registrationPresenter;
    }

    @Override
    public void showRegisteringDialog() {
        showLoadingDialog(getString(R.string.no_internet_message));
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
    public void showInvalidEmail() {
        showValidationError(emailErrorTv, getString(R.string.invalid_email_message));
    }

    @Override
    public void showInvalidPassword() {
        showValidationError(passwordErrorTv, getString(R.string.invalid_password_message));
    }

    @Override
    public void showInvalidConfirmPassword() {
        showValidationError(confirmPasswordErrorTv, getString(R.string.invalid_password_message));
    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }

    @Override
    public void showRegisterError() {
        notificationHelper.showErrorDialog(context.getString(R.string.register_error), context.getString(R.string.register_error_message));
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
                NavigationHelper.goToActivityWithNoPayload(this, SignInActivity.class, transitionHelper.slideOutActivity());
                break;
        }
        return true;
    }

}