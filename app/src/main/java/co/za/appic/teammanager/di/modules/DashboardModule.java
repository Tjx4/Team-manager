package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.dashboard.WorkerDashboardPresenter;
import co.za.appic.teammanager.features.dashboard.WorkerDashboardView;
import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {
    private WorkerDashboardView workerDashboardView;

    public DashboardModule(WorkerDashboardView workerDashboardView) {
        this.workerDashboardView = workerDashboardView;
    }

    @Provides
    public WorkerDashboardView provideDashboardView() {
        return workerDashboardView;
    }

    @Provides
    static WorkerDashboardPresenter providePresenter(WorkerDashboardView workerDashboardView) {
        return new WorkerDashboardPresenter(workerDashboardView);
    }
}