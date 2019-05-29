package co.za.appic.teammanager.features.registration;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;

public class RegistrationPresenter extends BaseAsyncPresenter implements IRegistrationPresenter {

    private RegistrationView registrationView;

    public RegistrationPresenter(RegistrationView registrationView) {
        super(registrationView);
        this.registrationView = registrationView;
    }
}