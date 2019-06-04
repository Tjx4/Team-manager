package co.za.appic.teammanager.features.profile;

import co.za.appic.teammanager.base.views.BaseView;

public interface ProfileView extends BaseView {
    ProfilePresenter getPresenter();
    void setEditMode();
    void setViewMode();
}