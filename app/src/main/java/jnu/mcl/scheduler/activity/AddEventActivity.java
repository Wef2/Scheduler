package jnu.mcl.scheduler.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.dialog.EventDateDialog;
import jnu.mcl.scheduler.dialog.EventTimeDialog;
import jnu.mcl.scheduler.service.EventService;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private EventService eventService = EventService.getInstance();

    private Button confirmButton, cancelButton;
    private EditText eventTitleText;

    private TextView dtstartDate, dtstartTime, dtendDate, dtendTime;

    private int calendar_no;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        eventTitleText = (EditText) findViewById(R.id.eventTitleText);

        dtstartDate = (TextView)findViewById(R.id.dtstartDate);
        dtstartTime = (TextView)findViewById(R.id.dtstartTime);
        dtendDate = (TextView)findViewById(R.id.dtendDate);
        dtendTime = (TextView)findViewById(R.id.dtendTime);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dtstartDate.setOnClickListener(this);
        dtstartTime.setOnClickListener(this);
        dtendDate.setOnClickListener(this);
        dtendTime.setOnClickListener(this);
    }

    public String getEventTitle() {
        return eventTitleText.getText().toString();
    }

    public int getEventTitleLength() {
        return eventTitleText.getText().length();
    }

    @Override
    public void onClick(View v) {
        if (v == confirmButton) {
            String toastMessage;
            title = getEventTitle();
            if (getEventTitleLength() > 0) {
                eventService.addEvent(1, title, title);
                toastMessage = title + "이벤트가 생성되었습니다.";
                finish();
            } else {
                toastMessage = "정보를 입력해주세요";
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        } else if (v == cancelButton) {
            finish();
        } else if (v == dtstartDate) {
            EventDateDialog dtstartDateDialog = new EventDateDialog(this);
            dtstartDateDialog.show();
        } else if (v == dtstartTime) {
            EventTimeDialog dtstartTimeDialog = new EventTimeDialog(this);
            dtstartTimeDialog.show();
        } else if (v == dtendDate) {
            EventDateDialog dtendDateDialog = new EventDateDialog(this);
            dtendDateDialog.show();
        } else if (v == dtendTime) {
            EventTimeDialog dtendTimeDialog = new EventTimeDialog(this);
            dtendTimeDialog.show();
        }
    }
}
