package co.za.appic.teammanager.features.dashboard.supervisor.fragments;

import android.content.Context;
import android.os.Bundle;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.fragments.BasePagerFragment;
import co.za.appic.teammanager.models.TaskModel;

public abstract class BaseCreateTaskFragment extends BasePagerFragment {

    protected BaseCreateTaskFragment.IRequestRequestStylistFragment parentFragment;
    protected SupervisorDashboardActivity supervisorDashboardActivity;
    protected NewTaskFragment newTaskFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        supervisorDashboardActivity = (SupervisorDashboardActivity) context;
    }

    protected void onStageSetisfied(TaskModel taskModel) {
        newTaskFragment.moveToNextTimeStage(taskModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment((NewTaskFragment)getParentFragment());
    }

    public void onAttachToParentFragment(NewTaskFragment fragment)
    {
        try
        {
            newTaskFragment = fragment;
            parentFragment = fragment;
        }
        catch (ClassCastException e)
        {
        }
    }

    public interface IRequestRequestStylistFragment
    {
    }

}
