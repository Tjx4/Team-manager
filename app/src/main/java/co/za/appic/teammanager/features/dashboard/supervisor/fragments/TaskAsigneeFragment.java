package co.za.appic.teammanager.features.dashboard.supervisor.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.adapters.WorkersViewAdapter;
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.WorkerModel;

public class TaskAsigneeFragment extends BaseCreateTaskFragment  implements WorkersViewAdapter.ItemClickListener{

    private Button createTaskBtn;
    private RecyclerView lstAsigneesRv;
    private List<WorkerModel> workers;

    public static Fragment newInstance(Activity context, Bundle bundle) {
        return Fragment.instantiate(context, TaskDueDateFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_task_asigneee, container, false);
        this.parentView = parentView;
        initViews(parentView);
        return parentView;
    }

    @Override
    protected void initViews(View parentView) {
        workers = supervisorDashboardActivity.getPresenter().getWorkers();
        WorkersViewAdapter workersViewAdapter = new WorkersViewAdapter(supervisorDashboardActivity, workers);
        workersViewAdapter.setClickListener(this);

        lstAsigneesRv = parentView.findViewById(R.id.lstAsignees);
        lstAsigneesRv.setAdapter(workersViewAdapter);

        createTaskBtn = parentView.findViewById(R.id.btnCreateTask);
        createTaskBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                newTaskFragment.onCreateTaskButtonClicked(view);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        WorkerModel worker = workers.get(position);
        TaskModel taskModel = newTaskFragment.taskModel;
        taskModel.setWorker(worker.getFbId());
    }

}