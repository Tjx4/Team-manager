package co.za.appic.teammanager.features.dashboard;

import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;

public class DashboardPresenter extends BaseFirebaseAuthPresenter {
    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
    }

    public void putClientInBackground() {
        removeAuthFromFirebase();
    }

    public void putClientInForeground() {
        addAuthToFirebase();
    }


    public void signOutFromFirebase() {
        firebaseAuth.signOut();
    }
}