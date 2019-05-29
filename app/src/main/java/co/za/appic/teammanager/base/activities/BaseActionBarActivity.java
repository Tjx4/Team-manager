package co.za.appic.teammanager.base.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActionBarActivity extends BaseAsyncActivity{
    protected int actionBarMenu;
    protected ActionBar currentActionBar;
    protected boolean useLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionbarActivityDependencies();
        currentActionBar = getSupportActionBar();
        currentActionBar.setElevation(0);
    }

    protected abstract void setActionbarActivityDependencies();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getActionBarMenu() != 0)
            getMenuInflater().inflate(getActionBarMenu(), menu);

        return true;
    }

    @SuppressLint("RestrictedApi")
    protected void setAsParentActionBar() {
    }

    protected void setAsChildActionBar() {
        //Set also for specific Activity in Menifest
        currentActionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void setActionBarMenu(int actionBarMenu) {
        this.actionBarMenu = actionBarMenu;
    }
    protected int getActionBarMenu(){
        return actionBarMenu;
    }
}