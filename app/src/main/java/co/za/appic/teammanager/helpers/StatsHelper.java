package co.za.appic.teammanager.helpers;

public class StatsHelper {
    public static void sendErroReport(Exception error, String simpleErrorDescription){
        if(error.getMessage().equals("Index 0 requested, with a size of 0"))
            return;

        // Get device details
        String deviceType = "";
        String device  = "";
        String dateAndTime = "";
    }
}