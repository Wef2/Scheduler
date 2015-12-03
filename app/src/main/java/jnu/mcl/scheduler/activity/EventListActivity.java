package jnu.mcl.scheduler.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.adapter.EventListAdapter;
import jnu.mcl.scheduler.dialog.CustomLongClickDialog;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.model.QueryModel;
import jnu.mcl.scheduler.service.EventService;

public class EventListActivity extends AppCompatActivity implements EventServiceListener, QueryListener {

    private EventService eventService = EventService.getInstance();
    private QueryModel queryModel = QueryModel.getInstance();
    private QueryHandler queryHandler;

    private ArrayList<EventModel> eventList = new ArrayList<EventModel>();
    private EventListAdapter eventListAdapter;
    private ListView eventListView;

    private CustomLongClickDialog customLongClickDialog;
    private TextView modifyText, deleteText, copyText;

    private EventModel eventModel;

    private String calendarType;
    private String calendar_id;
    private int calendar_no;

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

                if (calendarType.equals("personal")) {
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.CALENDAR_ID, calendar_id);
                    startActivityForResult(intent, 0);
                } else if (calendarType.equals("share")) {
                    Intent intent = new Intent(EventListActivity.this, AddEventActivity.class);
                    intent.putExtra("type", "share");
                    intent.putExtra("calendarNo", calendar_no);
                    startActivity(intent);
                }
            }
        });

        eventService.addEventServiceListener(this);

        eventListView = (ListView) findViewById(R.id.eventListView);
        eventListAdapter = new EventListAdapter(EventListActivity.this);
        eventListView.setAdapter(eventListAdapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventListActivity.this, EventInformationActivity.class);
                if (calendarType.equals("personal")) {
                    intent.putExtra("type", "personal");
                    intent.putExtra("calendarId", calendar_id);
                    intent.putExtra("eventId", eventListAdapter.getItem(position).getId());
                } else if (calendarType.equals("share")) {
                    intent.putExtra("type", "share");
                    intent.putExtra("calendarNo", calendar_no);
                    intent.putExtra("eventNo", eventListAdapter.getItem(position).getEvent_no());
                }
                startActivity(intent);
            }
        });
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventModel = eventListAdapter.getItem(position);
                customLongClickDialog.show();
                return true;
            }
        });
        Intent intent = getIntent();
        calendarType = intent.getStringExtra("type");
        if (calendarType.equals("personal")) {
            calendar_id = intent.getStringExtra("calendarId");
            queryHandler = new QueryHandler(EventListActivity.this, this);
            queryHandler.startQuery(1, null, queryModel.getEventUri(), queryModel.getEventProjection(), null, null, null);
        } else if (calendarType.equals("share")) {
            calendar_no = intent.getIntExtra("calendarNo", 1);
            eventList = eventService.getEventList(calendar_no);
            eventListAdapter.changeList(eventList);
        }

        customLongClickDialog = new CustomLongClickDialog(EventListActivity.this);
        modifyText = customLongClickDialog.getModifyText();
        modifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarType.equals("personal")) {
                    Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Integer.parseInt(eventModel.getId()));
                    Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (calendarType.equals("share")) {
                    Intent intent = new Intent(EventListActivity.this, ModifyEventActivity.class);
                    intent.putExtra("type", "share");
                    intent.putExtra("calendarNo", calendar_no);
                    intent.putExtra("eventNo", eventModel.getEvent_no());
                    startActivity(intent);
                }
                customLongClickDialog.dismiss();
                queryHandler.startQuery(1, null, queryModel.getEventUri(), queryModel.getEventProjection(), null, null, null);
            }
        });
        deleteText = customLongClickDialog.getDeleteText();
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarType.equals("personal")) {

                    ContentResolver cr = getContentResolver();
                    ContentValues values = new ContentValues();
                    Uri deleteUri = null;
                    deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Integer.parseInt(eventModel.getId()));
                    int rows = getContentResolver().delete(deleteUri, null, null);
                    Log.w("rows", Integer.toString(rows));

                } else if (calendarType.equals("share")) {
                    eventService.deleteEvent(eventModel.getEvent_no());

                }
                Toast.makeText(EventListActivity.this, "이벤트를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                customLongClickDialog.dismiss();
            }
        });
        copyText = customLongClickDialog.getCopyText();
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarType.equals("personal")) {

                } else if (calendarType.equals("share")) {
                }
            }
        });


    }

    @Override
    public void onEventCreate() {
        eventListAdapter.changeList(eventService.getEventList());
    }

    @Override
    public void onEventDelete() {
        eventListAdapter.changeList(eventService.getEventList());
    }

    @Override
    public void onEventUpdate() {
        eventListAdapter.changeList(eventService.getEventList());
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        eventList.clear();
        while (cursor.moveToNext()) {
            if (cursor.getString(4).equals(calendar_id)) {
                EventModel eventModel = new EventModel();
                eventModel.setId(cursor.getString(0));
                eventModel.setTitle(cursor.getString(1));
                eventModel.setDtstart(cursor.getString(2));
                eventModel.setDtend(cursor.getString(3));
                eventModel.setCalendarId(cursor.getString(4));
                eventList.add(eventModel);
            }
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
}
