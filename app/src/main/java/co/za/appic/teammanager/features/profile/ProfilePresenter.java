package co.za.appic.teammanager.features.profile;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.models.UserModel;

public class ProfilePresenter extends BaseAsyncPresenter {

    private ProfileView profileView;
    private UserModel user;

    public ProfilePresenter(ProfileView profileView) {
        super(profileView);
        this.profileView = profileView;
        user = sharedPrefsHelper.getLinkedUser();
    }
}