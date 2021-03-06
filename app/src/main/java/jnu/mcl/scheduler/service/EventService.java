package jnu.mcl.scheduler.service;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.model.EventModel;

/**
 * Created by Kim on 2015-11-30.
 */
public class EventService {

    private ArrayList<EventServiceListener> eventServiceListeners;
    private static EventService newInstance;
    private DBConnector dbConnector;

    private EventService() {
        dbConnector = DBConnector.getInstance();
        eventServiceListeners = new ArrayList<EventServiceListener>();
    }

    public static EventService getInstance() {
        if (newInstance == null) {
            newInstance = new EventService();
        }
        return newInstance;
    }

    public ArrayList<EventModel> getEventList() {
        Connection conn = dbConnector.getConnection();
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_event order by dtstart asc");
            while (resultSet.next()) {
                EventModel eventModel = new EventModel();
                eventModel.setEvent_no(resultSet.getInt(1));
                eventModel.setTitle(resultSet.getString(3));
                eventModel.setDtstart(resultSet.getString(4));
                eventModel.setDtend(resultSet.getString(5));
                eventList.add(eventModel);
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return eventList;
    }

    public ArrayList<EventModel> getEventList(int calendar_no) {
        Connection conn = dbConnector.getConnection();
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_event where calendar_no="+calendar_no+"order by dtstart asc");
            while (resultSet.next()) {
                EventModel eventModel = new EventModel();
                eventModel.setEvent_no(resultSet.getInt(1));
                eventModel.setTitle(resultSet.getString(3));
                eventModel.setDtstart(resultSet.getString(4));
                eventModel.setDtend(resultSet.getString(5));
                eventList.add(eventModel);
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return eventList;
    }

    public ArrayList<EventModel> getTodayEventList(Long now, Long todayEnd) {
        Connection conn = dbConnector.getConnection();
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_event dtstart order by dtstart asc");
            while (resultSet.next()) {
                if(Long.parseLong(resultSet.getString(5)) >= now &&Long.parseLong(resultSet.getString(4)) <= todayEnd) {
                    EventModel eventModel = new EventModel();
                    eventModel.setEvent_no(resultSet.getInt(1));
                    eventModel.setTitle(resultSet.getString(3));
                    eventModel.setDtstart(resultSet.getString(4));
                    eventModel.setDtend(resultSet.getString(5));
                    eventList.add(eventModel);
                }
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return eventList;
    }


    public EventModel getEvent(int event_no) {
        Connection conn = dbConnector.getConnection();
        EventModel eventModel = new EventModel();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_event where event_no='"+event_no+"'");
            while (resultSet.next()) {
                eventModel.setEvent_no(resultSet.getInt(1));
                eventModel.setTitle(resultSet.getString(3));
                eventModel.setDtstart(resultSet.getString(4));
                eventModel.setDtend(resultSet.getString(5));
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return eventModel;
    }

    public void addEvent(int calendar_no, String title, String dtstart) {
        Connection conn = dbConnector.getConnection();
        try {
            String query = "insert into t_event (calendar_no, title, dtstart) values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, calendar_no);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, dtstart);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyEventCreate();
    }

    public void addEvent(int calendar_no, String title, String dtstart, String dtend) {
        Connection conn = dbConnector.getConnection();
        try {
            String query = "insert into t_event (calendar_no, title, dtstart, dtend) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, calendar_no);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, dtstart);
            preparedStatement.setString(4, dtend);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyEventCreate();
    }

    public void updateEvent(int event_no, String title, String dtstart, String dtend){
        Connection conn = dbConnector.getConnection();
        try {
            String query = "update t_event SET title=?, dtstart=?, dtend=? where event_no=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, dtstart);
            preparedStatement.setString(3, dtend);
            preparedStatement.setInt(4, event_no);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyEventUpdate();
    }

    public void deleteEvent(int event_no){
        Connection conn = dbConnector.getConnection();
        try {
            String query = "delete from t_event where event_no=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, event_no);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        notifyEventDelete();

    }

    public void addEventServiceListener(EventServiceListener calendarServiceListener) {
        if (!eventServiceListeners.contains(calendarServiceListener)) {
            eventServiceListeners.add(calendarServiceListener);
        }
    }

    public void notifyEventCreate() {
        for (EventServiceListener calendarServiceListener : eventServiceListeners) {
            calendarServiceListener.onEventCreate();
        }
    }

    public void notifyEventUpdate() {
        for (EventServiceListener calendarServiceListener : eventServiceListeners) {
            calendarServiceListener.onEventUpdate();
        }
    }

    public void notifyEventDelete() {
        for (EventServiceListener calendarServiceListener : eventServiceListeners) {
            calendarServiceListener.onEventDelete();
        }
    }
}
