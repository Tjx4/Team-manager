package co.za.appic.teammanager.features.profile;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.models.UserModel;

public class ProfilePresenter extends BaseAsyncPresenter {

    private ProfileView profileView;
    private UserModel user;
    private boolean isEditMode;

    public ProfilePresenter(ProfileView profileView) {
        super(profileView);
        this.profileView = profileView;
        user = sharedPrefsHelper.getLinkedUser();
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void saveChanges(String updatedName, String updatedSurname, UserGender userGender){

    }
}