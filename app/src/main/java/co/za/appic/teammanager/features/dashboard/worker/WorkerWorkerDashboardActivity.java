package co.za.appic.teammanager.features.dashboard.worker;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.adapters.TaskViewAdapter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerWorkerDashboardComponent;
import co.za.appic.teammanager.di.modules.WorkerDashboardModule;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardActivity;
import co.za.appic.teammanager.features.history.HistoryActivity;
import co.za.appic.teammanager.features.profile.ProfileActivity;
import co.za.appic.teammanager.features.signin.SignInActivity;
import co.za.appic.teammanager.helpers.AnimationHelper;
import co.za.appic.teammanager.helpers.NavigationHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;
import co.za.appic.teammanager.models.TaskModel;

public class WorkerWorkerDashboardActivity extends SharedDashboardActivity implements WorkerDashboardView, TaskViewAdapter.ItemClickListener {

    @Inject
    WorkerDashboardPresenter workerDashboardPresenter;

    private View currentView;
    private TextView wolcomeMessageTv;
    private TextView pendingCountTv;
    private TextView completedCounteTv;
    private LinearLayout checkingMessageLl;
    private LinearLayout tasksContainerLl;
    private RecyclerView tasksRv;
    private LinearLayout homeContentLl;
    private CardView continueTaskContainerCv;
    private FrameLayout activeTaskContainer;
    private boolean showPushnotifiaction;
    private boolean isMainView;
    private TextView tasksTitleTv;
    private RelativeLayout tasksContainerRl;
    private Button startTaskBtn;
    private Button completeTaskBtn;
    private TextView activeTaskDescriptionTxt;
    private TextView activeTakDueDateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingTasks();
        initActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPushnotifiaction = false;
        getPresenter().updateUserAndSyncTasks();
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
        homeContentLl = parentLayout.findViewById(R.id.llHomeContainer);
        continueTaskContainerCv = parentLayout.findViewById(R.id.cvContinueTaskContainer);
        activeTaskContainer = parentLayout.findViewById(R.id.flActiveTaskContainer);
        tasksTitleTv = parentLayout.findViewById(R.id.tvTasksTitle);
        tasksContainerRl = parentLayout.findViewById(R.id.rlTasksContainer);
        startTaskBtn = parentLayout.findViewById(R.id.btnStartTask);
        completeTaskBtn = parentLayout.findViewById(R.id.btnFinishTask);
        activeTaskDescriptionTxt = parentLayout.findViewById(R.id.txtActiveTaskDescription);
        activeTakDueDateTxt = parentLayout.findViewById(R.id.txtActiveTakDueDate);

