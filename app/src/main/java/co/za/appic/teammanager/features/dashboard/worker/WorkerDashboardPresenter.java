package co.za.appic.teammanager.features.dashboard.worker;

import co.za.appic.teammanager.features.dashboard.shared.SharedDashboardPresenter;

public class WorkerDashboardPresenter extends SharedDashboardPresenter {
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