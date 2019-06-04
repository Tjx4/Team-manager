package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.WorkerDashboardModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.dashboard.worker.WorkerDashboardPresenter;
import co.za.appic.teammanager.features.dashboard.worker.WorkerWorkerDashboardActivity;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = WorkerDashboardModule.class)
public interface WorkerDashboardComponent {
    void inject(WorkerWorkerDashboardActivity workerDashboardActivity);
    WorkerDashboardPresenter getMainPresenter();
}