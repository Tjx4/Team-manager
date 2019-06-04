package co.za.appic.teammanager.features.dashboard.worker;

import co.za.appic.teammanager.base.views.BaseView;

public interface WorkerDashboardView extends BaseView {
    WorkerDashboardPresenter getPresenter();
    void showWelcomeMessage(String message);
    void showPendingTaskCount(String pendingTasksCount);
    void showCompletedTaskCount(String completedTaskCount);
    void viewPendingTasks();
    void viewCompletedTasks();
    void viewTasks();
    void begginTask();
    void endTask();
}