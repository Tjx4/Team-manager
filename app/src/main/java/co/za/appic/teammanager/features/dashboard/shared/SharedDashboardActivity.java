package co.za.appic.teammanager.features.dashboard.shared;

import android.view.KeyEvent;
import android.view.MenuItem;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseSlideMenuActivity;
import co.za.appic.teammanager.features.history.HistoryActivity;
import co.za.appic.teammanager.features.profile.ProfileActivity;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public abstract class SharedDashboardActivity extends BaseSlideMenuActivity {

    @Override
    protected void onResume() {
        super.onResume();

        if(isNewActivity)
            return;

        overridePendingTransition(TransitionHelper.slideOutActivity()[0], TransitionHelper.slideOutActivity()[1]);
    }

    @Override
    protected int getSideMenu() {
        return  R.menu.dashboard_side_menu;
    }

    @Override
    protected boolean handleSlideMenuItemClicked(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_profile:
                NavigationHelper.goToActivityWithNoPayload(this , ProfileActivity.class,  TransitionHelper.slideInActivity());
                break;

            case R.id.action_history:
                NavigationHelper.goToActivityWithNoPayload(this , HistoryActivity.class,  TransitionHelper.slideInActivity());
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}