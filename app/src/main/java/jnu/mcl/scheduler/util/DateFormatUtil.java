package jnu.mcl.scheduler.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Kim on 2015-12-01.
 */
public class DateFormatUtil {

    public static String utcToLocal(String utc){
        String resultString;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("KST"));
        resultString = dateFormat.format(Long.parseLong(utc));
        return resultString;
    }

    public static String toUTC(int year, int month, int day, int hour, int minute){
        String monthString = lengthCheck(month);
        String dayString = lengthCheck(day);
        String hourString = lengthCheck(hour);
        String minuteString = lengthCheck(minute);
        String str = Integer.toString(year) + monthString + dayString + hourString + minuteString +" UTC";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm zzz");
        Date date = null;
        try {
            date = df.parse(str);
            Log.w("Test", "Test");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return Long.toString(epoch);
    }

    public static String lengthCheck(int value){
        String returnString;
        if(value < 10){
            returnString = "0" + Integer.toString(value);
        }
        else{
            returnString = Integer.toString(value);
        }
        return returnString;
    }
}
