package co.za.appic.teammanager.features.forgotPassword;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class ForgotPasswordPresenter extends BaseAsyncPresenter {

    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenter(ForgotPasswordView forgotPasswordView) {
        super(forgotPasswordView);
        this.forgotPasswordView = forgotPasswordView;
    }

    public void resetPassword(String email) {

    }
}