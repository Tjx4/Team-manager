package co.za.appic.teammanager.helpers;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {

    public static void blinkView(View view){
        if(view == null)
            return;

        Animation anim = new AlphaAnimation(0.7f, 1.0f);
        anim.setDuration(60);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(0);
        view.startAnimation(anim);
    }

    public static void initializeViews(final ViewGroup parentLayout, final View activeView){
        int childCount = parentLayout.getChildCount();

        for(int i  = 0; i < childCount; ++i){
            View currentChild = parentLayout.getChildAt(i);

            if(currentChild.getId() == activeView.getId()){
                currentChild.setVisibility(View.VISIBLE);
            }
            else{
                currentChild.setVisibility(View.GONE);
                TranslateAnimation anim = new TranslateAnimation(0, -activeView.getWidth(), 0, 0);
                anim.setDuration(0);
                currentChild.setAnimation(anim);
                currentChild.startAnimation(anim);
            }

        }
    }

    public static void shuffleViews(final ViewGroup parentLayout, final View activeView, final View incomingView){

        if(activeView == incomingView)
            return;

        final int duration = 400;

        TranslateAnimation anim = new TranslateAnimation(0, -activeView.getWidth(), 0, 0);
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation a) {
            }
            public void onAnimationRepeat(Animation a) {

            }
            public void onAnimationEnd(Animation a) {
                int childCount = parentLayout.getChildCount();

                for(int i  = 0; i < childCount; ++i){
                    View currentChild = parentLayout.getChildAt(i);

                    if(currentChild.getId() == incomingView.getId()){
                        incomingView.setVisibility(View.VISIBLE);
                        TranslateAnimation anim = new TranslateAnimation(currentChild.getWidth(), 0, 0, 0);
                        anim.setDuration(duration);
                        currentChild.setAnimation(anim);
                        currentChild.startAnimation(anim);
                    }
                    else if(currentChild.getId() == activeView.getId()){
                        currentChild.setVisibility(View.GONE);
                    }
                    else{
                        currentChild.setVisibility(View.GONE);
                    }

                }
            }
        });
        activeView.setAnimation(anim);
        activeView.startAnimation(anim);

    }
}