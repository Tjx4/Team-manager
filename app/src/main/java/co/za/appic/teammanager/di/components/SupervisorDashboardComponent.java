package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.SupervisorDashboardModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = SupervisorDashboardModule.class)
public interface SupervisorDashboardComponent {
    void inject(SupervisorDashboardActivity supervisorDashboardActivity);
    SupervisorDashboardPresenter getMainPresenter();
}