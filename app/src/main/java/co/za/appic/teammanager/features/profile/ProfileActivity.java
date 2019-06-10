package co.za.appic.teammanager.features.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseChildActivity;
import co.za.appic.teammanager.customViews.GenderSelectorView;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerProfileComponent;
import co.za.appic.teammanager.di.modules.ProfileModule;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.helpers.AnimationHelper;
import co.za.appic.teammanager.helpers.ImageHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;
import co.za.appic.teammanager.helpers.PermissionsHelper;
import co.za.appic.teammanager.helpers.RoundLoadingImageView;

public class ProfileActivity extends BaseChildActivity implements ProfileView {

    @Inject
    ProfilePresenter profilePresenter;

    private GridLayout employeeDetailsGl;
    private LinearLayout loaderContainerLl;
    private RoundLoadingImageView profilePicRli;
    private TextView employeeIdTv;
    private TextView fullNamesTv;
    private TextView employeeTypeTv;
    private TextView genderTv;
    private EditText nameTxt;
    private EditText surnameTxt;
    private GenderSelectorView genderGsv;
    private MenuItem editMenuItem;
    private MenuItem viewMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().syncUser();

        if(!PermissionsHelper.isWriteExternalStoragePermissionGranted(this)){
            PermissionsHelper.requestWriteExternalStoragePermission(this);
        }
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
        loaderContainerLl = findViewById(R.id.llLoaderContainer);
        profilePicRli = findViewById(R.id.rliProfilePic);
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
    public void showUserDetails(String employeeId, String names, String surnames, EmployeeType employeeType, UserGender gender, String ppUrl) {
        employeeIdTv.setText(employeeId);
        fullNamesTv.setText(names+" "+surnames);
        employeeTypeTv.setText(employeeType.getUserType());
        genderTv.setText(gender.getGender());

        nameTxt.setText(names);
        surnameTxt.setText(surnames);
        genderGsv.setSelectedGender(gender);

        profilePicRli.setImageFromFirebaseStorage(getPresenter().getFirebaseStorage(), ppUrl);
    }

    @Override
    public void showContent() {
        loaderContainerLl.setVisibility(View.GONE);
        employeeDetailsGl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showImageUploadErrorToast() {
        NotificationHelper.showShortToast(this, getResources().getString(R.string.upload_failed));
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
        viewMenuItem.setVisible(true);
        getPresenter().setEditMode(true);
    }

    @Override
    public void setViewMode() {
        toggleViews('e');
        editMenuItem.setVisible(true);
        viewMenuItem.setVisible(false);
        getPresenter().setEditMode(false);
    }

    @Override
    public void saveAndSetViewMode() {
        String updatedName = nameTxt.getText().toString();
        String updatedSurname = surnameTxt.getText().toString();
        UserGender userGender = genderGsv.getSelectedGender();

        getPresenter().saveChanges(updatedName, updatedSurname, userGender);
        setViewMode();
    }

    @Override
    public void onUploadPictureClicked(View view) {
        AnimationHelper.blinkView(view);
        ImageHelper.getImageFromPhone(this);
    }

    @Override
    public void onTakePictureClicked(View view) {
        AnimationHelper.blinkView(view);
        ImageHelper.getImageFromCamera(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            Bitmap currentImageBitmap = null;
            ImageView currentImageView = profilePicRli.getImageView();

            try {
                switch (requestCode) {
                    case 1:
                        Bundle extras = data.getExtras();
                        currentImageBitmap = (Bitmap) extras.get("data");
                        break;
                    case 2:
                        Uri chosenImageUri = data.getData();
                        currentImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                        break;
                }

                if(currentImageBitmap != null){
                    currentImageView.setImageBitmap(currentImageBitmap);
                    getPresenter().updateProfilePic(currentImageBitmap);
                }

            }
            catch (Exception e) {
                Log.e("IMG_ERROR", "Camera error: "+e);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        viewMenuItem = menu.findItem(R.id.action_view);
        editMenuItem = menu.findItem(R.id.action_edit);
        editMenuItem.setVisible(true);
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
                saveAndSetViewMode();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(getPresenter().isEditMode()){
            saveAndSetViewMode();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && getPresenter().isEditMode()){
            saveAndSetViewMode();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}