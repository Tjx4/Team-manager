package co.za.appic.teammanager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.za.appic.teammanager.helpers.DialogFragmentHelper;

public class NoInternetFragment extends DialogFragmentHelper {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setDimAmount(0.9f);

        View parentView = super.onCreateView( inflater,  container, savedInstanceState);

        return parentView;
    }
}