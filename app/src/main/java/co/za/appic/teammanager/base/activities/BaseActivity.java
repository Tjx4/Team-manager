package co.za.appic.teammanager.base.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import co.za.appic.teammanager.base.presenters.BasePresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.PermissionsHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected BasePresenter presenter;
    protected PermissionsHelper permissionsHelper;
    protected DialogFragmentHelper dialogFragmentHelper;
    protected NotificationHelper notificationHelper;
    protected TransitionHelper transitionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        permissionsHelper = new PermissionsHelper(this);
        notificationHelper = new NotificationHelper(this);
        transitionHelper = new TransitionHelper(this);
        setBaseActivityDependencies();

        try {
            int[] activityTransition = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).getIntArray(Constants.ACTIVITY_TRANSITION);
            overridePendingTransition(activityTransition[0], activityTransition[1]);
        } catch (Exception e) {

        }
    }

    protected abstract void setBaseActivityDependencies();

}