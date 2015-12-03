package jnu.mcl.scheduler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.activity.EventListActivity;
import jnu.mcl.scheduler.adapter.CalendarListAdapter;
import jnu.mcl.scheduler.dialog.CustomLongClickDialog;
import jnu.mcl.scheduler.dialog.ModifyCalendarDialog;
import jnu.mcl.scheduler.listener.CalendarServiceListener;
import jnu.mcl.scheduler.model.CalendarModel;
import jnu.mcl.scheduler.service.CalendarService;

/**
 * Created by 김 on 2015-11-29.
 */
public class ShareCalendarFragment extends Fragment {

    private CalendarService calendarService = CalendarService.getInstance();
    private CalendarServiceListener calendarServiceListener;

    private ArrayList<CalendarModel> shareCalendarList;
    private CalendarListAdapter shareCalendarListAdapter;
    private ListView shareCalendarListView;

    private CalendarModel calendarModel;

    private CustomLongClickDialog customLongClickDialog;
    private TextView modifyText, deleteText;

    public ShareCalendarFragment() {
    }

    public static ShareCalendarFragment newInstance() {
        ShareCalendarFragment fragment = new ShareCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        shareCalendarList = calendarService.getCalendarList();
        shareCalendarListView = (ListView) rootView.findViewById(R.id.calendarListView);
        shareCalendarListAdapter = new CalendarListAdapter(getContext());
        shareCalendarListAdapter.changeList(calendarService.getCalendarList());
        shareCalendarListView.setAdapter(shareCalendarListAdapter);
        shareCalendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calendarModel = shareCalendarListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventListActivity.class);
                intent.putExtra("type", "share");
                intent.putExtra("calendarNo", calendarModel.getCalendar_no());
                startActivity(intent);
            }
        });

        shareCalendarListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                calendarModel = shareCalendarListAdapter.getItem(position);
                customLongClickDialog.show();
                return true;
            }
        });

        customLongClickDialog = new CustomLongClickDialog(getContext());
        modifyText = customLongClickDialog.getModifyText();
        deleteText = customLongClickDialog.getDeleteText();

        modifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customLongClickDialog.dismiss();
                ModifyCalendarDialog modifyCalendarDialog = new ModifyCalendarDialog(getContext(), calendarModel.getCalendar_no());
                modifyCalendarDialog.show();
            }
        });

        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarService.deleteCalendar(calendarModel.getCalendar_no());
                Toast.makeText(getContext(),"캘린더를 삭제하였습니다",Toast.LENGTH_SHORT);
                customLongClickDialog.dismiss();
            }
        });

        calendarServiceListener = new CalendarServiceListener() {
            @Override
            public void onCalendarCreate() {
                shareCalendarListAdapter.changeList(calendarService.getCalendarList());
            }

            @Override
            public void onCalendarDelete() {
                shareCalendarListAdapter.changeList(calendarService.getCalendarList());
            }

            @Override
            public void onCalendarUpdate() {
                shareCalendarListAdapter.changeList(calendarService.getCalendarList());
            }
        };
        calendarService.addCalendarServiceListener(calendarServiceListener);

        return rootView;
    }

}