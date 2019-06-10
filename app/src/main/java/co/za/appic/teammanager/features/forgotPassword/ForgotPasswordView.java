package co.za.appic.teammanager.features.forgotPassword;

import android.view.View;
import co.za.appic.teammanager.base.views.BaseView;

public interface ForgotPasswordView extends BaseView {
    ForgotPasswordPresenter getPresenter();
    void onForgotPasswordClicked(View view);
    void showInvalidEmail();
    void hideValidationLabels();
    void showResetingDialog();
    void showResetSuccessMessage();
    void showResetFailMessage();
}