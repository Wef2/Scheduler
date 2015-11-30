package jnu.mcl.scheduler.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.EventListAdapter;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.EventModel;

public class EventActivity extends AppCompatActivity implements QueryListener {

    public Uri uri = CalendarContract.Events.CONTENT_URI;
    public String[] projection = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };


    private QueryHandler queryHandler;
    private ArrayList<EventModel> eventList = new ArrayList<EventModel>();
    private EventListAdapter eventListAdapter;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        eventListView = (ListView) findViewById(R.id.eventListView);
        eventListAdapter = new EventListAdapter(EventActivity.this, eventList);
        eventListView.setAdapter(eventListAdapter);
        queryHandler = new QueryHandler(EventActivity.this, this);
        queryHandler.startQuery(1, null, uri, projection, null, null, null);

    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        eventList.clear();
        while (cursor.moveToNext()) {
            EventModel eventModel = new EventModel();
            eventModel.setTitle(cursor.getString(0));
            eventModel.setDtstart(cursor.getString(1));
            eventModel.setDtend(cursor.getString(2));
            eventList.add(eventModel);
        }
        eventListAdapter.notifyDataSetChanged();
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
