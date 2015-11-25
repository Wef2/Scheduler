package jnu.mcl.scheduler.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.connector.DBConnector;

public class TestActivity extends AppCompatActivity {

    DBConnector dbConnector = DBConnector.getInstance();
    TextView textView, textView2;
    Button testButton;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        testButton = (Button) findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Test","Button");
                test();
            }
        });
    }

    public void test() {
        Connection conn = null;
        try {
            conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("select * from t_board");
            while (reset.next()) {
                Log.w("Test",reset.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
    }

}
