package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.service.EventService;

public class EventInformationActivity extends AppCompatActivity implements EventServiceListener {

    private EventService eventService = EventService.getInstance();
    private int event_no;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        calendarNameText = (TextView) findViewById(R.id.calendarNameText);
        eventTitleText = (TextView) findViewById(R.id.eventTitleText);
        dtstartText = (TextView) findViewById(R.id.dtstartText);
        dtendText = (TextView) findViewById(R.id.dtendText);

        Intent intent = getIntent();
        event_no = intent.getIntExtra("eventNo", 0);
        eventModel = eventService.getEvent(event_no);

        calendarNameText.setText(eventModel.getCalendarId());
        eventTitleText.setText(eventModel.getTitle());
        dtstartText.setText(eventModel.getDtstart());
        dtendText.setText(eventModel.getDtend());

        eventService.addEventServiceListener(this);
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
}
