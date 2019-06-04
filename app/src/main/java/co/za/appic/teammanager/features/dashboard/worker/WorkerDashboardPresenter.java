package co.za.appic.teammanager.features.dashboard.worker;

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
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.WorkerModel;

public class WorkerDashboardPresenter extends SharedDashboardPresenter {
    private WorkerDashboardView workerDashboardView;
    private List<TaskModel> pendingTasks;
    private List<TaskModel> completedTasks;
    private WorkerModel worker;

    public WorkerDashboardPresenter(WorkerDashboardView workerDashboardView) {
        super(workerDashboardView);
        this.workerDashboardView = workerDashboardView;
        worker = sharedPrefsHelper.getWorker();
        syncTasks(worker.getFbId());
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void syncTasks(final String workerId) {
        //DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("stylists");
        //UserRef.keepSynced(true);
        //UserRef.addValueEventListener(new ValueEventListener()

        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.DB_TASKS);
        Query query = tasksRef.orderByChild(Constants.DB_WORKER).equalTo(workerId);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pendingTasks = new ArrayList<>();
                completedTasks = new ArrayList<>();

                for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {

                    try{
                        TaskModel currentTask = new TaskModel();

                        String id =  chatSnapshot.getKey();
                        currentTask.setId(id);

                        String description = chatSnapshot.child(Constants.BB_TASK_DESCRIPTION).getValue().toString();
                        currentTask.setDescription(description);

                        String worker = chatSnapshot.child(Constants.DB_WORKER).getValue().toString();
                        currentTask.setWorker(worker);

                        String supervisor = chatSnapshot.child(Constants.DB_SUPERVISOR).getValue().toString();
                        currentTask.setSupervisor(supervisor);

                        int priorityId = Integer.parseInt(chatSnapshot.child(Constants.DB_TASK_PRIORITY).getValue().toString());
                        PriorityLevel priority = PriorityLevel.values()[--priorityId];
                        currentTask.setPriority(priority);

                        int taskStatusId = Integer.parseInt(chatSnapshot.child(Constants.DB_TASK_STATUS).getValue().toString());
                        TaskStatus taskStatus = TaskStatus.values()[--taskStatusId];
                        currentTask.setTaskStatus(taskStatus);

                        switch (taskStatus){
                            case pending:
                                pendingTasks.add(currentTask);
                                break;
                            case completed:
                                completedTasks.add(currentTask);
                                break;
                        }

                    }
                    catch (Exception e){

                    }
                }

                greetWorker();
                showTaskStats();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showTaskStats() {
        workerDashboardView.showTasks();
        int pendingTasksCount = pendingTasks.size();
        workerDashboardView.showPendingTaskCount(String.valueOf(pendingTasksCount));
        int completedTasksCount = completedTasks.size();
        workerDashboardView.showCompletedTaskCount(String.valueOf(completedTasksCount));
    }

    private void greetWorker() {
        String welcomeMessage = context.getResources().getString(R.string.worker_welcome_message, worker.getName());
        workerDashboardView.showWelcomeMessage(welcomeMessage);
    }

    public List<TaskModel> getPendingTasks() {
       return pendingTasks;
    }

    public List<TaskModel> getCompletedTasks() {
       return completedTasks;
    }

}