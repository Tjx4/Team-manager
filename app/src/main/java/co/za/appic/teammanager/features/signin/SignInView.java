package co.za.appic.teammanager.features.signin;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public interface SignInView extends BaseView {
    SigninPresenter getPresenter();
    void onLoginButtonClicked(View view);
    void onCreateNewAccountClicked(View view);
    void enterAppAsSupervisor(SupervisorModel supervisor);
    void enterAppAsWorker(WorkerModel worker);
}