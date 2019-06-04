package co.za.appic.teammanager.features.dashboard;

import co.za.appic.teammanager.base.presenters.BaseFirebaseAuthPresenter;

public class WorkerDashboardPresenter extends BaseFirebaseAuthPresenter {
    private WorkerDashboardView workerDashboardView;

    public WorkerDashboardPresenter(WorkerDashboardView workerDashboardView) {
        super(workerDashboardView);
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