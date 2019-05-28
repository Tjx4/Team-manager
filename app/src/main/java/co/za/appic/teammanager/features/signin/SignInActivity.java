package co.za.appic.teammanager.features.signin;

import android.os.Bundle;
import android.view.View;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.activities.BaseNoActionBarActivity;

public class SignInActivity extends BaseNoActionBarActivity implements SignInView{

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

    }

    @Override
    public void onLoginButtonClicked(View view) {

    }

}
