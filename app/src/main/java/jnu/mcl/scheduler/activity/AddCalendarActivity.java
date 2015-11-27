package jnu.mcl.scheduler.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.connector.DBConnector;

public class AddCalendarActivity extends AppCompatActivity {

    DBConnector dbConnector = DBConnector.getInstance();

    private EditText calendarNameEditText, calendarDescriptionEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;
    private Button confirmButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarNameEditText = (EditText)findViewById(R.id.calendarNameEditText);
        calendarDescriptionEditText = (EditText)findViewById(R.id.calendarDescriptionEditText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        confirmButton = (Button)findViewById(R.id.confirmButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void addCalendarToDB() {
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("insert into t_profile");
            conn.close();
        } catch (Exception e) {
            Toast.makeText(this, "DB Connection Error", Toast.LENGTH_SHORT).show();
            Log.w("Error connection", e);
        }
    }


}
