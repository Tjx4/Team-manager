package co.za.appic.teammanager.features.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseActionBarActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerRegistrationComponent;
import co.za.appic.teammanager.di.modules.RegistrationModule;

public class RegistrationActivity extends BaseActionBarActivity implements RegistrationView {

    @Inject
    IRegistrationPresenter registrationPresenter;

    private EditText nameTxt;
    private EditText passwordTxt;
    private EditText surrNameTxt;
    private EditText confirmPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActionbarActivityDependencies() {
        currentActionBar.setTitle(context.getString(R.string.create_account_message));
        currentActionBar.setIcon(R.drawable.ic_register_light);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_registration);
        initViews();
    }

    @Override
    protected void initViews() {
        nameTxt = findViewById(R.id.txtName);
        surrNameTxt = findViewById(R.id.txtSurname);
        passwordTxt = findViewById(R.id.txtPassword);
        confirmPasswordTxt = findViewById(R.id.txtConfirmPassword);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerRegistrationComponent.builder().appComponent(appComponent)
                .registrationModule(new RegistrationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRegisterButtonClicked(View view) {

    }

    @Override
    public RegistrationPresenter getPresenter() {
        return (RegistrationPresenter)registrationPresenter;
    }

    @Override
    public void hideLoader() {

    }
}