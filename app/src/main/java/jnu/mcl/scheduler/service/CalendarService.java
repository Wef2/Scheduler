package jnu.mcl.scheduler.service;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.model.CalendarModel;

/**
 * Created by 김 on 2015-11-27.
 */
public class CalendarService {

    private static CalendarService newInstance;
    private DBConnector dbConnector;

    private CalendarService() {
        dbConnector = DBConnector.getInstance();
    }

    public static CalendarService getInstance() {
        if (newInstance == null) {
            newInstance = new CalendarService();
        }
        return newInstance;
    }

    public ArrayList<CalendarModel> getCalendarList() {
        Connection conn = dbConnector.getConnection();
        ArrayList<CalendarModel> calendarModelArrayList = new ArrayList<CalendarModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_calendar");
            while (resultSet.next()) {
                CalendarModel calendarModel = new CalendarModel();
                calendarModel.setNo(resultSet.getInt(1));
                calendarModel.setName(resultSet.getString(2));
                calendarModel.setDescription(resultSet.getString(5));
                calendarModelArrayList.add(calendarModel);
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return calendarModelArrayList;
    }

    public void addCalendar() {

    }
}
