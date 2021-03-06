package co.za.appic.teammanager.features.signin;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public interface SignInView extends BaseView {
    SignInPresenter getPresenter();
    void onSignInButtonClicked(View view);
    void onCreateNewAccountClicked(View view);
    void onForgotPasswordButtonClicked(View view);
    void onSwitchUserButtonClicked(View view);
    void enterAppAsSupervisor(SupervisorModel supervisor);
    void enterAppAsWorker(WorkerModel worker);
    void closeLoaderAndShowSignInError(String title, String message);
    void setLinkedUserAndPassword(UserModel linkedUser);
    void enterUsernameAndPassword();
    void showInvalidUsername();
    void showInvalidPassword();
    void hideValidationLabels();
    void showSigningInDialog();
}