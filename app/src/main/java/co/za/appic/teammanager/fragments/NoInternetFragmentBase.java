package co.za.appic.teammanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.za.appic.teammanager.features.dashboard.supervisor.fragments.TaskDescriptionAndPriorityFragment;

public class NoInternetFragmentBase extends BaseDialogFragment {

    public static NoInternetFragmentBase newInstance(Activity context, Bundle bundle) {
        return (NoInternetFragmentBase)Fragment.instantiate(context, NoInternetFragmentBase.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setDimAmount(0.9f);

        View parentView = super.onCreateView( inflater,  container, savedInstanceState);

        return parentView;
    }
}