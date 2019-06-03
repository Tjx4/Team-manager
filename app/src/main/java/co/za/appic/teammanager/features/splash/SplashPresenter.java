package co.za.appic.teammanager.features.splash;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class SplashPresenter extends BaseAsyncPresenter {
    private SplashView splashView;

    public SplashPresenter(SplashView splashView) {
        super(splashView);
        this.splashView = splashView;
    }
}