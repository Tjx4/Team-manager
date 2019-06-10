package co.za.appic.teammanager.features.forgotPassword;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerForgotPasswordComponent;
import co.za.appic.teammanager.di.modules.ForgotPasswordModule;
import co.za.appic.teammanager.helpers.NotificationHelper;

public class ForgotPasswordActivity extends BaseChildActivity implements ForgotPasswordView {

    @Inject
    ForgotPasswordPresenter forgotPasswordPresenter;

    private EditText emailTxt;
    private TextView emailErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        super.setActionbarActivityDependencies();
        currentActionBar.setTitle(getResources().getString(R.string.forgot_password));
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_forgot_password);
        initViews();
    }

    @Override
    protected void initViews() {
        emailTxt = findViewById(R.id.txtEmail);
        emailErrorTv = findViewById(R.id.tvEmailError);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerForgotPasswordComponent.builder().appComponent(appComponent)
                .forgotPasswordModule(new ForgotPasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showInvalidEmail() {
        showValidationError(emailErrorTv, getString(R.string.invalid_email_message));
    }

    @Override
    public void showResetingDialog() {
        showLoadingDialog(getString(R.string.reseting_password));
    }

    @Override
    public void hideValidationLabels() {
        emailErrorTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showResetSuccessMessage() {
        NotificationHelper.showSuccessDialog(this, getResources().getString(R.string.success_title), getResources().getString(R.string.forgot_password_success_message));
    }

    @Override
    public void showResetFailMessage() {
        NotificationHelper.showErrorDialog(this, getResources().getString(R.string.error_title), getResources().getString(R.string.forgot_password_error_message));
    }

    @Override
    public ForgotPasswordPresenter getPresenter() {
        return forgotPasswordPresenter;
    }

    @Override
    public void onForgotPasswordClicked(View view) {
        hideValidationLabels();
        String email = emailTxt.getText().toString();
        getPresenter().resetPassword(email);
    }

    @Override
    public void resetEmailText() {
        emailTxt.setText("");
    }
}
