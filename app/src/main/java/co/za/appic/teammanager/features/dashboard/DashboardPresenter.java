package co.za.appic.teammanager.features.dashboard;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class DashboardPresenter extends BaseAsyncPresenter {
    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
    }
}