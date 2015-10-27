package com.kaleido.cesmarttracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */

public class AnnounceFragment extends Fragment {
    Test test = new Test();
    ArrayList<String> courses = new ArrayList<>();
    int year_x,month_x,day_x;
    CustomListAdapter adapter;
    String courseSelected;
    ListView list;
    String[] itemname ={
            "Course",
            "Date",
            "Global",
    };

    Integer[] imgid ={
            R.drawable.ic_course,
            R.drawable.ic_schedule,
            R.drawable.ic_inbox,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        View view=inflater.inflate(R.layout.activity_announce,container,false);
        adapter = new CustomListAdapter(getActivity(), itemname, imgid, year_x,month_x,day_x,courseSelected);
        list=(ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        //final CharSequence[] course = {" Digital ", " Advance Digital ", " Computer Organization ", " Computer Architecture "};
                        for(int i=0;i<test.getTeachers().get(0).getCourses().size();i++){
                            courses.add(test.getTeachers().get(0).getCourses().get(i).getName());
                        }
                        CharSequence[] charSequences=courses.toArray(new CharSequence[courses.size()]);
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(getActivity());
                        builder.setTitle("Select Course");
                        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("Cancel", null);
                        builder.create();
                        builder.show();
                        break;
                    case 1:
                        DialogFragment newFragment = new SelectDateFragment();
                        newFragment.show(getFragmentManager(), "DatePicker");
                        break;
                    case 2:
                        String Selecteditem = itemname[+position];
                        Toast.makeText(getActivity().getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return  view;
    }

}
