package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.registration.RegistrationPresenter;
import co.za.appic.teammanager.features.registration.RegistrationView;
import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationModule {
    private RegistrationView registrationView;

    public RegistrationModule(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Provides
    public RegistrationView provideRegistrationView() {
        return registrationView;
    }

    @Provides
    static RegistrationPresenter providePresenter(RegistrationView registrationView) {
        return new RegistrationPresenter(registrationView);
    }
}