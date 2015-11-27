package jnu.mcl.scheduler.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;

public class ProfileActivity extends AppCompatActivity {

    DBConnector dbConnector = DBConnector.getInstance();
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);

    TextView nicknameText, descriptionText;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        nicknameText = (TextView) findViewById(R.id.nicknameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        getProfileFromDB();
    }

    public void getProfileFromDB() {
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_profile where no='1'");
            while (resultSet.next()) {
                nicknameText.setText(resultSet.getString(2));
                descriptionText.setText(resultSet.getString(4));
            }
            conn.close();
        } catch (Exception e) {
            Toast.makeText(this, "DB Connection Error", Toast.LENGTH_SHORT).show();
            Log.w("Error connection", e);
        }
    }

}