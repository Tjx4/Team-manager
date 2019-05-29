package co.za.appic.teammanager.helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import co.za.appic.teammanager.enums.UserStatus;
import co.za.appic.teammanager.models.UserModel;

public class CacheHelper {
    private SharedPreferences sharedPreferences;
    private AppCompatActivity activity;
    private UserModel baseUserModel;
    private char userStatus;
    //Constants
    private static final String USERSTATUS = "user_status";

    public CacheHelper(AppCompatActivity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        this.activity = activity;
    }

    public void setUserStatus(UserStatus userStatus){
        if(userStatus == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USERSTATUS, userStatus.getIndex());
        editor.commit();
    }

    public UserStatus getUserStatus() {
        int userIndex = sharedPreferences.getInt(USERSTATUS, 0);
        UserStatus userStatus = UserStatus.values()[userIndex];

        if (userIndex == 0)
            userStatus = UserStatus.unregistered;

        return userStatus;
    }
}
