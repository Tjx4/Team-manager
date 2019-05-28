package co.za.appic.teammanager.helpers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;

public class DialogFragmentHelper extends DialogFragment {
    protected View clickedView;
    protected AppCompatActivity activity;

    static DialogFragmentHelper newInstance() {
        return new DialogFragmentHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations =  R.style.DialogTheme;

        int layout = getArguments().getInt(Constants.LAYOUT);
        View v = inflater.inflate(layout, container, false);
        return v;
    }

    protected void setViewClickEvents(View[] views) {
        for(View view : views){
            if(view == null)
                continue;

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onFragmentViewClickedEvent(v);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    public void hideLoaderAndShowEnterMessage() {

    }

    protected void setListviewClickEvents(ListView listView) {
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFragmentViewClickedEvent(view);
            }
        });
    }

    protected void onFragmentViewClickedEvent(View view) {
        getDialog().dismiss();
    }
}