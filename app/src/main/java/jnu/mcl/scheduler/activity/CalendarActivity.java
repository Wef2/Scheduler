package jnu.mcl.scheduler.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.model.CalendarModel;

public class CalendarActivity extends AppCompatActivity {

    private DBConnector dbConnector = DBConnector.getInstance();
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);

    private ArrayList<CalendarModel> calendarModelArrayList = new ArrayList<CalendarModel>();
    private CalendarListAdapter calendarListAdapter;
    private ListView calendarListView;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        getCalendarListFromDB();
    }

    public void getCalendarListFromDB() {
        Connection conn = dbConnector.getConnection();
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
            calendarListAdapter = new CalendarListAdapter(this, calendarModelArrayList);
            calendarListView.setAdapter(calendarListAdapter);
            conn.close();
        } catch (Exception e) {
            Toast.makeText(this, "DB Connection Error", Toast.LENGTH_SHORT).show();
            Log.w("Error connection", e);
        }
    }
}
