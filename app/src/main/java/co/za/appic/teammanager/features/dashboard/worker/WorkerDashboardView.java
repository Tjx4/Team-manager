package co.za.appic.teammanager.features.dashboard.worker;

import android.view.View;
import java.util.List;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.models.TaskModel;

public interface WorkerDashboardView extends BaseView {
    WorkerDashboardPresenter getPresenter();
    void setWelcomeMessage(String message);
    void showLoadingTasks();
    void showTasksDashboard();
    void setPendingTaskCount(String pendingTasksCount);
    void setCompletedTaskCount(String completedTaskCount);
    void onViewPendingTasksClicked(View view);
    void onViewCompletedTasksClicked(View view);
    void showPendingTasks();
    void showCompletedTasks();
    void showActiveTask(TaskModel task);
    void onHomeClicked(View view);
    void notifyUserOfnewTask();
    void onStartTaskButtonClicked(View view);
    void onFinishTaskButtonClicked(View view);
    void onContinueTaskClicked(View view);
    void transitionViews(View incomingView);
    void setCurrentTasksList(List<TaskModel> tasks);
}