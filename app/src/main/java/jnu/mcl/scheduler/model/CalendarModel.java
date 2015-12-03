package jnu.mcl.scheduler.model;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;

/**
 * Created by Kim on 2015-11-27.
 */
public class CalendarModel {

    private String id;
    private int calendar_no;

    private String accountName;
    private String accountType;
    private String name;
    private String calendarDisplayName;
    private String calendarColor;
    private String calendarAccessLevel;
    private String ownerAccount;

    private boolean syncEvents;
    private String calendarTimeZone;
    private String allowedReminders;
    private String allowedAvailability;
    private String allowedAttendeeTypes;

    public void setId(String id) {
        this.id = id;
    }

    public int getCalendar_no() {
        return calendar_no;
    }

    public void setCalendar_no(int calendar_no) {
        this.calendar_no = calendar_no;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalendarDisplayName() {
        return calendarDisplayName;
    }

    public void setCalendarDisplayName(String calendarDisplayName) {
        this.calendarDisplayName = calendarDisplayName;
    }

    public String getId() {
        return this.id;
    }

    public String getCalendarColor() {
        return calendarColor;
    }

    public void setCalendarColor(String calendarColor) {
        this.calendarColor = calendarColor;
    }

    public String getCalendarAccessLevel() {
        return calendarAccessLevel;
    }

    public void setCalendarAccessLevel(String calendarAccessLevel) {
        this.calendarAccessLevel = calendarAccessLevel;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

}