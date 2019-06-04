package co.za.appic.teammanager.base.activities;

import android.os.Bundle;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.fragments.LoadingSpinnerFragment;
import co.za.appic.teammanager.fragments.NoInternetFragment;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;
import co.za.appic.teammanager.helpers.NotificationHelper;

public abstract class BaseAsyncActivity extends BaseActivity{

    private LoadingSpinnerFragment loadingDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onBackPressed() {
        super.onBackPressed();
        //presenter.handleBackButtonPressed();

        if(dialogFragment != null)
            dialogFragment.dismiss();
    }

    public void showLoadingDialog(String loadingMessage) {
        loadingDialogFragment = new LoadingSpinnerFragment();
        NotificationHelper.showFragmentDialog(this, loadingMessage, R.layout.fragment_loading_spinner, loadingDialogFragment);
    }

    public void showDialogFragment(String title, int Layout, DialogFragmentHelper newFragment) {
        NotificationHelper.showFragmentDialog(this, title, Layout, newFragment);
        dialogFragment = newFragment;
    }

    public void hideLoadingDialog() {
        if(loadingDialogFragment != null)
            loadingDialogFragment.dismiss();
    }

    protected void showNoInternetActivity() {
        dialogFragment = new NoInternetFragment();
        showDialogFragment(getString(R.string.no_internet), R.layout.fragment_no_internet , dialogFragment);
    }
}