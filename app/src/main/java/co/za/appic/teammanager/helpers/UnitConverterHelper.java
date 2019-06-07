package co.za.appic.teammanager.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class UnitConverterHelper {
    public static float pixelToDp(float px, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int dp = (int) (px * scale + 0.5f);
        return dp;
    }

    public static float dpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    public static float pxToSp(float px, Context context){
        float sp = px / context.getResources().getDisplayMetrics().scaledDensity;
        return  px;
    }
}
