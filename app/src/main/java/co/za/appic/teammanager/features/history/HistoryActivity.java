package co.za.appic.teammanager.features.history;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.adapters.HistoryTaskViewAdapter;
import co.za.appic.teammanager.adapters.TaskViewAdapter;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerHistoryComponent;
import co.za.appic.teammanager.di.modules.HistoryModule;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.models.TaskModel;

public class HistoryActivity extends BaseChildActivity implements HistoryView, HistoryTaskViewAdapter.ItemClickListener {

    @Inject
    HistoryPresenter historyPresenter;

    private RecyclerView taskHistoryRv;
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int employeeTypeId = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).getInt(Constants.EMPLOYEE_EXTRA_ID);
        historyPresenter.syncTaskHistory(EmployeeType.values()[--employeeTypeId]);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_history);
        initViews();
    }

    @Override
    protected void initViews() {
        taskHistoryRv = findViewById(R.id.rvTaskHistory);
        titleTv = findViewById(R.id.tvTaskHistoryTitle);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        super.setActionbarActivityDependencies();
        currentActionBar.setTitle(getResources().getString(R.string.history));
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerHistoryComponent.builder().appComponent(appComponent)
                .historyModule(new HistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public HistoryPresenter getPresenter() {
        return historyPresenter;
    }

    @Override
    public void showHistory(List<TaskModel> tasks) {
        if(tasks == null || tasks.size() < 1){
            titleTv.setText(getResources().getString(R.string.no_history_message));
            return;
        }

        HistoryTaskViewAdapter taskViewAdapter = new HistoryTaskViewAdapter(this, tasks);
        taskViewAdapter.setClickListener(this);
        taskHistoryRv.setAdapter(taskViewAdapter);
    }

    @Override
    public void onItemClick(View view, TaskModel taskModel) {
    }
}
