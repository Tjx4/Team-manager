package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.dashboard.DashboardPresenter;
import co.za.appic.teammanager.features.dashboard.DashboardView;
import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {
    private DashboardView dashboardView;

    public DashboardModule(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    @Provides
    public DashboardView provideDashboardView() {
        return dashboardView;
    }

    @Provides
    static DashboardPresenter providePresenter(DashboardView dashboardView) {
        return new DashboardPresenter(dashboardView);
    }
}