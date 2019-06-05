package co.za.appic.teammanager.features.dashboard.supervisor.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.models.TaskModel;

public class TaskDueDateFragment extends BaseCreateTaskFragment {

    private DatePicker dueDateDp;
    private Button nextBtn;

    public static Fragment newInstance(Activity context, Bundle bundle) {
        return Fragment.instantiate(context, TaskDueDateFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_task_due_date, container, false);
        this.parentView = parentView;
        initViews(parentView);
        return parentView;
    }

    @Override
    protected void initViews(View parentView) {
        dueDateDp = parentView.findViewById(R.id.dueDateDp);

        nextBtn = parentView.findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                TaskModel taskModel = newTaskFragment.taskModel;

                int year = dueDateDp.getYear();
                int month = dueDateDp.getMonth() + 1;
                int day = dueDateDp.getDayOfMonth();
                String dueDay = day+"/"+month+"/"+year;
                taskModel.setDueDay(dueDay);

                onStageSetisfied(taskModel);
            }
        });
    }

}