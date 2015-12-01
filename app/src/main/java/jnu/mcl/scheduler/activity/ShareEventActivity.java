package jnu.mcl.scheduler.activity;

import android.content.Intent;
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
import jnu.mcl.scheduler.listener.EventServiceListener;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.service.EventService;

public class ShareEventActivity extends AppCompatActivity implements EventServiceListener {

    private EventService eventService = EventService.getInstance();

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
                Intent intent = new Intent(ShareEventActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        eventService.addEventServiceListener(this);

        eventListView = (ListView) findViewById(R.id.eventListView);
        eventListAdapter = new EventListAdapter(ShareEventActivity.this);
        eventListView.setAdapter(eventListAdapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShareEventActivity.this, EventInformationActivity.class);
                intent.putExtra("eventNo", eventListAdapter.getItem(position).getEvent_no());
                startActivity(intent);
            }
        });
        eventList = eventService.getEventList();
        eventListAdapter.changeList(eventList);
    }

    @Override
    public void onEventCreate() {
        eventListAdapter.changeList(eventService.getEventList());
    }

    @Override
    public void onEventDelete() {

    }

    @Override
    public void onEventUpdate() {

    }
}
