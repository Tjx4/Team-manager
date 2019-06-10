package co.za.appic.teammanager.features.forgotPassword;

import android.os.Bundle;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerForgotPasswordComponent;
import co.za.appic.teammanager.di.modules.ForgotPasswordModule;

public class ForgotPasswordActivity extends BaseChildActivity implements ForgotPasswordView {

    @Inject
    ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_forgot_password);
        initViews();
    }

    @Override
    protected void initViews() {
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerForgotPasswordComponent.builder().appComponent(appComponent)
                .forgotPasswordModule(new ForgotPasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public ForgotPasswordPresenter getPresenter() {
        return forgotPasswordPresenter;
    }
}
