package co.za.appic.teammanager.features.signin;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseNoActionBarActivity;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSignInComponent;
import co.za.appic.teammanager.di.interfaces.DaggerActivity;
import co.za.appic.teammanager.di.modules.SignInModule;
import co.za.appic.teammanager.features.dashboard.worker.WorkerWorkerDashboardActivity;
import co.za.appic.teammanager.features.registration.RegistrationActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInActivity extends BaseNoActionBarActivity implements DaggerActivity, SignInView {

    @Inject
    SignInPresenter signInPresenter;

    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView linkedUserTv;
    private TextView usernameErrorTv;
    private TextView passwordErrorTv;
    private LinearLayout anonymouseUserContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().showLinkedUserOREnterUsername();
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
        linkedUserTv = findViewById(R.id.tvLinkedUser);
        usernameErrorTv = findViewById(R.id.tvUsernameError);
        passwordErrorTv = findViewById(R.id.tvPasswordError);
        anonymouseUserContainer = findViewById(R.id.llAnonymouseUserContainer);
    }

    @Override
    public SignInPresenter getPresenter() {
        return signInPresenter;
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
        NavigationHelper.goToActivityWithNoPayload(this, RegistrationActivity.class, TransitionHelper.slideInActivity());
    }

    @Override
    public void onForgotPasswordButtonClicked(View view) {
        NotificationHelper.showShortToast(this, getString(R.string.demo_unavailable_feature_message));
    }

    @Override
    public void onSwitchUserButtonClicked(View view) {
        enterUsernameAndPassword();
    }

    @Override
    public void enterAppAsSupervisor(SupervisorModel supervisor) {
        hideLoaderAndEnterApp(supervisor,  WorkerWorkerDashboardActivity.class);
    }

    @Override
    public void enterAppAsWorker(WorkerModel worker) {
        hideLoaderAndEnterApp(worker,  WorkerWorkerDashboardActivity.class);
    }

    private void hideLoaderAndEnterApp(UserModel user, Class dashboard) {
        hideLoader();
        Bundle payload = new Bundle();
        payload.putString(Constants.NAME, user.getName());
        NavigationHelper.goToActivityWithPayload(this ,dashboard, payload, TransitionHelper.fadeInActivity());
        finish();
    }

    @Override
    public void closeLoaderAndShowSignInError(String title, String message) {
        hideLoader();
        NotificationHelper.showErrorDialog(this, title, message);
    }

    @Override
    public void showInvalidUsername() {
        showValidationError(usernameErrorTv, getString(R.string.invalid_username_message));
    }

    @Override
    public void showInvalidPassword() {
        showValidationError(passwordErrorTv, getString(R.string.invalid_password_message));
    }

    @Override
    public void hideValidationLabels() {
        usernameErrorTv.setVisibility(View.INVISIBLE);
        passwordErrorTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSigningInDialog() {
        showLoadingDialog(getString(R.string.signing_in));
    }

    @Override
    public void enterUsernameAndPassword() {
        anonymouseUserContainer.setVisibility(View.VISIBLE);
        linkedUserTv.setVisibility(View.GONE);
    }

    @Override
    public void setLinkedUserAndPassword(UserModel linkedUser) {
        anonymouseUserContainer.setVisibility(View.GONE);
        linkedUserTv.setVisibility(View.VISIBLE);

        String greetingMessage = getResources().getString(R.string.user_greeting_message, linkedUser.getName());
        linkedUserTv.setText(greetingMessage);
        usernameTxt.setText(linkedUser.getEmail());
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