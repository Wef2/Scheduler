package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.model.FriendModel;

/**
 * Created by Kim on 2015-11-27.
 */
public class CalendarListAdapter extends ArrayAdapter<CalendarModel> {

    public CalendarListAdapter(Context context, ArrayList<CalendarModel> calendarModelList) {
        super(context, 0, calendarModelList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CalendarModel calendarModel = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.calendar_item, parent, false);
        }
        TextView calendarNameText = (TextView) view.findViewById(R.id.calendarNameText);
        TextView calendarDescriptionText = (TextView) view.findViewById(R.id.calendarDescriptionText);
        calendarNameText.setText(calendarModel.getName());
        calendarDescriptionText.setText(calendarModel.getDescription());
        return view;
    }


}

