package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.EventListAdapter;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.DateModel;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.model.QueryModel;
import jnu.mcl.scheduler.service.EventService;
import jnu.mcl.scheduler.util.DateFormatUtil;

public class ScheduleActivity extends AppCompatActivity implements QueryListener {

    private EventService eventService = EventService.getInstance();
    private QueryModel queryModel = QueryModel.getInstance();
    private QueryHandler queryHandler;

    private ArrayList<EventModel> eventList = new ArrayList<EventModel>();
    private EventListAdapter eventListAdapter;
    private ListView eventListView;

    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);

    private DateModel dateModel;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        dateModel = DateFormatUtil.epochToModel(Long.toString(System.currentTimeMillis()));

        textView = (TextView)findViewById(R.id.todayText);
        textView.setText(dateModel.getYear() + "년 " + dateModel.getMonth() + "월 " + dateModel.getDay() + "일");

        eventListView = (ListView) findViewById(R.id.listView);
        eventListAdapter = new EventListAdapter(ScheduleActivity.this);
        eventListView.setAdapter(eventListAdapter);

        String now = DateFormatUtil.toUTC(Integer.toString(dateModel.getYear()),lengthCheck(dateModel.getMonth()),lengthCheck(dateModel.getDay()),
                lengthCheck(dateModel.getHour()),lengthCheck(dateModel.getDay()));
        String todayEnd = DateFormatUtil.toUTC(Integer.toString(dateModel.getYear()),lengthCheck(dateModel.getMonth()),lengthCheck(dateModel.getDay()),
                "23","59");

        eventList = eventService.getTodayEventList(Long.parseLong(now), Long.parseLong(todayEnd));
        eventListAdapter.changeList(eventList);

    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        while (cursor.moveToNext()) {
            EventModel eventModel = new EventModel();
            eventModel.setId(cursor.getString(0));
            eventModel.setTitle(cursor.getString(1));
            eventModel.setDtstart(cursor.getString(2));
            eventModel.setDtend(cursor.getString(3));
            eventList.add(eventModel);
        }
        eventListAdapter.changeList(eventList);
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

    public String lengthCheck(int value){
        String returnString;
        if(value < 10){
            returnString = "0" + Integer.toString(value);
        }
        else{
            returnString = Integer.toString(value);
        }
        return returnString;
    }
}