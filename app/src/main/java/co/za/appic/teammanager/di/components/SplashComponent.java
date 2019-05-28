package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.SplashModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.splash.ISplashPresenter;
import co.za.appic.teammanager.features.splash.SplashActivity;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = SplashModule.class)
public interface SplashComponent {
    void inject(SplashActivity SplashActivity);
    ISplashPresenter getMainPresenter();
}