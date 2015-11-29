package jnu.mcl.scheduler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jnu.mcl.scheduler.R;

/**
 * Created by 김 on 2015-11-29.
 */
public class PersonalCalendarFragment extends Fragment {

    public PersonalCalendarFragment(){

    }

    public static PersonalCalendarFragment newInstance() {
        PersonalCalendarFragment fragment = new PersonalCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("PersonalCalendarFragment");
        return rootView;
    }
}
