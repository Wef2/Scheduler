package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.EventModel;
import jnu.mcl.scheduler.util.DateFormatUtil;

/**
 * Created by 김 on 2015-11-29.
 */
public class EventListAdapter extends BaseAdapter {

    private ArrayList<EventModel> eventModels;
    private LayoutInflater inflater;

    public EventListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.eventModels = new ArrayList<>();
    }

    public void changeList(Collection<EventModel> newEventModels) {
        this.eventModels.clear();
        this.eventModels.addAll(newEventModels);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventModels.size();
    }

    @Override
    public EventModel getItem(int position) {
        return eventModels.get(position);
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

    private void bind(EventModel eventModel, View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String dtstart = "시작 : "+DateFormatUtil.utcToLocal(eventModel.getDtstart());
        String dtend = "";
        if(eventModel.getDtend()!=null){
            dtend = "종료 : "+DateFormatUtil.utcToLocal(eventModel.getDtend());
        }

        viewHolder.eventTitleText.setText(eventModel.getTitle());
        viewHolder.eventStartText.setText(dtstart);
        viewHolder.eventEndText.setText(dtend);
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.event_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView eventTitleText;
        final TextView eventStartText;
        final TextView eventEndText;

        ViewHolder(View view) {
            eventTitleText = (TextView) view.findViewById(R.id.eventTitleText);
            eventStartText = (TextView) view.findViewById(R.id.eventStartText);
            eventEndText = (TextView) view.findViewById(R.id.eventEndText);
        }
    }

}
