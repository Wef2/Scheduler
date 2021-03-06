package jnu.mcl.scheduler.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.handler.QueryHandler;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.listener.QueryListener;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.model.QueryModel;
import jnu.mcl.scheduler.service.CalendarService;
import jnu.mcl.scheduler.service.EventService;
import jnu.mcl.scheduler.util.DateFormatUtil;

public class EventInformationActivity extends AppCompatActivity implements EventServiceListener, QueryListener {

    private QueryModel queryModel = QueryModel.getInstance();
    private QueryHandler queryHandler;

    private EventService eventService = EventService.getInstance();
    private CalendarService calendarService = CalendarService.getInstance();

    private int calendar_no;
    private int event_no;
    private String event_id;

    private CalendarModel calendarModel;
    private String calendar_type;
    private String calendar_name;
    private EventModel eventModel;

    private TextView calendarNameText, eventTitleText, dtstartText, dtendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        calendar_type = intent.getStringExtra("type");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendar_type.equals("personal")) {
                    Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Integer.parseInt(eventModel.getId()));
                    Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (calendar_type.equals("share")) {
                    Intent intent = new Intent(EventInformationActivity.this, ModifyEventActivity.class);
                    intent.putExtra("type", "share");
                    intent.putExtra("calendarNo", calendar_no);
                    intent.putExtra("eventNo", eventModel.getEvent_no());
                    startActivity(intent);
                }
                finish();
            }
        });

        calendarNameText = (TextView) findViewById(R.id.calendarNameText);
        eventTitleText = (TextView) findViewById(R.id.eventTitleText);
        dtstartText = (TextView) findViewById(R.id.dtstartText);
        dtendText = (TextView) findViewById(R.id.dtendText);

        if (calendar_type.equals("personal")) {
            event_id = intent.getStringExtra("eventId");
            queryHandler = new QueryHandler(EventInformationActivity.this, this);
            queryHandler.startQuery(1, null, queryModel.getEventUri(), queryModel.getEventProjection(), null, null, null);

        } else if (calendar_type.equals("share")) {
            calendar_no = intent.getIntExtra("calendarNo", calendar_no);
            calendarModel = calendarService.getCalendar(calendar_no);
            calendar_name = calendarModel.getName();
            calendarNameText.setText(calendar_name);
            event_no = intent.getIntExtra("eventNo", 0);
            eventModel = eventService.getEvent(event_no);
            eventService.addEventServiceListener(this);
            setTexts();
        }
    }

    public void setTexts() {
        String dtstart = DateFormatUtil.utcToLocal(eventModel.getDtstart());
        String dtend = DateFormatUtil.utcToLocal(eventModel.getDtend());
        eventTitleText.setText(eventModel.getTitle());
        dtstartText.setText(dtstart);
        dtendText.setText(dtend);
    }

    @Override
    public void onEventCreate() {

    }

    @Override
    public void onEventDelete() {

    }

    @Override
    public void onEventUpdate() {

    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {

        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(event_id)) {
                eventModel = new EventModel();
                eventModel.setId(cursor.getString(0));
                eventModel.setTitle(cursor.getString(1));
                eventModel.setDtstart(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    eventModel.setDtend(cursor.getString(3));
                } else {
                    eventModel.setDtend("");
                }
                setTexts();
            }
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
