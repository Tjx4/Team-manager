package co.za.appic.teammanager.features.dashboard.shared;

import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;
import co.za.appic.teammanager.base.views.BaseView;

public abstract class SharedDashboardPresenter extends BaseFirebaseAuthPresenter {
    
    public SharedDashboardPresenter(BaseView baseView) {
        super(baseView);
    }
}
