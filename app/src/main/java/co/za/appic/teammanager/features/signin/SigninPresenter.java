package co.za.appic.teammanager.features.signin;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class SigninPresenter extends BaseAsyncPresenter implements ISigninPresenter{

    private SignInView signInView;

    public SigninPresenter(SignInView signInView){
        super(signInView);
    }
}
