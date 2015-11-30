package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.CalendarModel;

/**
 * Created by Kim on 2015-11-27.
 */
public class CalendarListAdapter extends BaseAdapter {

    private ArrayList<CalendarModel> calendarModels;
    private LayoutInflater inflater;

    public CalendarListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.calendarModels = new ArrayList<>();
    }

    public void changeList(Collection<CalendarModel> newCalendarModels) {
        this.calendarModels.clear();
        this.calendarModels.addAll(newCalendarModels);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return calendarModels.size();
    }

    @Override
    public CalendarModel getItem(int position) {
        return calendarModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(CalendarModel calendarModel, View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.nameTextView.setText(calendarModel.getName());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.calendar_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView nameTextView;

        ViewHolder(View view) {
            nameTextView = (TextView) view.findViewById(R.id.calendarNameText);
        }
    }
}

