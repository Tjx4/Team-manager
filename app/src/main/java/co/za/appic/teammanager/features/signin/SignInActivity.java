package co.za.appic.teammanager.features.signin;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseNoActionBarActivity;
import co.za.appic.teammanager.di.components.AppComponent;
import co.za.appic.teammanager.di.components.DaggerSignInComponent;
import co.za.appic.teammanager.di.interfaces.DaggerActivity;
import co.za.appic.teammanager.di.modules.SignInModule;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInActivity extends BaseNoActionBarActivity implements DaggerActivity, SignInView {

    @Inject
    ISignInPresenter signInPresenter;

    private EditText usernameTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerSignInComponent.builder().appComponent(appComponent)
                .signInModule(new SignInModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.activity_sign_in);
        initViews();
    }

    @Override
    protected void initViews() {
        usernameTxt = findViewById(R.id.txtUsername);
        passwordTxt = findViewById(R.id.txtPassword);
    }

    @Override
    public SignInPresenter getPresenter() {
        return (SignInPresenter) signInPresenter;
    }

    @Override
    public void onLoginButtonClicked(View view) {

    }

    @Override
    public void onCreateNewAccountClicked(View view) {

    }

    @Override
    public void enterAppAsSupervisor(SupervisorModel supervisor) {

    }

    @Override
    public void enterAppAsWorker(WorkerModel worker) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}