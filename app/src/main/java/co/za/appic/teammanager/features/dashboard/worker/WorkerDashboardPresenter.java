package co.za.appic.teammanager.features.dashboard.worker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
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
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.TASKS);
        Query query = tasksRef.orderByChild(Constants.WORKER).equalTo(workerId);
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

                        String description = chatSnapshot.child(Constants.TASK_DESCRIPTION).getValue().toString();
                        currentTask.setDescription(description);

                        String worker = chatSnapshot.child(Constants.WORKER).getValue().toString();
                        currentTask.setWorker(worker);

                        String supervisor = chatSnapshot.child(Constants.SUPERVISOR).getValue().toString();
                        currentTask.setSupervisor(supervisor);

                        int priorityId = Integer.parseInt(chatSnapshot.child(Constants.PRIORITY).getValue().toString());
                        PriorityLevel priority = PriorityLevel.values()[--priorityId];
                        currentTask.setPriority(priority);

                        int taskStatusId = Integer.parseInt(chatSnapshot.child(Constants.TASK_STATUS).getValue().toString());
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

                int pendingTasksCount = pendingTasks.size();
                int completedTasksCount =  completedTasks.size();
                String welcomeMessage = "Hi "+worker.getName()+" you have "+pendingTasksCount+" pending tasks and "+completedTasksCount+" completed tasks";
                workerDashboardView.showWelcomeMessage(welcomeMessage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<TaskModel> getPendingTasks() {
       return pendingTasks;
    }

    public List<TaskModel> getCompletedTasks() {
       return completedTasks;
    }

}