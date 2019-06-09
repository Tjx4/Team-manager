package co.za.appic.teammanager.helpers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionsHelper {
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static boolean isPermissionGranted(AppCompatActivity activity, String permission) {
        if(isbellowMashMellow() )
            return true;

        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isWriteExternalStoragePermissionGranted(AppCompatActivity activity) {
        return isPermissionGranted(activity, WRITE_EXTERNAL_STORAGE);
    }

    public static void requestWriteExternalStoragePermission(AppCompatActivity activity) {
        if(isbellowMashMellow() )
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE)) {

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public static boolean isbellowMashMellow() {
        return Build.VERSION.SDK_INT < 23;
    }
}
