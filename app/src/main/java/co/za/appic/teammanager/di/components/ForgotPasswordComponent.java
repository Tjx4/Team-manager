package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.ForgotPasswordModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.forgotPassword.ForgotPasswordActivity;
import co.za.appic.teammanager.features.forgotPassword.ForgotPasswordPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = ForgotPasswordModule.class)
public interface ForgotPasswordComponent {
    void inject(ForgotPasswordActivity forgotPassword);
    ForgotPasswordPresenter getMainPresenter();
}