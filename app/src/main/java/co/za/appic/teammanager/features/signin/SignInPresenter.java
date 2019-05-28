package co.za.appic.teammanager.features.signin;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class SignInPresenter extends BaseAsyncPresenter implements ISignInPresenter {

    private SignInView signInView;

    public SignInPresenter(SignInView signInView){
        super(signInView);
    }
}
