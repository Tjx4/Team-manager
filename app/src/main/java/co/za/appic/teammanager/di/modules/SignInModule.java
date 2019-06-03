package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.signin.SignInPresenter;
import co.za.appic.teammanager.features.signin.SignInView;
import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {
    private SignInView SignInView;

    public SignInModule(SignInView SignInView) {
        this.SignInView = SignInView;
    }

    @Provides
    public SignInView provideSignInView() {
        return SignInView;
    }

    @Provides
    static SignInPresenter providePresenter(SignInView SignInView) {
        return new SignInPresenter(SignInView);
    }
}