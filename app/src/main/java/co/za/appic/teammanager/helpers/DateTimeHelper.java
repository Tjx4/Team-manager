package co.za.appic.teammanager.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    public static String getYear() {
        //2018
        SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getdMonth() {
        //February
        SimpleDateFormat formatedDate = new SimpleDateFormat("MMMM");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getYearAndMonth() {
        //February 8, 2018
        SimpleDateFormat formatedDate = new SimpleDateFormat("MMMM d, yyyy");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getYearMonthDay() {
        //Thursday, February 8, 2018
        SimpleDateFormat formatedDate = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getYearMonthDayAndTime() {
        // Thu, 2018-2-8 at 4:59:57 AM GMT+02:00
        SimpleDateFormat formatedDate = new SimpleDateFormat("y-MM-dd hh:mm");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getYearMonthDayAndTimeSeconds() {
        // Thu, 2018-2-8 at 4:59:57 AM GMT+02:00
        SimpleDateFormat formatedDate = new SimpleDateFormat("y-MM-dd hh:mm:ss");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }

    public static String getDayYearMonthAtTimeAndTimeZone() {
        // Thu, 2018-2-8 at 4:59:57 AM GMT+02:00
        SimpleDateFormat formatedDate = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
        String StringDate = formatedDate.format(new Date());
        return StringDate;
    }


}
