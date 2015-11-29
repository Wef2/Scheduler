package jnu.mcl.scheduler.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.activity.EventActivity;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;

/**
 * Created by 김 on 2015-11-29.
 */
public class ShareCalendarFragment extends Fragment {

    private CalendarService calendarService = CalendarService.getInstance();

    private ArrayList<CalendarModel> shareCalendarList;
    private CalendarListAdapter shareCalendarListAdapter;
    private ListView shareCalendarListView;

    public ShareCalendarFragment() {

    }

    public static ShareCalendarFragment newInstance() {
        ShareCalendarFragment fragment = new ShareCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        shareCalendarList = calendarService.getCalendarList();
        shareCalendarListView = (ListView) rootView.findViewById(R.id.calendarListView);
        shareCalendarListAdapter = new CalendarListAdapter(getContext(), shareCalendarList);
        shareCalendarListView.setAdapter(shareCalendarListAdapter);
        shareCalendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
