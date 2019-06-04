package co.za.appic.teammanager.base.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import co.za.appic.teammanager.MyApplication;
import co.za.appic.teammanager.base.presenters.BasePresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.di.interfaces.DaggerActivity;
import co.za.appic.teammanager.fragments.BaseDialogFragment;

public abstract class BaseActivity extends AppCompatActivity implements DaggerActivity {

    protected BasePresenter presenter;
    protected boolean isNewActivity;
    protected BaseDialogFragment dialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewActivity = true;
        setBaseActivityDependencies();
        getActivityTransition();
        setupComponent(MyApplication.get(this).component());
    }

    @Override
    protected void onPause() {
        super.onPause();
        isNewActivity = false;
    }

    private void getActivityTransition() {
        try {
            int[] activityTransition = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).getIntArray(Constants.ACTIVITY_TRANSITION);
            overridePendingTransition(activityTransition[0], activityTransition[1]);
        } catch (Exception e) {
        }
    }

    protected abstract void setBaseActivityDependencies();

    protected void initViews() { }

    protected void showValidationError(TextView errorTv, String errorMessage) {
        errorTv.setVisibility(View.VISIBLE);
        errorTv.setText(errorMessage);

        View preView = getPrevView(errorTv);

        if(preView == null)
            return;

        final Rect rect = new Rect(0, -15, preView.getWidth(), preView.getHeight());
        preView.requestRectangleOnScreen(rect, false);
    }

    private View getPrevView(View triggerView){
        View preView = null;

        try {
            ViewGroup vg = (ViewGroup)triggerView.getParent();
            for (int itemPos = 0; itemPos < vg.getChildCount(); itemPos++) {
                View view = vg.getChildAt(itemPos);
                if (triggerView.equals(view)) {
                    int vIndx = itemPos - 1;
                    preView = vg.getChildAt(vIndx);
                    break;
                }
            }
        }
        catch (Exception e){

        }
        return preView;
    }
}