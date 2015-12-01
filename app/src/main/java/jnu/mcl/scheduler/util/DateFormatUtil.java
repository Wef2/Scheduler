package jnu.mcl.scheduler.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Kim on 2015-12-01.
 */
public class DateFormatUtil {

    public static String utcToLocal(String utc){
        String resultString;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        format.setTimeZone(TimeZone.getDefault());
        resultString = format.format(Long.parseLong(utc));
        return resultString;
    }
}
