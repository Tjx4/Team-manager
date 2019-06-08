package co.za.appic.teammanager.features.profile;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface ProfileView extends BaseView {
    ProfilePresenter getPresenter();
    void setEditMode();
    void setViewMode();
    void toggleViews(char currentViewGroup);
    void saveAndSetViewMode();
    void onUploadPictureClicked(View view);
    void onTakePictureClicked(View view);
}