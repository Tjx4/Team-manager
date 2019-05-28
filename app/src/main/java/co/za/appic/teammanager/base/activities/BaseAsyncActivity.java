package co.za.appic.teammanager.base.activities;

import android.os.Bundle;

public abstract class BaseAsyncActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onBackPressed() {
        super.onBackPressed();
        presenter.handleBackButtonPressed();
    }

    public void showLoadingDialog(String loadingMessage) {
    }

    public void hideLoadingDialog() {
    }

    protected void showNoInternetActivity() {

    }
}