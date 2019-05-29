package co.za.appic.teammanager.base.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import co.za.appic.teammanager.MyApplication;
import co.za.appic.teammanager.base.presenters.BasePresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.interfaces.DaggerActivity;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.PermissionsHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public abstract class BaseActivity extends AppCompatActivity implements DaggerActivity {

    protected Context context;
    protected BasePresenter presenter;
    protected PermissionsHelper permissionsHelper;
    protected DialogFragmentHelper dialogFragment;
    protected NotificationHelper notificationHelper;
    protected TransitionHelper transitionHelper;
    protected boolean isNewActivity;

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

        isNewActivity = true;

        setupComponent(MyApplication.get(this).component());
    }

    protected abstract void setBaseActivityDependencies();
    protected void initViews() { }
}