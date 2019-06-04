package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.dashboard.worker.WorkerDashboardPresenter;
import co.za.appic.teammanager.features.dashboard.worker.WorkerDashboardView;
import dagger.Module;
import dagger.Provides;

@Module
public class WorkerDashboardModule {
    private WorkerDashboardView workerDashboardView;

    public WorkerDashboardModule(WorkerDashboardView workerDashboardView) {
        this.workerDashboardView = workerDashboardView;
    }

    @Provides
    public WorkerDashboardView provideWorkerDashboardView() {
        return workerDashboardView;
    }

    @Provides
    static WorkerDashboardPresenter providePresenter(WorkerDashboardView workerDashboardView) {
        return new WorkerDashboardPresenter(workerDashboardView);
    }
}