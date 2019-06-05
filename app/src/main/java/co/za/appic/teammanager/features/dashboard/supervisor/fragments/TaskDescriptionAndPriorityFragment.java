package co.za.appic.teammanager.features.dashboard.supervisor.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.models.TaskModel;

public class TaskDescriptionAndPriorityFragment extends BaseCreateTaskFragment {
    private EditText descriptionTxt;
    private Button nextBtn;
    private RadioGroup priorityRg;
    private TextView descriptionErrorTv;
    private TextView priorityErrorTv;

    public static Fragment newInstance(Activity context, Bundle bundle) {
        return Fragment.instantiate(context, TaskDescriptionAndPriorityFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_task_description_priority, container, false);
        this.parentView = parentView;
        initViews(parentView);
        return parentView;
    }

    @Override
    protected void initViews(View parentView) {
        descriptionErrorTv = parentView.findViewById(R.id.tvDescriptionError);
        priorityErrorTv = parentView.findViewById(R.id.tvPriorityError);
        descriptionTxt = parentView.findViewById(R.id.txtDescription);

        priorityRg = parentView.findViewById(R.id.rdogPriority);
        priorityRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdoLow:
                        newTaskFragment.taskModel.setPriority(PriorityLevel.low);
                        break;
                    case R.id.rdoMedium:
                        newTaskFragment.taskModel.setPriority(PriorityLevel.medium);
                        break;
                    case R.id.rdoHigh:
                        newTaskFragment.taskModel.setPriority(PriorityLevel.high);
                        break;
                }
            }
        });

        nextBtn = parentView.findViewById(R.id.btnNextTaskStage);
        nextBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                hideValidationLabels();

                TaskModel taskModel = newTaskFragment.taskModel;

                taskModel.setDescription(descriptionTxt.getText().toString());
                boolean idValiddescription = taskModel.getDescription() != null && !taskModel.getDescription().isEmpty();
                if(!idValiddescription){
                    showInvalidDescription();
                    return;
                }

                boolean isValidPriority = taskModel.getPriority() != null;
                if(!isValidPriority){
                    showInvalidPriority();
                    return;
                }

                onStageSetisfied(taskModel);
            }
        });


    }

    @Override
    protected void onStageSetisfied(TaskModel taskModel) {
        super.onStageSetisfied(taskModel);
        hideValidationLabels();
    }

    public void showInvalidDescription() {
        descriptionErrorTv.setText(getString(R.string.invalid_usertype_task_description));
        descriptionErrorTv.setVisibility(View.VISIBLE);
    }

    public void showInvalidPriority() {
        priorityErrorTv.setText(getString(R.string.invalid_usertype_priority));
        priorityErrorTv.setVisibility(View.VISIBLE);
    }

    public void hideValidationLabels() {
        descriptionErrorTv.setVisibility(View.INVISIBLE);
        priorityErrorTv.setVisibility(View.INVISIBLE);
    }

}