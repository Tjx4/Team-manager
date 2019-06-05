package co.za.appic.teammanager.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.adapters.SectionsPagerAdapter;
import co.za.appic.teammanager.customViews.StagedViewPager;
import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.features.dashboard.supervisor.fragments.BaseCreateTaskFragment;
import co.za.appic.teammanager.features.dashboard.supervisor.fragments.TaskDescriptionAndPriorityFragment;
import co.za.appic.teammanager.features.dashboard.supervisor.fragments.TaskDueDateFragment;
import co.za.appic.teammanager.features.dashboard.supervisor.fragments.TaskDueTimeFragment;
import co.za.appic.teammanager.models.TaskModel;
import co.za.appic.teammanager.models.ViewPagerFragmentModel;

public class NewTaskFragment extends BaseDialogFragment implements BaseCreateTaskFragment.IRequestRequestStylistFragment{



    private RadioGroup priorityRg;
    private TextView titleTv;
    private Button createTaskBtn;
    private ImageButton backImgBtn;
    private LinearLayout loadingContainerLl;
    private SupervisorDashboardActivity supervisorDashboardActivity;
    private StagedViewPager stagedViewPager;
    protected SectionsPagerAdapter currentPagerAdapter;
    protected List<ViewPagerFragmentModel> stepFragments;

    public TaskModel taskModel;
    public int currentStage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = super.onCreateView( inflater,  container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        supervisorDashboardActivity = (SupervisorDashboardActivity)activity;

        backImgBtn = parentView.findViewById(R.id.imgBtnback);
        backImgBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(currentStage < 1){
                    backImgBtn.setVisibility(View.INVISIBLE);
                }
                else {
                    --currentStage;
                }

                stagedViewPager.setCurrentItem(currentStage);

            }
        });

        titleTv = parentView.findViewById(R.id.tvTitle);
        loadingContainerLl = parentView.findViewById(R.id.llLoadingContainer);
        stagedViewPager = parentView.findViewById(R.id.taskTabs);

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

                supervisorDashboardActivity.getPresenter().createTask(taskModel);
            }
        });

        taskModel = new TaskModel();

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


    public void moveToNextCategoryStage() {
        backImgBtn.setVisibility(View.INVISIBLE);
    }

    public void moveToNextTimeStage(TaskModel taskModel) {
        ++currentStage;
        stagedViewPager.setCurrentItem(currentStage);
        backImgBtn.setVisibility(View.VISIBLE);
    }

    class CreateStages extends AsyncTask<Boolean, Integer , SectionsPagerAdapter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            currentPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
            stepFragments = new ArrayList<>();
        }

        @Override
        protected SectionsPagerAdapter doInBackground(Boolean... booleans) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewPagerFragmentModel step1 = new ViewPagerFragmentModel();
            step1.setTitle("Enter task details");
            step1.setFragment(new TaskDescriptionAndPriorityFragment());
            stepFragments.add(step1);
            currentPagerAdapter.addFragment(step1.getFragment(), step1.getTitle());

            ViewPagerFragmentModel step2 = new ViewPagerFragmentModel();
            step2.setTitle("What date do you want it to be due");
            step2.setFragment(new TaskDueDateFragment());
            stepFragments.add(step2);
            currentPagerAdapter.addFragment(step2.getFragment(), step2.getTitle());

            ViewPagerFragmentModel step3 = new ViewPagerFragmentModel();
            step3.setTitle("What time do you want it to be done by");
            step3.setFragment(new TaskDueTimeFragment());
            stepFragments.add(step3);
            currentPagerAdapter.addFragment(step3.getFragment(), step3.getTitle());

            return currentPagerAdapter;
        }

        @Override
        protected void onPostExecute(SectionsPagerAdapter sectionsPagerAdapter) {
            super.onPostExecute(sectionsPagerAdapter);

            loadingContainerLl.setVisibility(View.GONE);

            stagedViewPager.setAdapter(sectionsPagerAdapter);
            stagedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == 0)
                        moveToNextCategoryStage();

                    currentStage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }


}