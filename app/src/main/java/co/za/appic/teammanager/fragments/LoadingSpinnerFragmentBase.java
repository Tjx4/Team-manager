package co.za.appic.teammanager.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wang.avi.AVLoadingIndicatorView;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;

public class LoadingSpinnerFragmentBase extends BaseDialogFragment {
    private TextView loadingTxt;
    private AVLoadingIndicatorView loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setDimAmount(0.9f);

        View parentView = super.onCreateView( inflater,  container, savedInstanceState);
        loader = parentView.findViewById(R.id.progressBarLoading);
        loadingTxt = parentView.findViewById(R.id.txtLoading);

        String loadingMessage = getArguments().getString(Constants.TITLE);
        loadingTxt.setText(loadingMessage);

        return parentView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed(){

            }
        };
    }

    public void hideLoaderAndShowEnterMessage() {
        super.hideLoaderAndShowEnterMessage();
        loader.setVisibility(View.INVISIBLE);
        loadingTxt.setText("Opening app...");
    }

}