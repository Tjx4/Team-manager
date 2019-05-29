package co.za.appic.teammanager.features.registration;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface RegistrationView extends BaseView {
    void onRegisterButtonClicked(View view);
    RegistrationPresenter getPresenter();
    void showRegisteringDialog();
    void showInvalidName();
    void showInvalidSurname();
    void showInvalidEmail();
    void showInvalidPassword();
    void showInvalidConfirmPassword();
}