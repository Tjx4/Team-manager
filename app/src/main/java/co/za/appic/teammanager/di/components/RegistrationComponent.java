package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.RegistrationModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.registration.IRegistrationPresenter;
import co.za.appic.teammanager.features.registration.RegistrationActivity;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = RegistrationModule.class)
public interface RegistrationComponent {
    void inject(RegistrationActivity registrationActivity);
    IRegistrationPresenter getMainPresenter();
}