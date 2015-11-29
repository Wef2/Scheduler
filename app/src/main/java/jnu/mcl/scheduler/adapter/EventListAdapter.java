package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.EventModel;

/**
 * Created by 김 on 2015-11-29.
 */
public class EventListAdapter extends ArrayAdapter<EventModel> {

    public EventListAdapter(Context context, ArrayList<EventModel> eventModelList) {
        super(context, 0, eventModelList);
        setNotifyOnChange(true);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        EventModel eventModel = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        return view;
    }
}
