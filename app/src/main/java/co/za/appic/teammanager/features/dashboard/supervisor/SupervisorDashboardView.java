package co.za.appic.teammanager.features.dashboard.supervisor;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface SupervisorDashboardView extends BaseView {
    SupervisorDashboardPresenter getPresenter();
    void onCreateNewtaskClicked(View view);
}