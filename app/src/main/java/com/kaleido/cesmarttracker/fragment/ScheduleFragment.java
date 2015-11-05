package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */
public class ScheduleFragment extends Fragment {

    private ListView notify;
    private ArrayAdapter<String> adapter;
    CalendarView calendar;
    Test test = new Test();
    ArrayList<String> event = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment,container,false);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, event);
        notify = (ListView) view.findViewById(R.id.list);
        calendar = (CalendarView) view.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Toast.makeText(getActivity().getApplicationContext(), dayOfMonth + "/" + ++month + "/" + year, Toast.LENGTH_SHORT).show();
                String date = dayOfMonth + "/" + month + "/" + year;
                event.clear();
                for (int i = 0; i < test.getStudents().get(0).getEvents().size(); i++) {
                    if (date.contentEquals(test.getStudents().get(0).getEvents().get(i).getDueDate()))
                        event.add(test.getStudents().get(0).getEvents().get(i).getTitle());
                }
                notify.setAdapter(adapter);
            }
        });
        return  view;
    }
}
