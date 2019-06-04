package co.za.appic.teammanager.features.history;

import android.os.Bundle;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.di.components.AppComponent;

public class HistoryActivity extends BaseChildActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void setBaseActivityDependencies() {

    }

    @Override
    public void setupComponent(AppComponent appComponent) {

    }
}
