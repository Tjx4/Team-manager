package co.za.appic.teammanager.helpers;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

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
}