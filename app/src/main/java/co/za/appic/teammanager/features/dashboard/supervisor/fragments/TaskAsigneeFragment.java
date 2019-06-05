package co.za.appic.teammanager.features.dashboard.supervisor.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import co.za.appic.teammanager.R;

public class TaskAsigneeFragment extends BaseCreateTaskFragment {

    private Button createTaskBtn;

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
        createTaskBtn = parentView.findViewById(R.id.btnNext);
        createTaskBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                newTaskFragment.onCreateTaskButtonClicked(view);
            }
        });
    }

}
