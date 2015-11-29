package jnu.mcl.scheduler.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.dialog.ModifyProfileDialog;
import jnu.mcl.scheduler.listener.NavigationItemSelectedListener;
import jnu.mcl.scheduler.listener.UserServiceListener;
import jnu.mcl.scheduler.model.UserModel;
import jnu.mcl.scheduler.service.UserService;
import jnu.mcl.scheduler.util.SharedPreferenceUtil;

public class ProfileActivity extends AppCompatActivity {

    ModifyProfileDialog modifyProfileDialog;
    private TextView nicknameText, descriptionText;
    private ImageView profileImage;
    private Button modifyButton;
    private UserService userService = UserService.getInstance();
    private UserModel myProfile = null;
    private String id;
    private NavigationItemSelectedListener navigationItemSelectedListener = new NavigationItemSelectedListener(this);
    private UserServiceListener userServiceListener;

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
        modifyButton = (Button) findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyProfileDialog = new ModifyProfileDialog(ProfileActivity.this);
                modifyProfileDialog.show();
            }
        });

        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        id = SharedPreferenceUtil.getSharedPreference(this, "id");

        userServiceListener = new UserServiceListener() {
            @Override
            public void onUserUpdate() {
                profileDataChanged();
            }
        };
        userService.addUserServiceListener(userServiceListener);

        profileDataChanged();
    }

    public void profileDataChanged() {
        myProfile = userService.getUser(id);
        nicknameText.setText(myProfile.getNickname());
        descriptionText.setText(myProfile.getDescription());
    }
}