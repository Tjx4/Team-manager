package co.za.appic.teammanager.features.profile;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.customViews.GenderSelectorView;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerProfileComponent;
import co.za.appic.teammanager.di.modules.ProfileModule;
import co.za.appic.teammanager.enums.UserGender;

public class ProfileActivity extends BaseChildActivity implements ProfileView {

    @Inject
    ProfilePresenter profilePresenter;

    private GridLayout employeeDetailsGl;
    private TextView employeeIdTv;
    private TextView fullNamesTv;
    private TextView employeeTypeTv;
    private TextView genderTv;
    private EditText nameTxt;
    private EditText surnameTxt;
    private GenderSelectorView genderGsv;
    private MenuItem editMenuItem;
    private MenuItem viewMenuItem;
    private MenuItem saveMenuItem;

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
        employeeDetailsGl = findViewById(R.id.glEmployeeDetails);
        employeeIdTv = findViewById(R.id.tvEmployeeId);
        fullNamesTv = findViewById(R.id.tvFullNames);
        employeeTypeTv = findViewById(R.id.tvEmployeeType);
        genderTv = findViewById(R.id.tvGender);
        nameTxt = findViewById(R.id.txtName);
        surnameTxt = findViewById(R.id.txtSurname);
        genderGsv = findViewById(R.id.gsvGender);
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
        toggleViews('v');
        editMenuItem.setVisible(false);
        viewMenuItem.setVisible(false);
        saveMenuItem.setVisible(true);
    }

    @Override
    public void setViewMode() {
        toggleViews('e');
        editMenuItem.setVisible(true);
        viewMenuItem.setVisible(false);
        saveMenuItem.setVisible(false);
    }

    @Override
    public void saveAndSetViewMode() {
        String updatedName = surnameTxt.getText().toString();
        String updatedSurname = surnameTxt.getText().toString();
        UserGender userGender = genderGsv.getSelectedGender();

        getPresenter().saveChanges(updatedName, updatedSurname, userGender);
        setViewMode();
    }

    @Override
    public void toggleViews(char currentViewGroup) {
        int childCount = employeeDetailsGl.getChildCount();

        for(int i = 0; i < childCount; ++i){
            View child = employeeDetailsGl.getChildAt(i);
            Object tag = child.getTag();
            char viewGroup = 0;

            if(tag != null)
                viewGroup = tag.toString().charAt(0);

            if(viewGroup == currentViewGroup){
                child.setVisibility(View.GONE);
            }
            else {
                child.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        editMenuItem = menu.findItem(R.id.action_edit);
        viewMenuItem = menu.findItem(R.id.action_view);
        saveMenuItem = menu.findItem(R.id.action_save);
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

            case R.id.action_save:
                setViewMode();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(getPresenter().isEditMode()){
            setViewMode();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && getPresenter().isEditMode()){
            setViewMode();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}