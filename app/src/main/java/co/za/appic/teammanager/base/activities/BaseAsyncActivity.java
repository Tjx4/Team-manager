package co.za.appic.teammanager.base.activities;

import android.os.Bundle;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.fragments.LoadingSpinnerFragment;

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
        notificationHelper.showFragmentDialog(loadingMessage, R.layout.fragment_loading_spinner, new LoadingSpinnerFragment());
    }

    public void hideLoadingDialog() {
    }

    protected void showNoInternetActivity() {

    }
}