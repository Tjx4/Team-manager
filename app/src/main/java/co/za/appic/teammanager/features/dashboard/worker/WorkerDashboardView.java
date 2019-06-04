package co.za.appic.teammanager.features.dashboard.worker;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface WorkerDashboardView extends BaseView {
    WorkerDashboardPresenter getPresenter();
    void showWelcomeMessage(String message);
    void showCheckingTasks();
    void showTasks();
    void showPendingTaskCount(String pendingTasksCount);
    void showCompletedTaskCount(String completedTaskCount);
    void onViewPendingTasksClicked(View view);
    void onViewCompletedTasksClicked(View view);
    void onHomeClicked(View view);
    void onBeginTaskClicked(View view);
    void onEndTaskClicked(View view);
}