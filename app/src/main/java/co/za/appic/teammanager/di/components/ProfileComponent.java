package co.za.appic.teammanager.di.components;

import co.za.appic.teammanager.di.modules.ProfileModule;
import co.za.appic.teammanager.di.scope.PerActivityScope;
import co.za.appic.teammanager.features.profile.ProfileActivity;
import co.za.appic.teammanager.features.profile.ProfilePresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = ProfileModule.class)
public interface ProfileComponent {
    void inject(ProfileActivity profileActivity);
    ProfilePresenter getMainPresenter();
}