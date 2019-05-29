package co.za.appic.teammanager.base.activities;

import android.os.Bundle;
import android.view.MenuItem;

public abstract class BaseChildActivity extends BaseActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        super.setActionbarActivityDependencies();
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

        transitionHelper.slideOutActivity();
        return super.onOptionsItemSelected(item);
    }
}
