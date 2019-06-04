package co.za.appic.teammanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.helpers.DateTimeHelper;
import co.za.appic.teammanager.models.TaskModel;

public class NewTaskFragment extends BaseDialogFragment {

    private RadioGroup priorityRg;
    private Button createTaskBtn;
    private EditText descriptionTxt;
    private SupervisorDashboardActivity supervisorDashboardActivity;
    private TaskModel taskModel;
    private DatePicker dueDateDp;
    private TimePicker dueTimeTp;


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

        dueDateDp = parentView.findViewById(R.id.dueDateDp);
        dueTimeTp = parentView.findViewById(R.id.dueTimeDp);

        createTaskBtn = parentView.findViewById(R.id.btnCreateTask);
        createTaskBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                taskModel.setDescription(descriptionTxt.getText().toString());
                boolean idValiddescription = taskModel.getDescription() != null && !taskModel.getDescription().isEmpty();
                if(idValiddescription){
                    return;
                }

                boolean isValidPriority = taskModel.getPriority() != null;
                if(!isValidPriority){
                    return;
                }

                int year = dueDateDp.getYear();
                int month = dueDateDp.getMonth() + 1;
                int day = dueDateDp.getDayOfMonth();
                String dueDate = day+"/"+month+"/"+year;

                int hourOfDay = dueTimeTp.getCurrentHour();
                int minute = dueTimeTp.getCurrentMinute();
                String dueTime = DateTimeHelper.getTimeFromTp(hourOfDay, minute);
                taskModel.setDueDateTime(dueDate+" "+dueTime);

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