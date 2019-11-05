package co.za.appic.teammanager.features.history;

import java.util.List;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.models.TaskModel;

public interface HistoryView extends BaseView {
    HistoryPresenter getPresenter();
    void showHistoryForCurrentEmployee(List<TaskModel> tasks, EmployeeType employeeType);
}