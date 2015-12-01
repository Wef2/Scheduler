package jnu.mcl.scheduler.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.activity.PersonalEventActivity;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.model.QueryModel;

/**
 * Created by 김 on 2015-11-29.
 */
public class PersonalCalendarFragment extends Fragment implements QueryListener {

    private QueryModel queryModel;
    private QueryHandler queryHandler;

    private ArrayList<CalendarModel> personalCalendarList = new ArrayList<CalendarModel>();
    private CalendarListAdapter personalCalendarListAdapter;
    private ListView personalCalendarListView;

    public PersonalCalendarFragment() {
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
        personalCalendarListAdapter = new CalendarListAdapter(getContext());
        personalCalendarListView.setAdapter(personalCalendarListAdapter);
        personalCalendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PersonalEventActivity.class);
                startActivity(intent);
            }
        });
        queryHandler = new QueryHandler(getContext(), this);
        queryHandler.startQuery(1, null, queryModel.getCalendarUri(), queryModel.getCalendarProjection(), null, null, null);

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
        personalCalendarListAdapter.changeList(personalCalendarList);
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
