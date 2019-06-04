package co.za.appic.teammanager.features.dashboard.supervisor;

import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardPresenter;

public class SupervisorDashboardPresenter extends SharedDashboardPresenter {
    private SupervisorDashboardView supervisorDashboardView;
    public SupervisorDashboardPresenter(SupervisorDashboardView supervisorDashboardView) {
        super(supervisorDashboardView);
        this.supervisorDashboardView = supervisorDashboardView;
    }
}