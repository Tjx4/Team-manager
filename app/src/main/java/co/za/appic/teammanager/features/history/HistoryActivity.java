package co.za.appic.teammanager.features.history;

import android.os.Bundle;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.di.components.AppComponent;

public class HistoryActivity extends BaseChildActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        super.setActionbarActivityDependencies();
        currentActionBar.setTitle(getResources().getString(R.string.history));
    }

    @Override
    public void setupComponent(AppComponent appComponent) {

    }
}
