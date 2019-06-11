package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.HistoryModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.history.HistoryActivity;
import co.za.appic.teammanager.features.history.HistoryPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = HistoryModule.class)
public interface HistoryComponent {
    void inject(HistoryActivity historyActivity);
    HistoryPresenter getMainPresenter();
}