        tasksRv = parentLayout.findViewById(R.id.lstTasks);
        tasksRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initActivity() {
        isMainView = true;
        currentView = homeContentLl;
        AnimationHelper.initializeViews(getParentLayout(), homeContentLl);
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
    public void showLoadingTasks() {
        checkingMessageLl.setVisibility(View.VISIBLE);
        tasksContainerLl.setVisibility(View.GONE);
    }

    @Override
    public void showTasksDashboard() {
        checkingMessageLl.setVisibility(View.GONE);
        tasksContainerLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setWelcomeMessage(String message) {
        wolcomeMessageTv.setText(message);
    }

    @Override
    public void setPendingTaskCount(String pendingTasksCount) {
        pendingCountTv.setText(pendingTasksCount);
    }

    @Override
    public void setCompletedTaskCount(String completedTaskCount) {
        completedCounteTv.setText(completedTaskCount);
    }

    @Override
    public void onViewPendingTasksClicked(View view) {
        AnimationHelper.blinkView(view);
        showPendingTasks();
    }

    @Override
    public void onViewCompletedTasksClicked(View view) {
        AnimationHelper.blinkView(view);
        showCompletedTasks();
    }

    @Override
    public void showPendingTasks() {
        List<TaskModel>  pendingTasks =  getPresenter().getPendingTasks();
        int pendingTasksCount = pendingTasks.size();

        String headerMessage = getResources().getString(R.string.no_pending_tasks);

        if(pendingTasksCount > 0)
            headerMessage = "You have "+pendingTasksCount+" pending task"+((pendingTasksCount == 1)? "" : "s");

        showTaskListAndTitle(headerMessage, pendingTasks);
    }

    @Override
    public void showCompletedTasks() {
        List<TaskModel> completedTasks = getPresenter().getCompletedTasks();
        int completedTaskCount = completedTasks.size();

        String headerMessage = getResources().getString(R.string.no_completed_tasks);

        if(completedTaskCount > 0)
            headerMessage = "You have "+completedTaskCount+" completed task"+((completedTaskCount == 1)? "" : "s");

        showTaskListAndTitle(headerMessage, completedTasks);
    }

    private void showTaskListAndTitle(String tasksMessage, List<TaskModel> tasks) {
        tasksTitleTv.setText(tasksMessage);
        setCurrentTasksList(tasks);
        transitionViews(tasksContainerRl);
        isMainView = false;
    }

    @Override
    public void setCurrentTasksList(List<TaskModel> tasks) {
        TaskViewAdapter taskViewAdapter = new TaskViewAdapter(this, tasks);
        taskViewAdapter.setClickListener(this);
        tasksRv.setAdapter(taskViewAdapter);
    }

    @Override
    public void onHomeClicked(View view) {
        int cardVisibility = (getPresenter().getActiveTask() != null)? View.VISIBLE : View.INVISIBLE ;
        continueTaskContainerCv.setVisibility(cardVisibility);
        transitionViews(homeContentLl);
        isMainView = true;
    }

    @Override
    public void showActiveTask(TaskModel tasks) {
        if(tasks == null){
            NotificationHelper.showShortToast(this, getResources().getString(R.string.no_task_in_progress));
            return;
        }

        if(tasks.getTaskStatus() == TaskStatus.inprogress){
            startTaskBtn.setVisibility(View.INVISIBLE);
            completeTaskBtn.setVisibility(View.VISIBLE);
        }
        else {
            startTaskBtn.setVisibility(View.VISIBLE);
            completeTaskBtn.setVisibility(View.INVISIBLE);
        }

        activeTaskDescriptionTxt.setText(getPresenter().getActiveTask().getDescription());
        activeTakDueDateTxt.setText("Due on "+getPresenter().getActiveTask().getDueDateTime());

        transitionViews(activeTaskContainer);
        isMainView = false;
    }

    @Override
    public void notifyUserOfnewTask(){
        if(showPushnotifiaction)
            NotificationHelper.showPushNotification(this, R.drawable.ic_notify, getResources().getString(R.string.new_task_title), getResources().getString(R.string.new_task_message));
        else
            NotificationHelper.showShortToast(this, getResources().getString(R.string.new_task_message));
    }


    @Override
    public void onItemClick(View view, TaskModel task) {
        TaskModel currentActiveTask = getPresenter().getActiveTask();
        if(currentActiveTask != null){
            NotificationHelper.showLongToast(this, getResources().getString(R.string.complete_active_message));
            showActiveTask(currentActiveTask);
            return;
        }

       getPresenter().setActiveTask(task);
       showActiveTask(task);
    }

    @Override
    public void transitionViews(View incomingView) {
        AnimationHelper.shuffleViews(getParentLayout(), currentView, incomingView);
        currentView = incomingView;
    }

    public void onContinueTaskClicked(View view) {
        showActiveTask(getPresenter().getActiveTask());
    }

    @Override
    public void onStartTaskButtonClicked(View view) {
        startTaskBtn.setVisibility(View.INVISIBLE);
        completeTaskBtn.setVisibility(View.VISIBLE);
        getPresenter().putTaskInProgress();
    }

    @Override
    public void onFinishTaskButtonClicked(View view) {
        startTaskBtn.setVisibility(View.VISIBLE);
        completeTaskBtn.setVisibility(View.INVISIBLE);
        getPresenter().completeTask();
        onHomeClicked(view);
        NotificationHelper.showShortToast(this, getResources().getString(R.string.task_completed));
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
                showPendingTasks();
                break;
            case R.id.action_completed_tasks:
                showCompletedTasks();
                break;
           case R.id.action_active_tasks:
                showActiveTask(getPresenter().getActiveTask());
                break;
            case R.id.action_tasks:
                onHomeClicked(null);
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!isMainView && dialogFragment == null){
            onHomeClicked(null);
            return false;
        }
        else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        if(!isMainView && dialogFragment == null){
            onHomeClicked(null);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected boolean handleSlideMenuItemClicked(MenuItem item) {
        super.handleSlideMenuItemClicked(item);
        int itemId = item.getItemId();

        switch (itemId){

            case R.id.action_history:
                Bundle payload = new Bundle();
                payload.putInt(Constants.EMPLOYEE_EXTRA_ID, EmployeeType.worker.getUserId());
                NavigationHelper.goToActivityWithPayload(this , HistoryActivity.class, payload, TransitionHelper.slideInActivity());
                break;
        }
        return true;
    }


}