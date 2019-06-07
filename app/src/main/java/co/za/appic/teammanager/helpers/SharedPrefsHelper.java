package co.za.appic.teammanager.helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import co.za.appic.teammanager.enums.UserStatus;
import co.za.appic.teammanager.models.SupervisorModel;
import co.za.appic.teammanager.models.UserModel;
import co.za.appic.teammanager.models.WorkerModel;

public class SharedPrefsHelper {
    private SharedPreferences sharedPreferences;
    private AppCompatActivity activity;
    private UserModel baseUserModel;
    private char userStatus;

    private static final String USERSTATUS = "user_status";
    private static final String USER = "user";
    private static final String WORKER = "worker";
    private static final String SUPERVISOR = "supervisor";

    public SharedPrefsHelper(AppCompatActivity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        this.activity = activity;
    }

    public void setLinkedUser(UserModel user){
        if(user == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String connectionsJSONString = new Gson().toJson(user);
        editor.putString(USER, connectionsJSONString);
        editor.commit();
    }

    public UserModel getLinkedUser(){
        String json = sharedPreferences.getString(USER, "");
        Gson gson = new Gson();
        UserModel userModel = gson.fromJson(json, UserModel.class);
        return userModel;
    }

    public void setWorker(WorkerModel worker){
        if(worker == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String connectionsJSONString = new Gson().toJson(worker);
        editor.putString(WORKER, connectionsJSONString);
        editor.commit();
    }

    public WorkerModel getWorker(){
        String json = sharedPreferences.getString(WORKER, "");
        Gson gson = new Gson();
        WorkerModel workerModel = gson.fromJson(json, WorkerModel.class);
        return workerModel;
    }

    public void setSupervisor(SupervisorModel supervisor){
        if(supervisor == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String connectionsJSONString = new Gson().toJson(supervisor);
        editor.putString(SUPERVISOR, connectionsJSONString);
        editor.commit();
    }

    public SupervisorModel getSupervisor(){
        String json = sharedPreferences.getString(SUPERVISOR, "");
        Gson gson = new Gson();
        SupervisorModel supervisorModel = gson.fromJson(json, SupervisorModel.class);
        return supervisorModel;
    }

   public void setUserStatus(UserStatus userStatus){
        if(userStatus == null)
            return;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USERSTATUS, userStatus.getIndex());
        editor.commit();
    }

    public UserStatus getUserStatus() {
        UserModel linkedUser = getLinkedUser();
        if(linkedUser != null)
            return UserStatus.registered;

        int userIndex = sharedPreferences.getInt(USERSTATUS, 0);
        UserStatus userStatus = UserStatus.values()[userIndex];

        if (userIndex == 0)
            userStatus = UserStatus.unregistered;

        return userStatus;
    }
}
