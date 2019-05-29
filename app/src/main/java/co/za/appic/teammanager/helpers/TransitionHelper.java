package co.za.appic.teammanager.helpers;

import android.content.Context;
import co.za.appic.teammanager.R;

public class TransitionHelper {
    private int inAnimation;
    private int outAnimation;
    private Context context;

    public  TransitionHelper(Context context) {
        this.context = context;
    }

    public int getInAnimation() {
        return inAnimation;
    }
    public void setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
    }
    public int getOutAnimation() {
        return outAnimation;
    }
    public void setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
    }

    public TransitionHelper slideInActivity() {
        return getTransitionAnimation(R.anim.slide_right, R.anim.no_transition);
    }

    public TransitionHelper slideOutActivity() {
        return getTransitionAnimation(R.anim.slide_left, R.anim.no_transition);
    }

    public TransitionHelper fadeInActivity() {
        return getTransitionAnimation(R.anim.fade_in, R.anim.no_transition);
    }

    public TransitionHelper fadeOutActivity() {
        return getTransitionAnimation(R.anim.no_transition, R.anim.fade_out);
    }

    private TransitionHelper getTransitionAnimation(int inAnimation, int outAnimation) {
        this.setInAnimation(inAnimation);
        this.setOutAnimation(outAnimation);
        return this;
    }
}