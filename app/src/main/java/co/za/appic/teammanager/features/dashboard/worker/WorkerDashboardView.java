package co.za.appic.teammanager.features.dashboard.worker;

import co.za.appic.teammanager.base.views.BaseView;

public interface WorkerDashboardView extends BaseView {
    WorkerDashboardPresenter getPresenter();
    void viewPendingTasks();
    void viewCompletedTasks();
    void viewTasks();
    void begginTask();
    void endTask();
}
