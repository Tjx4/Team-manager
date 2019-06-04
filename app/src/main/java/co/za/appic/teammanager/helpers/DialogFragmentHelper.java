package co.za.appic.teammanager.helpers;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.fragments.BaseDialogFragment;

public class DialogFragmentHelper {

    public static void showFragment(AppCompatActivity activity, Bundle payload, String title, int Layout, BaseDialogFragment newFragment) {
        if(payload == null)
            payload = new Bundle();

        payload.putString(Constants.TITLE, title);
        payload.putInt(Constants.LAYOUT, Layout);

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        newFragment.setArguments(payload);
        newFragment.show(ft, activity.getResources().getString(R.string.dialo_def_tag));
    }
}