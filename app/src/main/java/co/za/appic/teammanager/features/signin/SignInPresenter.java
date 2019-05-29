package co.za.appic.teammanager.features.signin;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class SignInPresenter extends BaseAsyncPresenter implements ISignInPresenter {

    private SignInView signInView;

    public SignInPresenter(SignInView signInView){
        super(signInView);
        this.signInView = signInView;
    }

    @Override
    public void SignInUser(String username, String password) {

    }

    @Override
    public void showSignIndError(String title, String message) {

    }
}
