package jnu.mcl.scheduler.util;

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
        String str = Integer.toString(year) + Integer.toString(month) + Integer.toString(day) + Integer.toString(hour) + Integer.toString(minute)+" UTC";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm zzz");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return Long.toString(epoch);
    }
}
