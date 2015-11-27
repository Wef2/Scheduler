package jnu.mcl.scheduler.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.connector.DBConnector;

public class LoginActivity extends AppCompatActivity {

    private DBConnector dbConnector = DBConnector.getInstance();

    private EditText idEditText, nicknameEditText;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idEditText = (EditText)findViewById(R.id.idEditText);
        nicknameEditText = (EditText)findViewById(R.id.nicknameEditText);

        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signupButton);

    }


    public void getUserFromDB() {
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_user where id="+idEditText.getText());
            while (resultSet.next()) {
            }
            conn.close();
        } catch (Exception e) {
            Toast.makeText(this, "ID, Nickname을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            Log.w("Error connection", e);
        }
    }

    public void addUser(){

    }

}
