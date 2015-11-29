package jnu.mcl.scheduler.listener;

/**
 * Created by 김 on 2015-11-30.
 */
public interface CalendarServiceListener {

    void onCalendarCreate();
    void onCalendarDelete();
    void onCalendarUpdate();
}
