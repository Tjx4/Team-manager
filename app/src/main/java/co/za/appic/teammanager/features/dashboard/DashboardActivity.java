package co.za.appic.teammanager.features.dashboard;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseActionBarActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.features.profile.ProfileActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public class DashboardActivity extends BaseActionBarActivity implements DashboardView  {

    DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_dashboard);
        initViews();
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void setActionbarActivityDependencies() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void setupComponent(AppComponent appComponent) {

    }

    @Override
    public DashboardPresenter getPresenter() {
        return dashboardPresenter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.worker_dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_signout:
                getPresenter().signOutFromFirebase();
                NavigationHelper.goToActivityWithNoPayload(this , SignInActivity.class,  TransitionHelper.slideOutActivity());
                finish();
                break;

            case R.id.action_profile:
                NavigationHelper.goToActivityWithNoPayload(this , ProfileActivity.class,  TransitionHelper.slideInActivity());
                break;
        }
        return true;
    }

}