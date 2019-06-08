package co.za.appic.teammanager.helpers;

import co.za.appic.teammanager.models.UserModel;

public class ImageHelper {

    public static String getProfilePicPath(UserModel user){
        return "users"+"/"+user.getFbId()+"/profpic/"+user.getProfilePicUrl();
    }

}