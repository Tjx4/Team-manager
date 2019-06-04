package co.za.appic.teammanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.models.TaskModel;

public class NewTaskFragment extends BaseDialogFragment {

    private RadioGroup priorityRg;
    private Button createTaskBtn;
    private EditText descriptionTxt;
    private SupervisorDashboardActivity supervisorDashboardActivity;
    private TaskModel taskModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = super.onCreateView( inflater,  container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        supervisorDashboardActivity = (SupervisorDashboardActivity)activity;
        taskModel = new TaskModel();

        descriptionTxt = parentView.findViewById(R.id.txtDescription);
        descriptionTxt = parentView.findViewById(R.id.txtDescription);

        priorityRg = parentView.findViewById(R.id.rdogPriority);
        priorityRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdoLow:
                        taskModel.setPriority(PriorityLevel.low);
                        break;
                    case R.id.rdoMedium:
                        taskModel.setPriority(PriorityLevel.medium);
                        break;
                    case R.id.rdoHigh:
                        taskModel.setPriority(PriorityLevel.high);
                        break;
                }
            }
        });

        createTaskBtn = parentView.findViewById(R.id.btnCreateTask);
        createTaskBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                boolean idValiddescription = taskModel.getDescription() != null && !taskModel.getDescription().isEmpty();
                if(idValiddescription){
                    return;
                }

                boolean isValidPriority = taskModel.getPriority() != null;
                if(!isValidPriority){
                    return;
                }

                supervisorDashboardActivity.getPresenter().createTask(taskModel);
            }
        });

        return  parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}