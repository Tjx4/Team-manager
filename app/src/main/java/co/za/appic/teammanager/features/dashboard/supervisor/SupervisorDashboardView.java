package co.za.appic.teammanager.features.dashboard.supervisor;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface SupervisorDashboardView extends BaseView {
    SupervisorDashboardPresenter getPresenter();
    void onCreateNewTaskClicked(View view);
    void showWelcomeMessage(String welcomeMessage);
    void hideNewTaskDialogAndShowSuccessMessage();
}