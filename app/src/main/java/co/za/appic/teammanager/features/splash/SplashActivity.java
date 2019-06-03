package co.za.appic.teammanager.features.splash;

import android.os.Bundle;
import javax.inject.Inject;
import co.za.appic.teammanager.base.activities.BaseAsyncActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSplashComponent;
import co.za.appic.teammanager.di.modules.SplashModule;
import co.za.appic.teammanager.enums.UserStatus;
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

        NavigationHelper.goToActivityWithNoPayload(this, SignInActivity.class, TransitionHelper.slideInActivity());
        // showSplashAndEnterApp();

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
    public void showSplashAndEnterApp() {
        if(getPresenter().sharedPrefsHelper.getUserStatus() == UserStatus.registered){
            NavigationHelper.goToActivityWithNoPayload(this, SignInActivity.class, TransitionHelper.slideInActivity());
        }
        else if(getPresenter().sharedPrefsHelper.getUserStatus() == UserStatus.pending){
            NavigationHelper.goToActivityWithNoPayload(this, PendingRegActivity.class, TransitionHelper.slideInActivity());
        }
        else{
            NavigationHelper.goToActivityWithNoPayload(this, RegistrationActivity.class, TransitionHelper.slideInActivity());
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