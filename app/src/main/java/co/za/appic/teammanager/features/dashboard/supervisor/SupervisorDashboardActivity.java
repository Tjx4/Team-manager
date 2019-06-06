package co.za.appic.teammanager.features.dashboard.supervisor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSupervisorDashboardComponent;
import co.za.appic.teammanager.di.modules.SupervisorDashboardModule;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.features.dashboard.supervisor.fragments.NewTaskFragment;
import co.za.appic.teammanager.helpers.AnimationHelper;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public class SupervisorDashboardActivity extends SharedDashboardActivity implements SupervisorDashboardView  {

    @Inject
    SupervisorDashboardPresenter supervisorDashboardPresenter;
    private TextView wolcomeMessageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean handleSlideMenuItemClicked(MenuItem item) {
        super.handleSlideMenuItemClicked(item);

        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_signout:
                getPresenter().signOutFromFirebase();
                NavigationHelper.goToActivityWithNoPayload(this , SignInActivity.class,  TransitionHelper.slideOutActivity());
                finish();
                break;

        }
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_supervisor_dashboard;
    }

    @Override
    protected ViewGroup getParentLayout() {
        return (FrameLayout) findViewById(R.id.supervisorDashboardParentLayout);
    }

    @Override
    protected void initViews() {
        setSlideMenuDependencies(this,  getResources().getString(R.string.app_name), R.layout.activity_supervisor_dashboard, false);
        parentLayout = getMainLayout().inflate();

        wolcomeMessageTv = parentLayout.findViewById(R.id.tvWolcomeMessage);
    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerSupervisorDashboardComponent.builder().appComponent(appComponent)
                .supervisorDashboardModule(new SupervisorDashboardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public SupervisorDashboardPresenter getPresenter() {
        return supervisorDashboardPresenter;
    }

    @Override
    public void onCreateNewTaskClicked(View view) {
        AnimationHelper.blinkView(view);
        NewTaskFragment newTaskFragment  =  NewTaskFragment.newInstance(this, null);
        DialogFragmentHelper.showFragment(this, null, getResources().getString(R.string.create_task), R.layout.fragment_new_task, newTaskFragment);
        dialogFragment = newTaskFragment;
    }

    @Override
    public void showWelcomeMessage(String welcomeMessage) {
        wolcomeMessageTv.setText(welcomeMessage);
    }

    @Override
    public void hideNewTaskDialogAndShowSuccessMessage() {
        dialogFragment.dismiss();
        NotificationHelper.showSuccessDialog(this, getString(R.string.successful), getString(R.string.task_creation_success_message));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.supervisor_dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int itemId = item.getItemId();

        switch (itemId){

            case R.id.action_create_task:
                onCreateNewTaskClicked(null);
                break;
        }
        return true;
    }

}