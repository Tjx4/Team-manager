package co.za.appic.teammanager.features.splash;

import android.os.Bundle;
import android.widget.ImageView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseAsyncActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSplashComponent;
import co.za.appic.teammanager.di.modules.SplashModule;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;

public class SplashActivity extends BaseAsyncActivity implements SplashView {

    @Inject
    ISplashPresenter splashPresenter;
    private ImageView logoImg;
    private AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationHelper.goToActivityWithNoPayload(this, SignInActivity.class, transitionHelper.slideInActivity());
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
        /*

            if(getPresenter().cacheProvider.getUserStatus() == UserStatus.registered){
                NavigationHelper.goToActivityWithNoPayload(SignInActivity.class, slideInActivity());
            }
            else if(getPresenter().cacheProvider.getUserStatus() == UserStatus.pending){
                NavigationHelper.goToActivityWithNoPayload(PendingRegActivity.class, slideInActivity());
            }
            else{
                NavigationHelper.goToActivityWithNoPayload(RegistrationActivity.class, slideInActivity());
            }
        */
    }

    @Override
    protected void setBaseActivityDependencies() {
        //setContentView(R.layout.activity_splash);
        //initViews();
    }

    @Override
    protected void initViews() {
    }

    @Override
    public SplashPresenter getPresenter() {
        return (SplashPresenter)splashPresenter;
    }
}
