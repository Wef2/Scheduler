package jnu.mcl.scheduler.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;

public class CalendarActivity extends AppCompatActivity implements QueryListener {

    private CalendarService calendarService = CalendarService.getInstance();
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);

    private ArrayList<CalendarModel> calendarModelArrayList = new ArrayList<CalendarModel>();
    private CalendarListAdapter calendarListAdapter;
    private ListView calendarListView;

    private QueryHandler queryHandler;

    public Uri uri = CalendarContract.Calendars.CONTENT_URI;

    public String[] projection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_LOCATION,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.SYNC_EVENTS,
                CalendarContract.Calendars.VISIBLE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        calendarListView = (ListView) findViewById(R.id.calendarListView);
        calendarListAdapter = new CalendarListAdapter(this, calendarModelArrayList);
        calendarListView.setAdapter(calendarListAdapter);

        queryHandler = new QueryHandler(this, this);
        queryHandler.startQuery(1, null, uri, projection, null, null, null);
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        calendarModelArrayList.clear();
        while(cursor.moveToNext()) {
            CalendarModel calendarModel = new CalendarModel();
            calendarModel.setId(cursor.getInt(0));
            calendarModel.setName(cursor.getString(2));
            calendarModelArrayList.add(calendarModel);
        }
        calendarListAdapter.notifyDataSetChanged();
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
