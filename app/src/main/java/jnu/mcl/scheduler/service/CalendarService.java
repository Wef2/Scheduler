package jnu.mcl.scheduler.service;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<CalendarModel> getCalendarList() {
        Connection conn = dbConnector.getConnection();
        ArrayList<CalendarModel> calendarList = new ArrayList<CalendarModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_calendar");
            while (resultSet.next()) {
                CalendarModel calendarModel = new CalendarModel();
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

    public void addCalendarServiceListener(CalendarServiceListener calendarServiceListener){
        if(!calendarServiceListeners.contains(calendarServiceListener)) {
            calendarServiceListeners.add(calendarServiceListener);
        }
    }

    public void notifyCalendarCreate(){
        for(CalendarServiceListener calendarServiceListener : calendarServiceListeners){
            calendarServiceListener.onCalendarCreate();
        }
    }
}
