package jnu.mcl.scheduler.activity;

import android.database.DataSetObserver;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.model.FriendModel;

public class FriendActivity extends AppCompatActivity {

    DBConnector dbConnector = DBConnector.getInstance();
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);

    private ArrayList<FriendModel> friendModelArrayList = new ArrayList<FriendModel>();
    private ArrayAdapter<FriendModel> friendModelArrayAdapter;
    private ListView friendListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        friendListView = (ListView)findViewById(R.id.friendListView);

        getFriendListFromDB();
    }


    public void getFriendListFromDB() {
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_profile");
            while (resultSet.next()) {
                FriendModel friendModel = new FriendModel();
                friendModel.setNo(resultSet.getInt(1));
                friendModel.setNickname(resultSet.getString(2));
                friendModel.setDescription(resultSet.getString(4));

                friendModelArrayList.add(friendModel);
            }
            friendModelArrayAdapter = new ArrayAdapter<FriendModel>(this, android.R.layout.simple_expandable_list_item_1, friendModelArrayList);
            friendListView.setAdapter(friendModelArrayAdapter);
            conn.close();
        } catch (Exception e) {
            Toast.makeText(this, "DB Connection Error", Toast.LENGTH_SHORT).show();
            Log.w("Error connection", e);
        }
    }

}