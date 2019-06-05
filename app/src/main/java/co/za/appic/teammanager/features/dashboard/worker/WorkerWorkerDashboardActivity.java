package co.za.appic.teammanager.features.dashboard.worker;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.adapters.TaskViewAdapter;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerWorkerDashboardComponent;
import co.za.appic.teammanager.di.modules.WorkerDashboardModule;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.AnimationHelper;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;
import co.za.appic.teammanager.models.TaskModel;

public class WorkerWorkerDashboardActivity extends SharedDashboardActivity implements WorkerDashboardView, TaskViewAdapter.ItemClickListener {

    @Inject
    WorkerDashboardPresenter workerDashboardPresenter;

    private TextView wolcomeMessageTv;
    private TextView pendingCountTv;
    private TextView completedCounteTv;
    private LinearLayout checkingMessageLl;
    private LinearLayout tasksContainerLl;
    private RecyclerView tasksRv;
    private LinearLayout homeContentLl;
    private boolean showPushnotifiaction;
    private boolean isMainView;
    private TextView tasksTitleTv;
    private RelativeLayout tasksContainerRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCheckingTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPushnotifiaction = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        showPushnotifiaction = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPushnotifiaction = true;
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
        return R.layout.activity_worker_dashboard;
    }

    @Override
    protected ViewGroup getParentLayout() {
        return (FrameLayout) findViewById(R.id.workerDashboardParentLayout);
    }

    @Override
    protected void initViews() {
        setSlideMenuDependencies(this,  getResources().getString(R.string.app_name), R.layout.activity_worker_dashboard, false);
        parentLayout = getMainLayout().inflate();

        wolcomeMessageTv = parentLayout.findViewById(R.id.tvWolcomeMessage);
        pendingCountTv = parentLayout.findViewById(R.id.tvPendingCount);
        completedCounteTv = parentLayout.findViewById(R.id.tvCompletedCount);
        checkingMessageLl = parentLayout.findViewById(R.id.llCheckingMessage);
        tasksContainerLl = parentLayout.findViewById(R.id.llTasksContainer);
        homeContentLl = parentLayout.findViewById(R.id.llHomeContent);
        tasksTitleTv = parentLayout.findViewById(R.id.tvTasksTitle);
        tasksContainerRl = parentLayout.findViewById(R.id.rlTasksContainer);

        tasksRv = parentLayout.findViewById(R.id.lstTasks);
        tasksRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void hideLoader() {
        hideLoadingDialog();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerWorkerDashboardComponent.builder().appComponent(appComponent)
                .workerDashboardModule(new WorkerDashboardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public WorkerDashboardPresenter getPresenter() {
        return workerDashboardPresenter;
    }

    @Override
    public void showWelcomeMessage(String message) {
        wolcomeMessageTv.setText(message);
    }

    @Override
    public void showCheckingTasks() {
        checkingMessageLl.setVisibility(View.VISIBLE);
        tasksContainerLl.setVisibility(View.GONE);
    }

    @Override
    public void showTasks() {
        checkingMessageLl.setVisibility(View.GONE);
        tasksContainerLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPendingTaskCount(String pendingTasksCount) {
        pendingCountTv.setText(pendingTasksCount);
    }

    @Override
    public void showCompletedTaskCount(String completedTaskCount) {
        completedCounteTv.setText(completedTaskCount);
    }

    @Override
    public void onViewPendingTasksClicked(View view) {
        AnimationHelper.blinkView(view);
        tasksContainerRl.setVisibility(View.VISIBLE);
        homeContentLl.setVisibility(View.GONE);

        int  pendingTasks =  getPresenter().getPendingTasks().size();
        String tasksMessage = "You have "+pendingTasks+" pending tasks";
        tasksTitleTv.setText(tasksMessage);

        NotificationHelper.showShortToast(this, getResources().getString(R.string.pending_tasks));
        showTasks( getPresenter().getPendingTasks());
        isMainView = false;
    }

    @Override
    public void onViewCompletedTasksClicked(View view) {
        AnimationHelper.blinkView(view);
        tasksContainerRl.setVisibility(View.VISIBLE);
        homeContentLl.setVisibility(View.GONE);

        int completedTasks = getPresenter().getCompletedTasks().size();
        String tasksMessage = "You have "+completedTasks+" completed tasks";
        tasksTitleTv.setText(tasksMessage);

        NotificationHelper.showShortToast(this, getResources().getString(R.string.completed_tasks));
        showTasks( getPresenter().getCompletedTasks());
        isMainView = false;
    }

    @Override
    public void onHomeClicked(View view) {
        tasksContainerRl.setVisibility(View.GONE);
        homeContentLl.setVisibility(View.VISIBLE);
        NotificationHelper.showShortToast(this, getResources().getString(R.string.main_view));
        isMainView = true;
    }

    @Override
    public void notifyUserOfnewTask(){
        if(showPushnotifiaction)
            NotificationHelper.showPushNotification(this, R.drawable.ic_notify, getResources().getString(R.string.new_task_title), getResources().getString(R.string.new_task_message));
        else
            NotificationHelper.showShortToast(this, getResources().getString(R.string.new_task_message));
    }

    @Override
    public void showTasks(List<TaskModel> tasks) {
        TaskViewAdapter taskViewAdapter = new TaskViewAdapter(this, tasks);
        taskViewAdapter.setClickListener(this);
        tasksRv.setAdapter(taskViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onBeginTaskClicked(View view) {
        AnimationHelper.blinkView(view);
        NotificationHelper.showShortToast(this,getResources().getString(R.string.task_started));
    }

    @Override
    public void onEndTaskClicked(View view) {
        AnimationHelper.blinkView(view);
        NotificationHelper.showShortToast(this, getResources().getString(R.string.task_ended));
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
            case R.id.action_pending_tasks:
                onViewPendingTasksClicked(null);
                break;
            case R.id.action_completed_tasks:
                onViewCompletedTasksClicked(null);
                break;
            case R.id.action_tasks:
                onHomeClicked(null);
                break;
        }
        return true;
    }

}