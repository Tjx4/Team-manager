package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.SignInModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.features.signin.SignInPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = SignInModule.class)
public interface SignInComponent {
    void inject(SignInActivity SignInActivity);
    SignInPresenter getMainPresenter();
}