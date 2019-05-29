package co.za.appic.teammanager.base.presenters;

import co.za.appic.teammanager.base.activities.BaseActivity;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.helpers.CacheHelper;
import co.za.appic.teammanager.helpers.GlideHelper;

public abstract class BasePresenter {

    private BaseView baseView;
    public CacheHelper cacheHelper;
    public GlideHelper glideHelper;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
        cacheHelper = new CacheHelper((BaseActivity)baseView);
        glideHelper = new GlideHelper((BaseActivity)baseView);
    }

    public void handleBackButtonPressed(){

    }
}