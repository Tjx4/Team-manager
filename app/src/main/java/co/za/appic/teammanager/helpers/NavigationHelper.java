package co.za.appic.teammanager.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import co.za.appic.teammanager.constants.Constants;

public class NavigationHelper {

    private static void goToActivity(Context activityFrom, Class activityTo, TransitionHelper transitionAnimation, Bundle payload) {
        Intent intent = new Intent(activityFrom, activityTo);

        Bundle fullPayload = (payload == null) ? new Bundle() : payload;
        fullPayload.putIntArray(Constants.ACTIVITY_TRANSITION, new int[]{transitionAnimation.getInAnimation(), transitionAnimation.getOutAnimation()});

        intent.putExtra(Constants.PAYLOAD_KEY, fullPayload);
        activityFrom.startActivity(intent);
    }

    public static void goToActivityWithNoPayload(Context activityFrom, Class activity, TransitionHelper transitionAnimation) {
        goToActivity(activityFrom, activity, transitionAnimation, null);
    }

    public static void goToActivityWithPayload(Context activityFrom, Class activity, Bundle payload, TransitionHelper transitionAnimation) {
        goToActivity(activityFrom, activity, transitionAnimation, payload);
    }

}
