package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardPresenter;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardView;
import dagger.Module;
import dagger.Provides;

@Module
public class SupervisorDashboardModule {
    private SupervisorDashboardView supervisorDashboardView;

    public SupervisorDashboardModule(SupervisorDashboardView supervisorDashboardView) {
        this.supervisorDashboardView = supervisorDashboardView;
    }

    @Provides
    public SupervisorDashboardView provideSupervisorDashboardView() {
        return supervisorDashboardView;
    }

    @Provides
    static SupervisorDashboardPresenter providePresenter(SupervisorDashboardView supervisorDashboardView) {
        return new SupervisorDashboardPresenter(supervisorDashboardView);
    }
}