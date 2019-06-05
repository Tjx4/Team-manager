package co.za.appic.teammanager.features.dashboard.supervisor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardPresenter;
import co.za.appic.teammanager.helpers.DateTimeHelper;
import co.za.appic.teammanager.helpers.StringHelper;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SupervisorDashboardPresenter extends SharedDashboardPresenter {
    private SupervisorDashboardView supervisorDashboardView;
    private SupervisorModel supervisorModel;
    private List<WorkerModel> workers;

    public SupervisorDashboardPresenter(SupervisorDashboardView supervisorDashboardView) {
        super(supervisorDashboardView);
        this.supervisorDashboardView = supervisorDashboardView;
        supervisorModel = sharedPrefsHelper.getSupervisor();
        syncWorkers();
        greetSupervisor();
    }

    public void syncWorkers() {
        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child(Constants.WORKERS_TABLE);
        workerRef.keepSynced(true);
        workerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workers = new ArrayList<>();

                for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {

                    try{
                        WorkerModel worker = getWorkerFromDataSnapshot(chatSnapshot);
                        workers.add(worker);
                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void greetSupervisor() {
        String welcomeMessage = context.getResources().getString(R.string.supervisor_welcome_message, supervisorModel.getName());
        supervisorDashboardView.showWelcomeMessage(welcomeMessage);
    }

    public List<WorkerModel> getWorkers() {
        return workers;
    }

    public void createTask(final TaskModel taskModel) {
        taskModel.setTaskStatus(TaskStatus.pending);
        taskModel.setSupervisor(supervisorModel.getFbId());
        taskModel.setCreationDateTime(DateTimeHelper.getYearMonthDayAndTime());

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

        supervisorDashboardView.hideNewTaskDialogAndShowSuccessMessage();
    }
}