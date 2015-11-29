package jnu.mcl.scheduler.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.util.SharedPreferenceUtil;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final String id = SharedPreferenceUtil.getSharedPreference(SplashActivity.this, "id");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (id == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, ProfileActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}
