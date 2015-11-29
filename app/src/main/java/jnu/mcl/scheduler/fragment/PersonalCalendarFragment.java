package jnu.mcl.scheduler.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.CalendarModel;

/**
 * Created by 김 on 2015-11-29.
 */
public class PersonalCalendarFragment extends Fragment implements QueryListener {

    public Uri uri = CalendarContract.Calendars.CONTENT_URI;
    public String[] projection = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.SYNC_EVENTS,
    };

    private QueryHandler queryHandler;

    private ArrayList<CalendarModel> personalCalendarList = new ArrayList<CalendarModel>();
    private CalendarListAdapter personalCalendarListAdapter;
    private ListView personalCalendarListView;

    public PersonalCalendarFragment(){
    }

    public static PersonalCalendarFragment newInstance() {
        PersonalCalendarFragment fragment = new PersonalCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        personalCalendarListView = (ListView) rootView.findViewById(R.id.calendarListView);
        personalCalendarListAdapter = new CalendarListAdapter(getContext(), personalCalendarList);
        personalCalendarListView.setAdapter(personalCalendarListAdapter);

        queryHandler = new QueryHandler(getContext(), this);
        queryHandler.startQuery(1, null, uri, projection, null, null, null);

        return rootView;
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        personalCalendarList.clear();
        while (cursor.moveToNext()) {
            CalendarModel calendarModel = new CalendarModel();
            calendarModel.setId(cursor.getInt(0));
            calendarModel.setName(cursor.getString(4));
            personalCalendarList.add(calendarModel);
        }
        personalCalendarListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInsertComplete(int token, Object cookie, Uri uri) {

    }

    @Override
    public void onUpdateComplete(int token, Object cookie, int result) {

    }

    @Override
    public void onDeleteComplete(int token, Object cookie, int result) {

    }
}
