package jnu.mcl.scheduler.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.dialog.EventDateDialog;
import jnu.mcl.scheduler.dialog.EventTimeDialog;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;
import jnu.mcl.scheduler.service.EventService;
import jnu.mcl.scheduler.util.DateFormatUtil;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private EventService eventService = EventService.getInstance();
    private CalendarService calendarService = CalendarService.getInstance();

    private Button confirmButton, cancelButton;
    private EditText eventTitleText;
    private TextView calendarNameText, dtstartDate, dtstartTime, dtendDate, dtendTime;

    private Date startDate, endDate;
    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;

    private CalendarModel calendarModel;
    private String calendar_type;
    private String calendar_name;
    private int calendar_no;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarNameText = (TextView) findViewById(R.id.calendarNameText);
        Intent intent = getIntent();
        calendar_type = intent.getStringExtra("type");
        if (calendar_type.equals("personal")) {

        } else if (calendar_type.equals("share")) {
            calendar_no = intent.getIntExtra("calendarNo", calendar_no);
            calendarModel = calendarService.getCalendar(calendar_no);
            calendar_name = calendarModel.getName();
            calendarNameText.setText(calendar_name);
        }

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        eventTitleText = (EditText) findViewById(R.id.eventTitleText);

        dtstartDate = (TextView) findViewById(R.id.dtstartDate);
        dtstartTime = (TextView) findViewById(R.id.dtstartTime);
        dtendDate = (TextView) findViewById(R.id.dtendDate);
        dtendTime = (TextView) findViewById(R.id.dtendTime);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dtstartDate.setOnClickListener(this);
        dtstartTime.setOnClickListener(this);
        dtendDate.setOnClickListener(this);
        dtendTime.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        startDate = currentTime;
        startYear = currentTime.getYear();
        startMonth = currentTime.getMonth();
        startDay = currentTime.getDay();
        startHour = currentTime.getHours();
        startMinute = currentTime.getMinutes();

        setStartTexts();
    }

    public void setStartTexts() {
        dtstartDate.setText(startYear + "/" + startMonth + "/" + startDay);
        dtstartTime.setText(startHour + ":" + startMinute);
    }

    public void setEndTexts() {
        dtendDate.setText(endYear + "/" + endMonth + "/" + endDay);
        dtendTime.setText(endHour + ":" + endMinute);
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
                String dtstart;
                String dtend;
                dtstart = DateFormatUtil.toUTC(startYear, startMonth, startDay, startHour, startMinute);
                dtend = DateFormatUtil.toUTC(endYear, endMonth, endDay, endHour, endMinute);
                eventService.addEvent(calendar_no, title, dtstart, dtend);
                toastMessage = title + "이벤트가 생성되었습니다.";
                finish();
            } else {
                toastMessage = "정보를 입력해주세요";
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        } else if (v == cancelButton) {
            finish();
        } else if (v == dtstartDate) {
            final EventDateDialog dtstartDateDialog = new EventDateDialog(this);
            dtstartDateDialog.show();
            dtstartDateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    DatePicker datePicker = dtstartDateDialog.getDatePicker();
                    startYear = datePicker.getYear();
                    startMonth = datePicker.getMonth() + 1;
                    startDay = datePicker.getDayOfMonth();
                    setStartTexts();
                }
            });
        } else if (v == dtstartTime) {
            final EventTimeDialog dtstartTimeDialog = new EventTimeDialog(this);
            dtstartTimeDialog.show();
            dtstartTimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    TimePicker timePicker = dtstartTimeDialog.getTimePicker();
                    startHour = timePicker.getCurrentHour();
                    startMinute = timePicker.getCurrentMinute();
                    setStartTexts();
                }
            });
        } else if (v == dtendDate) {
            final EventDateDialog dtendDateDialog = new EventDateDialog(this);
            dtendDateDialog.show();
            dtendDateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    DatePicker datePicker = dtendDateDialog.getDatePicker();
                    endYear = datePicker.getYear();
                    endMonth = datePicker.getMonth() + 1;
                    endDay = datePicker.getDayOfMonth();
                    setEndTexts();
                }
            });
        } else if (v == dtendTime) {
            final EventTimeDialog dtendTimeDialog = new EventTimeDialog(this);
            dtendTimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    TimePicker timePicker = dtendTimeDialog.getTimePicker();
                    endHour = timePicker.getCurrentHour();
                    endMinute = timePicker.getCurrentMinute();
                    setEndTexts();
                }
            });
            dtendTimeDialog.show();
        }
    }
}
