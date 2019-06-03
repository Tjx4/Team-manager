package co.za.appic.teammanager.helpers;

import co.za.appic.teammanager.R;

public class TransitionHelper {
    private static int inAnimation;
    private static int outAnimation;

    private static int[] getTransitions(int inTransition, int outTransition) {
        return new int[]{inTransition, outTransition};
    }

    public static int[] slideInActivity() {
        return getTransitions(R.anim.slide_right, R.anim.no_transition);
    }

    public static int[] slideOutActivity() {
        return getTransitions(R.anim.fade_in, R.anim.slide_out);
    }

    public static int[] transitionInActivity() {
        return getTransitions(R.anim.slide_out, R.anim.slide_in);
    }

    public static int[] transitionOutActivity() {
        return getTransitions(R.anim.slide_in, R.anim.slide_out);
    }

    public static int[] fadeInActivity() {
        return getTransitions(R.anim.fade_in, R.anim.no_transition);
    }

    public static int[] fadeOutActivity() {
        return getTransitions(R.anim.no_transition, R.anim.fade_out);
    }

}