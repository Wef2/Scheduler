package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.EventListAdapter;
import jnu.mcl.scheduler.dialog.EventLongClickDialog;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.model.QueryModel;
import jnu.mcl.scheduler.service.EventService;

public class EventActivity extends AppCompatActivity implements EventServiceListener, QueryListener {

    private EventService eventService = EventService.getInstance();
    private QueryModel queryModel = QueryModel.getInstance();
    private QueryHandler queryHandler;

    private ArrayList<EventModel> eventList = new ArrayList<EventModel>();
    private EventListAdapter eventListAdapter;
    private ListView eventListView;

    private String calendarType;

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
                Intent intent = new Intent(EventActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        eventService.addEventServiceListener(this);

        Intent intent = getIntent();
        calendarType = intent.getStringExtra("type");
        eventListView = (ListView) findViewById(R.id.eventListView);
        eventListAdapter = new EventListAdapter(EventActivity.this);
        eventListView.setAdapter(eventListAdapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventActivity.this, EventInformationActivity.class);
                intent.putExtra("eventId", eventListAdapter.getItem(position).getId());
                intent.putExtra("eventNo", eventListAdapter.getItem(position).getEvent_no());
                startActivity(intent);
            }
        });
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EventLongClickDialog eventLongClickDialog = new EventLongClickDialog(EventActivity.this);
                eventLongClickDialog.show();
                return true;
            }
        });

        if (calendarType.equals("personal")) {
            queryHandler = new QueryHandler(EventActivity.this, this);
            queryHandler.startQuery(1, null, queryModel.getEventUri(), queryModel.getEventProjection(), null, null, null);
        } else if (calendarType.equals("share")) {
            eventList = eventService.getEventList();
            eventListAdapter.changeList(eventList);
        }

    }

    @Override
    public void onEventCreate() {
        if (calendarType.equals("share")) {
            eventListAdapter.changeList(eventService.getEventList());
        }
    }

    @Override
    public void onEventDelete() {

    }

    @Override
    public void onEventUpdate() {

    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (calendarType.equals("personal")) {
            eventList.clear();
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
