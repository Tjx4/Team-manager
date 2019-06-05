package co.za.appic.teammanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.za.appic.teammanager.R;

public abstract class BasePagerFragment extends Fragment {
    protected View parentView;
    protected int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_fragment, container, false);
    }

    protected void setValues(){
    }

    protected int getNextStage(){
        return ++index;
    }

    protected abstract void initViews(View parentView);

    protected void setViewClickEvents(View[] views) {
        for(View view : views){
            if(view == null)
                continue;

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}