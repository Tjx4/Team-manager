package co.za.appic.teammanager.di.modules;

import co.za.appic.teammanager.features.profile.ProfilePresenter;
import co.za.appic.teammanager.features.profile.ProfileView;
import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {
    private ProfileView profileView;

    public ProfileModule(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides
    public ProfileView provideProfileView() {
        return profileView;
    }

    @Provides
    static ProfilePresenter providePresenter(ProfileView profileView) {
        return new ProfilePresenter(profileView);
    }
}