package co.za.appic.teammanager.features.signin;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseNoActionBarActivity;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSignInComponent;
import co.za.appic.teammanager.di.interfaces.DaggerActivity;
import co.za.appic.teammanager.di.modules.SignInModule;
import co.za.appic.teammanager.features.dashboard.DashboardActivity;
import co.za.appic.teammanager.features.registration.RegistrationActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.StringHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInActivity extends BaseNoActionBarActivity implements DaggerActivity, SignInView {

    @Inject
    ISignInPresenter signInPresenter;

    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView usernameErrorTv;
    private TextView passwordErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerSignInComponent.builder().appComponent(appComponent)
                .signInModule(new SignInModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_sign_in);
        initViews();
    }

    @Override
    protected void initViews() {
        usernameTxt = findViewById(R.id.txtUsername);
        passwordTxt = findViewById(R.id.txtPassword);
        usernameErrorTv = findViewById(R.id.tvUsernameError);
        passwordErrorTv = findViewById(R.id.tvPasswordError);
    }

    @Override
    public SignInPresenter getPresenter() {
        return (SignInPresenter) signInPresenter;
    }

    @Override
    public void onSignInButtonClicked(View view) {
        hideValidationLabels();

        String username = usernameTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        getPresenter().SignInUser(username, password);
    }

    @Override
    public void onCreateNewAccountClicked(View view) {
        NavigationHelper.goToActivityWithNoPayload(this, RegistrationActivity.class, transitionHelper.slideInActivity());
    }

    @Override
    public void enterAppAsSupervisor(SupervisorModel supervisor) {
        Bundle payload = new Bundle();
        payload.putString(Constants.NAME, supervisor.getName());
        NavigationHelper.goToActivityWithPayload(this, DashboardActivity.class, payload, transitionHelper.slideInActivity());
    }

    @Override
    public void enterAppAsWorker(WorkerModel worker) {
        Bundle payload = new Bundle();
        payload.putString(Constants.NAME, worker.getName());
        NavigationHelper.goToActivityWithPayload(this, DashboardActivity.class, payload, transitionHelper.slideInActivity());
    }

    @Override
    public void showInvalidUsername(String message) {
        usernameErrorTv.setVisibility(View.VISIBLE);
        usernameErrorTv.setText(message);
    }

    @Override
    public void showInvalidPassword(String message) {
        passwordErrorTv.setVisibility(View.VISIBLE);
        passwordErrorTv.setText(message);
    }

    @Override
    public void hideValidationLabels() {
        usernameErrorTv.setVisibility(View.INVISIBLE);
        passwordErrorTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSigningInDialog() {
        showLoadingDialog(context.getString(R.string.signing_in));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }
}