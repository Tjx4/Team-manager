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
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardPresenter;
import co.za.appic.teammanager.helpers.DateTimeHelper;
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.WorkerModel;

public class WorkerDashboardPresenter extends SharedDashboardPresenter {
    private WorkerDashboardView workerDashboardView;
    private List<String> capturedTasks;
    private List<TaskModel> pendingTasks;
    private List<TaskModel> completedTasks;
    private TaskModel activeTask;
    private WorkerModel worker;
    private boolean isNewAvailable;
    private boolean isInitial;

    public WorkerDashboardPresenter(WorkerDashboardView workerDashboardView) {
        super(workerDashboardView);
        this.workerDashboardView = workerDashboardView;
        capturedTasks = new ArrayList<>();
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void updateUserAndSyncTasks() {
        worker = sharedPrefsHelper.getWorker();
        syncTasks();
    }

    public void syncTasks() {
        String workerId = firebaseAuth.getUid();
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.DB_TASKS);
        Query query = tasksRef.orderByChild(Constants.DB_WORKER).equalTo(workerId);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isInitial = pendingTasks == null;
                pendingTasks = new ArrayList<>();
                completedTasks = new ArrayList<>();

                for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {

                    try{
                        TaskModel currentTask = getTaskFromDataSnapsho(chatSnapshot);

                        switch (currentTask.getTaskStatus()){
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

                checkForNewPendingTasks();
                greetWorker();
                showTaskStats();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkForNewPendingTasks() {
        boolean isNew;
        int newFound = 0;

        for(TaskModel currentTask : pendingTasks){
            String taskId = currentTask.getId();
            if(!isInitial){

                isNew = !capturedTasks.contains(taskId);

                if(isNew){
                    capturedTasks.add(taskId);
                    ++newFound;
                }
            }
            else {
                capturedTasks.add(taskId);
            }
        }

        isNewAvailable = newFound > 0;
    }

    private void showTaskStats() {
        workerDashboardView.showTasksDashboard();
        int pendingTasksCount = pendingTasks.size();
        workerDashboardView.setPendingTaskCount(String.valueOf(pendingTasksCount));
        int completedTasksCount = completedTasks.size();
        workerDashboardView.setCompletedTaskCount(String.valueOf(completedTasksCount));

        if(isNewAvailable)
            workerDashboardView.notifyUserOfnewTask();
    }

    private void greetWorker() {
        String welcomeMessage = context.getResources().getString(R.string.worker_welcome_message, worker.getName());
        workerDashboardView.setWelcomeMessage(welcomeMessage);
    }

    private boolean isTaskCaptured(List<TaskModel> tasks, TaskModel newTask) {
        boolean isTaskCaptured = false;

        for(TaskModel task : tasks){
            String taskId = task.getId();
            String currentTuskId = newTask.getId();

            if(taskId.equals(currentTuskId)){
                isTaskCaptured = true;
                break;
            }
        }
        return isTaskCaptured;
    }

    public List<TaskModel> getPendingTasks() {
       return pendingTasks;
    }

    public List<TaskModel> getCompletedTasks() {
       return completedTasks;
    }

    public void setActiveTask(TaskModel activeTask) {
        this.activeTask = activeTask;
    }

    public void putTaskInProgress() {
        activeTask.setTaskStatus(TaskStatus.inprogress);
        updateTask();
    }

    public void completeTask() {
        activeTask.setTaskStatus(TaskStatus.completed);
        activeTask.setCompletionDateTime(DateTimeHelper.convertDashesToSlashes(DateTimeHelper.getYearMonthDayAndTime()));
        updateTask();
        activeTask = null;
    }

    private void updateTask() {
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.DB_TASKS);
        String newTaskId = activeTask.getId();
        DatabaseReference taskRef = tasksRef.child(newTaskId);
        DatabaseReference taskStatusRef = taskRef.child(Constants.DB_TASK_STATUS);
        taskStatusRef.setValue(activeTask.getTaskStatus().getId());

        if(activeTask.getTaskStatus() == TaskStatus.completed){
            DatabaseReference taskCompleteDateRef = taskRef.child(Constants.DB_TASK_COMPLETION_DATE);
            taskCompleteDateRef.setValue(activeTask.getCompletionDateTime());
        }
    }

    public TaskModel getActiveTask() {
        return activeTask;
    }

}