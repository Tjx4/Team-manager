package co.za.appic.teammanager.features.signin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import javax.inject.Inject;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseNoActionBarActivity;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SignInActivity extends BaseNoActionBarActivity implements SignInView {

    @Inject
    ISigninPresenter signInPresenter;

    private EditText usernameTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public SigninPresenter getPresenter() {
        return (SigninPresenter) signInPresenter;
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

}