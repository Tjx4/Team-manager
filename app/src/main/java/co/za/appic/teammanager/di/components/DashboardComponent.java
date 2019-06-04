package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.DashboardModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.dashboard.DashboardActivity;
import co.za.appic.teammanager.features.dashboard.DashboardPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = DashboardModule.class)
public interface DashboardComponent {
    void inject(DashboardActivity dashboardActivity);
    DashboardPresenter getMainPresenter();
}