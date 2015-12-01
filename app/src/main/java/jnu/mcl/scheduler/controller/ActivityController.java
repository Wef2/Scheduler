package jnu.mcl.scheduler.controller;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Kim on 2015-12-01.
 */
public class ActivityController {

    private static ActivityController newInstance;

    public static ActivityController getInstance(){
        if(newInstance == null){
            newInstance = new ActivityController();
        }
        return newInstance;
    }

    ArrayList<Activity> activities = new ArrayList<Activity>();

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void finishAllActivities(){
        for(Activity activity : activities){
           activity.finish();
        }
    }
}