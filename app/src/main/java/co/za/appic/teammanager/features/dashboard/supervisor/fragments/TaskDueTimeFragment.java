package co.za.appic.teammanager.features.dashboard.supervisor.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.helpers.DateTimeHelper;
import co.za.appic.teammanager.models.TaskModel;

public class TaskDueTimeFragment extends BaseCreateTaskFragment {

    private TimePicker dueTimeTp;
    private Button nextBtn;

    public static Fragment newInstance(Activity context, Bundle bundle) {
        return Fragment.instantiate(context, TaskDueDateFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_task_due_time, container, false);
        this.parentView = parentView;
        initViews(parentView);
        return parentView;
    }

    @Override
    protected void initViews(View parentView) {
        dueTimeTp = parentView.findViewById(R.id.dueTimeDp);

        nextBtn = parentView.findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                TaskModel taskModel = newTaskFragment.taskModel;

                int hourOfDay = dueTimeTp.getCurrentHour();
                int minute = dueTimeTp.getCurrentMinute();
                String dueTime = DateTimeHelper.getTimeFromTp(hourOfDay, minute);
                taskModel.setDueTime(dueTime);
                String dateTime = taskModel.getDueDay()+" "+taskModel.getDueTime();
                taskModel.setDueDateTime(dateTime);

                onStageSetisfied(taskModel);
            }
        });
    }

}