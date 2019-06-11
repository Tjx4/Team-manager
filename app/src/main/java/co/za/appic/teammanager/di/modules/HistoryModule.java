package co.za.appic.teammanager.di.modules;
import co.za.appic.teammanager.features.history.HistoryPresenter;
import co.za.appic.teammanager.features.history.HistoryView;
import dagger.Module;
import dagger.Provides;

@Module
public class HistoryModule {
    private HistoryView historyView;

    public HistoryModule(HistoryView historyView) {
        this.historyView = historyView;
    }

    @Provides
    public HistoryView provideHistoryView() {
        return historyView;
    }

    @Provides
    static HistoryPresenter providePresenter(HistoryView historyView) {
        return new HistoryPresenter(historyView);
    }
}