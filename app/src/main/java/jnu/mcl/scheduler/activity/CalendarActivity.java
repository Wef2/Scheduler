package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.adapter.SectionsPagerAdapter;
import jnu.mcl.scheduler.dialog.AddCalendarDialog;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;

public class CalendarActivity extends AppCompatActivity implements QueryListener {

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
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private CalendarService calendarService = CalendarService.getInstance();
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);
    private ArrayList<CalendarModel> personalCalendarList, shareCalendarList;
    private CalendarListAdapter personalCalendarListAdapter, shareCalendarListAdapter;
    private ListView personalCalendarListView, shareCalendarListView;

    private QueryHandler queryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCalendarDialog addCalendarDialog = new AddCalendarDialog(CalendarActivity.this);
                addCalendarDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        personalCalendarList = new ArrayList<CalendarModel>();
        shareCalendarList = new ArrayList<CalendarModel>();

        personalCalendarListView = (ListView) findViewById(R.id.personalCalendarListView);
        personalCalendarListAdapter = new CalendarListAdapter(this, personalCalendarList);
        personalCalendarListView.setAdapter(personalCalendarListAdapter);
        personalCalendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CalendarActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        queryHandler = new QueryHandler(this, this);
        queryHandler.startQuery(1, null, uri, projection, null, null, null);
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


