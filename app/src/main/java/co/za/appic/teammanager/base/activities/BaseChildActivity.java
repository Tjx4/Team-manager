package co.za.appic.teammanager.base.activities;

import android.os.Bundle;
import android.view.MenuItem;
import co.za.appic.teammanager.helpers.TransitionHelper;

public abstract class BaseChildActivity extends BaseActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        currentActionBar.setDisplayUseLogoEnabled(false);
        currentActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        TransitionHelper.slideOutActivity();
        return super.onOptionsItemSelected(item);
    }
}