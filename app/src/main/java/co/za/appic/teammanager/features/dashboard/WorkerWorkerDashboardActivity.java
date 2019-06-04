package co.za.appic.teammanager.features.dashboard;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseSlideMenuActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerDashboardComponent;
import co.za.appic.teammanager.di.modules.DashboardModule;
import co.za.appic.teammanager.features.profile.ProfileActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public class WorkerWorkerDashboardActivity extends BaseSlideMenuActivity implements WorkerDashboardView {

    WorkerDashboardPresenter workerDashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNewActivity)
            return;

        overridePendingTransition(TransitionHelper.slideOutActivity()[0], TransitionHelper.slideOutActivity()[1]);
    }

    @Override
    protected boolean handleSlideMenuItemClicked(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_signout:

                break;
        }
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected ViewGroup getParentLayout() {
        return  (FrameLayout) findViewById(R.id.workerDashboardParentLayout);
    }

    @Override
    protected int getSideMenu() {
        return  R.menu.dashboard_side_menu;
    }

    @Override
    protected void initViews() {
        setSlideMenuDependencies(this,  getResources().getString(R.string.app_name), R.layout.activity_dashboard, false);
        parentLayout = getMainLayout().inflate();

    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerDashboardComponent.builder().appComponent(appComponent)
                .dashboardModule(new DashboardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public WorkerDashboardPresenter getPresenter() {
        return workerDashboardPresenter;
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