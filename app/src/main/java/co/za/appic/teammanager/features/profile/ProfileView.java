package co.za.appic.teammanager.features.profile;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.enums.UserGender;

public interface ProfileView extends BaseView {
    ProfilePresenter getPresenter();
    void setEditMode();
    void setViewMode();
    void toggleViews(char currentViewGroup);
    void saveAndSetViewMode();
    void onUploadPictureClicked(View view);
    void onTakePictureClicked(View view);
    void showUserDetails(String employeeId, String names, String surnames, EmployeeType employeeType, UserGender gender);
}