package jnu.mcl.scheduler.service;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.CalendarServiceListener;
import jnu.mcl.scheduler.model.CalendarModel;

/**
 * Created by 김 on 2015-11-27.
 */
public class CalendarService {

    private ArrayList<CalendarServiceListener> calendarServiceListeners;
    private static CalendarService newInstance;
    private DBConnector dbConnector;

    private CalendarService() {
        dbConnector = DBConnector.getInstance();
        calendarServiceListeners = new ArrayList<CalendarServiceListener>();
    }

    public static CalendarService getInstance() {
        if (newInstance == null) {
            newInstance = new CalendarService();
        }
        return newInstance;
    }

    public CalendarModel getCalendar(int calendar_no) {
        Connection conn = dbConnector.getConnection();
        CalendarModel calendarModel = new CalendarModel();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_calendar where no=" + calendar_no);
            while (resultSet.next()) {
                calendarModel.setCalendar_no(resultSet.getInt(1));
                calendarModel.setName(resultSet.getString(5));
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return calendarModel;
    }

    public ArrayList<CalendarModel> getCalendarList() {
        Connection conn = dbConnector.getConnection();
        ArrayList<CalendarModel> calendarList = new ArrayList<CalendarModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_calendar");
            while (resultSet.next()) {
                CalendarModel calendarModel = new CalendarModel();
                calendarModel.setCalendar_no(resultSet.getInt(1));
                calendarModel.setName(resultSet.getString(5));
                calendarList.add(calendarModel);
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return calendarList;
    }

    public void addCalendar(String account_name, String account_type, String name, String calendar_display_name) {
        Connection conn = dbConnector.getConnection();
        try {
            String query = "insert into t_calendar (account_name, account_type, name, calendar_display_name) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, account_name);
            preparedStatement.setString(2, account_type);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, calendar_display_name);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyCalendarCreate();
    }

    public void updateCalendar(int calendar_no, String name){
        Connection conn = dbConnector.getConnection();
        try {
            String query = "update t_calendar SET name=? where no=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, calendar_no);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyCalendarUpdate();
    }

    public void deleteCalendar(int calendar_no) {
        Connection conn = dbConnector.getConnection();
        try {
            String query1 = "delete from t_event where calendar_no=?";
            PreparedStatement preparedStatement1 = conn.prepareStatement(query1);
            preparedStatement1.setInt(1, calendar_no);
            preparedStatement1.executeUpdate();
            String query2 = "delete from t_calendar where no=?";
            PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
            preparedStatement2.setInt(1, calendar_no);
            preparedStatement2.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyCalendarDelete();
    }

    public void addCalendarServiceListener(CalendarServiceListener calendarServiceListener) {
        if (!calendarServiceListeners.contains(calendarServiceListener)) {
            calendarServiceListeners.add(calendarServiceListener);
        }
    }

    public void notifyCalendarCreate() {
        for (CalendarServiceListener calendarServiceListener : calendarServiceListeners) {
            calendarServiceListener.onCalendarCreate();
        }
    }

    public void notifyCalendarDelete() {
        for (CalendarServiceListener calendarServiceListener : calendarServiceListeners) {
            calendarServiceListener.onCalendarDelete();
        }
    }

    public void notifyCalendarUpdate() {
        for (CalendarServiceListener calendarServiceListener : calendarServiceListeners) {
            calendarServiceListener.onCalendarUpdate();
        }
    }
}
