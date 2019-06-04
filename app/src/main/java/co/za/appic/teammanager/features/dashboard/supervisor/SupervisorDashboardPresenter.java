package co.za.appic.teammanager.features.dashboard.supervisor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardPresenter;
import co.za.appic.teammanager.helpers.StringHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.TaskModel;

public class SupervisorDashboardPresenter extends SharedDashboardPresenter {
    private SupervisorDashboardView supervisorDashboardView;
    private SupervisorModel supervisorModel;

    public SupervisorDashboardPresenter(SupervisorDashboardView supervisorDashboardView) {
        super(supervisorDashboardView);
        this.supervisorDashboardView = supervisorDashboardView;
        supervisorModel = sharedPrefsHelper.getSupervisor();
        greetSupervisor();
    }

    private void greetSupervisor() {
        String welcomeMessage = context.getResources().getString(R.string.supervisor_welcome_message, supervisorModel.getName());
        supervisorDashboardView.showWelcomeMessage(welcomeMessage);
    }

    public void createTask(final TaskModel taskModel) {
        taskModel.setTaskStatus(TaskStatus.pending);
        taskModel.setSupervisor(supervisorModel.getFbId());

        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.DB_TASKS);
        String newTaskId = StringHelper.getUniqueString();
        DatabaseReference serviceId = tasksRef.child(newTaskId);
        serviceId.setValue(newTaskId);

        DatabaseReference description = serviceId.child(Constants.BB_TASK_DESCRIPTION);
        description.setValue(taskModel.getDescription());
        DatabaseReference taskStatus = serviceId.child(Constants.DB_TASK_STATUS);
        taskStatus.setValue(taskModel.getTaskStatus());
        DatabaseReference priority = serviceId.child(Constants.DB_TASK_PRIORITY);
        priority.setValue(taskModel.getPriority());
        DatabaseReference worker = serviceId.child(Constants.DB_WORKER);
        worker.setValue(taskModel.getWorker());
        DatabaseReference supervisor = serviceId.child(Constants.DB_SUPERVISOR);
        supervisor.setValue(taskModel.getSupervisor());
        DatabaseReference creationDate = serviceId.child(Constants.DB_TASK_CREATION_DATE);
        creationDate.setValue(taskModel.getCreationDateTime());
        DatabaseReference dueDate = serviceId.child(Constants.DB_TASK_DUE_DATE);
        dueDate.setValue(taskModel.getDueDateTime());

    }
}