package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.splash.ISplashPresenter;
import co.za.appic.teammanager.features.splash.SplashPresenter;
import co.za.appic.teammanager.features.splash.SplashView;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {
    private SplashView splashView;

    public SplashModule(SplashView splashView) {
        this.splashView = splashView;
    }

    @Provides
    public SplashView provideSplashView() {
        return splashView;
    }

    @Provides
    static ISplashPresenter providePresenter(SplashView splashView) {
        return new SplashPresenter(splashView);
    }
}