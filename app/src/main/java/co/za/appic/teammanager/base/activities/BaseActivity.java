package co.za.appic.teammanager.base.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.base.presenters.BasePresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;
import co.za.appic.teammanager.helpers.PermissionsHelper;
import co.za.appic.teammanager.helpers.TransitionHelper;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected BasePresenter presenter;
    protected PermissionsHelper permissionsHelper;
    protected DialogFragmentHelper dialogFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        permissionsHelper = new PermissionsHelper(this);
        setBaseActivityDependencies();

        try {
            int[] activityTransition = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).getIntArray(Constants.ACTIVITY_TRANSITION);
            overridePendingTransition(activityTransition[0], activityTransition[1]);
        } catch (Exception e) {

        }
    }

    protected abstract void setBaseActivityDependencies();

    public void showShortToast(String message) {
        Toast toast = getToast(message, Toast.LENGTH_SHORT);
        showTheToastIfNotNull(toast);
    }

    public void showLongToast(String message) {
        Toast toast = getToast(message, Toast.LENGTH_LONG);
        showTheToastIfNotNull(toast);
    }

    private void showTheToastIfNotNull(Toast toast){
        if (toast != null)
            toast.show();
    }

    private Toast getToast(String message, int length) {
        try {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.root));
            TextView text = layout.findViewById(R.id.toast_message);
            text.setText(message);
            Toast toast = new Toast(context);
            toast.setView(layout);
            toast.setDuration(length);

            return toast;
        } catch (Exception e) {
            Log.e(Constants.TOAST_ERROR, "" + e);
            return null;
        }
    }

    protected TransitionHelper slideInActivity() {
        return getTransitionAnimation(R.anim.slide_right, R.anim.no_transition);
    }

    protected TransitionHelper slideOutActivity() {
        return getTransitionAnimation(R.anim.no_transition, R.anim.slide_left);
    }

    protected TransitionHelper fadeInActivity() {
        return getTransitionAnimation(R.anim.fade_in, R.anim.no_transition);
    }

    public TransitionHelper fadeOutActivity() {
        return getTransitionAnimation(R.anim.no_transition, R.anim.fade_out);
    }

    private TransitionHelper getTransitionAnimation(int inAnimation, int outAnimation) {
        TransitionHelper transitionProvider = new TransitionHelper();
        transitionProvider.setInAnimation(inAnimation);
        transitionProvider.setOutAnimation(outAnimation);
        return transitionProvider;
    }
}