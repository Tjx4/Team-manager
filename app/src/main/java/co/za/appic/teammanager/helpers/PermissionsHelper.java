package co.za.appic.teammanager.helpers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class PermissionsHelper {

    public AppCompatActivity activity;
    public final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public PermissionsHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public boolean isPermissionGranted(String permision) {
        if(isbellowMashMellow() )
            return true;

        return activity.checkSelfPermission(permision) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestAccessFineLocationPermission() {
        if(isbellowMashMellow() )
            return;

        if (ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void requestAccessCoarseLocationPermission() {
        if(isbellowMashMellow() )
            return;

        if (ContextCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }

    public void requestReadPhoneStatePermission() {
        if(isbellowMashMellow() )
            return;

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }

    public void requestTelePhonyPermission() {
        if(isbellowMashMellow() )
            return;

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE}, 1);
            }
        }
    }

    public void requestWriteStoragePermission() {
        if(isbellowMashMellow() )
            return;

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    public boolean isbellowMashMellow() {
        return Build.VERSION.SDK_INT < 23;
    }
}
