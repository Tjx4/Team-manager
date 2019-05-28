package co.za.appic.teammanager.base.presenters;

import co.za.appic.teammanager.base.views.BaseView;

public abstract class BasePresenter {

    private BaseView baseView;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    public void handleBackButtonPressed(){

    }
}