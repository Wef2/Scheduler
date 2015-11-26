package jnu.mcl.scheduler.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.activity.FriendActivity;
import jnu.mcl.scheduler.activity.ScheduleActivity;
import jnu.mcl.scheduler.activity.SettingsActivity;

/**
 * Created by 김 on 2015-11-26.
 */
public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;

    public NavigationItemSelectedListener(Activity activity) {
        this.activity = activity;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class<?> gotClass = this.activity.getClass();
        Log.w("Got Class",gotClass.toString());

        if (id == R.id.nav_schedule) {
            if (gotClass == ScheduleActivity.class) {

            } else {
                Intent intent = new Intent(this.activity, ScheduleActivity.class);
                this.activity.startActivity(intent);
                Log.w("Intent", "To Schedule Activity");
            }
        } else if (id == R.id.nav_friend) {
            if (gotClass == FriendActivity.class) {

            } else {
                Intent intent = new Intent(this.activity, FriendActivity.class);
                this.activity.startActivity(intent);
                Log.w("Intent", "To Friend Activity");
            }
        } else if (id == R.id.nav_settings) {
            if (gotClass == SettingsActivity.class) {

            } else {
                Intent intent = new Intent(this.activity, SettingsActivity.class);
                this.activity.startActivity(intent);
                Log.w("Intent", "To Settings Activity");
            }
        }

        DrawerLayout drawer = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
