package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.dialog.EventDateDialog;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;
import jnu.mcl.scheduler.service.EventService;

public class SelectCalendarActivity extends AppCompatActivity {

    private CalendarService calendarService = CalendarService.getInstance();
    private EventService eventService = EventService.getInstance();
    private String title, dtstart, dtend;

    private ArrayList<CalendarModel> shareCalendarList;
    private CalendarListAdapter shareCalendarListAdapter;
    private ListView shareCalendarListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        dtstart = intent.getStringExtra("dtstart");
        dtend = intent.getStringExtra("dtend");

        shareCalendarList = calendarService.getCalendarList();
        shareCalendarListView = (ListView) findViewById(R.id.listView);
        shareCalendarListAdapter = new CalendarListAdapter(this);
        shareCalendarListAdapter.changeList(calendarService.getCalendarList());
        shareCalendarListView.setAdapter(shareCalendarListAdapter);
        shareCalendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int calendar_no = shareCalendarListAdapter.getItem(position).getCalendar_no();
                eventService.addEvent(calendar_no, title, dtstart, dtend);
                finish();
            }
        });


    }

}
