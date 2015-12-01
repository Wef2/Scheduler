package jnu.mcl.scheduler.model;

import android.net.Uri;
import android.provider.CalendarContract;

/**
 * Created by Kim on 2015-12-01.
 */
public class QueryModel {

    private static QueryModel newInstance;

    private QueryModel() {
    }

    public static QueryModel getInstance() {
        if (newInstance == null) {
            newInstance = new QueryModel();
        }
        return newInstance;
    }

    public Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
    public String[] calendarProjection = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.SYNC_EVENTS,
    };

    private Uri eventUri = CalendarContract.Events.CONTENT_URI;
    private String[] eventProjection = new String[]{
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };

    public Uri getCalendarUri() {
        return calendarUri;
    }

    public String[] getCalendarProjection() {
        return calendarProjection;
    }
    public Uri getEventUri() {
        return eventUri;
    }

    public String[] getEventProjection() {
        return eventProjection;
    }
}
