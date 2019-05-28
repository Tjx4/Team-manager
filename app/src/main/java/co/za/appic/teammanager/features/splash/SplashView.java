package co.za.appic.teammanager.features.splash;

import co.za.appic.teammanager.base.views.BaseView;

public interface SplashView extends BaseView {
    ISplashPresenter getPresenter();
    void showSplashAndEnterApp();
}
