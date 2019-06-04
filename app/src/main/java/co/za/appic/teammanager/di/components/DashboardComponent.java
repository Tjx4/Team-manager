package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.DashboardModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.dashboard.worker.WorkerDashboardPresenter;
import co.za.appic.teammanager.features.dashboard.worker.WorkerWorkerDashboardActivity;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = DashboardModule.class)
public interface DashboardComponent {
    void inject(WorkerWorkerDashboardActivity workerDashboardActivity);
    WorkerDashboardPresenter getMainPresenter();
}