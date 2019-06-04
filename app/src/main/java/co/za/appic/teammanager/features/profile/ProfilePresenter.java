package co.za.appic.teammanager.features.profile;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class ProfilePresenter extends BaseAsyncPresenter {

    private ProfileView profileView;
    public ProfilePresenter(ProfileView profileView) {
        super(profileView);
        this.profileView = profileView;
    }
}
