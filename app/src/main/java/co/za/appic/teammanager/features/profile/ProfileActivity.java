package co.za.appic.teammanager.features.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerProfileComponent;
import co.za.appic.teammanager.di.modules.ProfileModule;

public class ProfileActivity extends BaseChildActivity implements ProfileView {

    @Inject
    ProfilePresenter profilePresenter;

    private TextView employeeIdTv;
    private TextView fullNamesTv;
    private TextView employeeTypeTv;
    private TextView genderTv;
    private EditText nameTxt;
    private EditText surnameTxt;
    private EditText employeeTypeTxt;
    // private GenderSelectorView genderGsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        super.setActionbarActivityDependencies();
        currentActionBar.setTitle(getResources().getString(R.string.profile));
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_profile);
        initViews();
    }

    @Override
    protected void initViews() {
        employeeIdTv = findViewById(R.id.tvEmployeeId);
        fullNamesTv = findViewById(R.id.tvFullNames);
        employeeTypeTv = findViewById(R.id.tvEmployeeType);
        genderTv = findViewById(R.id.tvGender);
        nameTxt = findViewById(R.id.txtName);
        surnameTxt = findViewById(R.id.txtSurname);
        // genderGsv = findViewById(R.id.txtGender);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerProfileComponent.builder().appComponent(appComponent)
                .profileModule(new ProfileModule(this))
                .build()
                .inject(this);
    }

    @Override
    public ProfilePresenter getPresenter() {
        return profilePresenter;
    }

    @Override
    public void setEditMode() {

    }

    @Override
    public void setViewMode() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int itemId = item.getItemId();

        switch (itemId){
            case R.id.action_edit:
                setEditMode();
                break;

            case R.id.action_view:
                setViewMode();
                break;
        }
        return true;
    }
}