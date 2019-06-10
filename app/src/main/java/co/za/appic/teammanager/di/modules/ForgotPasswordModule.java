package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.forgotPassword.ForgotPasswordPresenter;
import co.za.appic.teammanager.features.forgotPassword.ForgotPasswordView;
import dagger.Module;
import dagger.Provides;

@Module
public class ForgotPasswordModule {
    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordModule(ForgotPasswordView forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
    }

    @Provides
    public ForgotPasswordView provideForgotPasswordView() {
        return forgotPasswordView;
    }

    @Provides
    static ForgotPasswordPresenter providePresenter(ForgotPasswordView forgotPasswordView) {
        return new ForgotPasswordPresenter(forgotPasswordView);
    }
}