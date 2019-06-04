package co.za.appic.teammanager.features.splash;

import android.os.Bundle;
import javax.inject.Inject;
import co.za.appic.teammanager.base.activities.BaseAsyncActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSplashComponent;
import co.za.appic.teammanager.di.modules.SplashModule;
import co.za.appic.teammanager.enums.UserStatus;
import co.za.appic.teammanager.features.dashboard.worker.WorkerWorkerDashboardActivity;
import co.za.appic.teammanager.features.pending.PendingRegActivity;
import co.za.appic.teammanager.features.registration.RegistrationActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public class SplashActivity extends BaseAsyncActivity implements SplashView {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserStatusAndNavigate();
        finish();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerSplashComponent.builder().appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void checkUserStatusAndNavigate() {

        switch (getPresenter().sharedPrefsHelper.getUserStatus()){
            case registered:
                NavigationHelper.goToActivityWithNoPayload(this, SignInActivity.class, TransitionHelper.fadeInActivity());
                break;
            case pending:
                NavigationHelper.goToActivityWithNoPayload(this, PendingRegActivity.class, TransitionHelper.fadeInActivity());
                break;
            default:
                NavigationHelper.goToActivityWithNoPayload(this, RegistrationActivity.class, TransitionHelper.fadeInActivity());
                break;
        }

    }

    @Override
    protected void setBaseActivityDependencies() {
    }

    @Override
    protected void initViews() {
    }

    @Override
    public SplashPresenter getPresenter() {
        return splashPresenter;
    }

    @Override
    public void hideLoader() {

    }
}