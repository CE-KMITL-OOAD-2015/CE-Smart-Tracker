package com.kaleido.cesmarttracker.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.adapter.AssignmentAdapter;
import com.kaleido.cesmarttracker.adapter.CustomListAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */

public class AnnounceFragment extends Fragment {
    Test test = new Test();
    ArrayList<String> courses = new ArrayList<>();
    // private static final String[] EMPTY_STRING_ARRAY = new String[0];
    int year_x,month_x,day_x;
    CustomListAdapter adapter;
    String courseSelected;
    ListView list;
    ListView asslist;
    AssignmentAdapter assadapter;
    int i = 1;
    int assignmentScore;
    String[] ass = {"Assignment Max Score"};
    String[] assScore = {""};
    CharSequence[] global = {"Announcement","Assignment","Result"};
    String[] itemname ={
            "Date",
            "Course",
            "Global",
    };
    String subitemname[] = {"","",""};


    Integer[] imgid ={
            R.drawable.ic_schedule,
            R.drawable.ic_course,
            R.drawable.ic_inbox,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        final View view = inflater.inflate(R.layout.activity_announce,container,false);
        adapter = new CustomListAdapter(getActivity(), itemname, imgid, year_x,month_x,day_x,courseSelected,subitemname);
        list=(ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
        asslist = (ListView)view.findViewById(R.id.assignmentList);
        assadapter = new AssignmentAdapter(getActivity(),ass,imgid,assScore);
        asslist.setAdapter(assadapter);
        asslist.setVisibility(View.INVISIBLE);
        for(int i=0;i<test.getTeachers().get(0).getCourses().size();i++){
            courses.add(test.getTeachers().get(0).getCourses().get(i).getName());
        }
        Button button=(Button)view.findViewById(R.id.SubmitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = (TextView) getActivity().findViewById(R.id.hiddenText);
                String abcd = txt.getText().toString();
                subitemname[0] = abcd;
                //TODO announce
                if(subitemname[3].contentEquals("Assignmemt")){

                }
                else if(subitemname[3].contentEquals("Result")){

                }
                else if(subitemname[3].contentEquals("Announcement")){

                }
            }
        });
        //final EditText score = (EditText) view.findViewById(R.id.assignmentMaxScore);
        //final TextView score2 = (TextView) view.findViewById(R.id.assignmentmax);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                switch (position) {
                    case 0:
                        //LinearLayout l1 = (LinearLayout)view.findViewById(R.id.linear);
                        //l1.addView(view);
                        //score2.setVisibility(View.INVISIBLE);
                        SelectDateFragment newFragment = new SelectDateFragment();
                        newFragment.show(getFragmentManager(), "DatePicker");
                        TextView txt = (TextView) getActivity().findViewById(R.id.hiddenText);
                        String abcd = txt.getText().toString();
                        subitemname[0] = abcd;
                        list.setAdapter(adapter);
                        break;

                    case 1:
                        //final CharSequence[] course = {" Digital ", " Advance Digital ", " Computer Organization ", " Computer Architecture "};
                        TextView txt2 = (TextView) getActivity().findViewById(R.id.hiddenText);
                        String abcd2 = txt2.getText().toString();
                        subitemname[0] = abcd2;
                        final CharSequence[] charSequences = courses.toArray(new CharSequence[courses.size()]);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Select Course");
                        //score2.setVisibility(View.INVISIBLE);
                        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                courseSelected = charSequences[which].toString();
                                subitemname[1] = courseSelected;
                                list.setAdapter(adapter);
                            }
                        });
                        builder.setNegativeButton("Cancel", null);
                        builder.create();
                        builder.show();
                        break;

                    case 2:
                        TextView txt3 = (TextView) getActivity().findViewById(R.id.hiddenText);
                        String abcd3 = txt3.getText().toString();
                        subitemname[0] = abcd3;
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setTitle("Select Global");
                        //score.setVisibility(View.INVISIBLE);
                        //score2.setVisibility(View.INVISIBLE);
                        builder1.setItems(global, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String Selecteditem = global[which].toString();
                                if (which == 0)
                                {
                                    asslist.setVisibility(View.INVISIBLE);
                                }
                                if (which == 1) {
                                    asslist.setVisibility(View.VISIBLE);

                                }
                                if (which==2)
                                {
                                    asslist.setVisibility(View.INVISIBLE);
                                }
                                subitemname[2] = Selecteditem;
                                //assignmentScore = Integer.parseInt(score2.getText().toString());
                                list.setAdapter(adapter);
                            }
                        });
                        builder1.setNegativeButton("Cancel", null);
                        builder1.create();
                        builder1.show();
                        break;
                }
            }
        });

        return  view;
    }

}

//package com.kaleido.cesmarttracker.fragment;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.kaleido.cesmarttracker.R;
//import com.kaleido.cesmarttracker.Test;
//import com.kaleido.cesmarttracker.adapter.CustomListAdapter;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
///**
// * Created by monkiyes on 10/25/2015 AD.
// */
//
//public class AnnounceFragment extends Fragment {
//    Test test = new Test();
//    ArrayList<String> courses = new ArrayList<>();
//    // private static final String[] EMPTY_STRING_ARRAY = new String[0];
//    int year_x,month_x,day_x;
//    CustomListAdapter adapter;
//    String courseSelected;
//    ListView list;
//    CharSequence[] global = {"Announcement","Assignment","Result"};
//    String[] itemname ={
//            "Date",
//            "Course",
//            "Announcement Type",
//    };
//    String subitemname[] = {"","",""};
//
//
//    Integer[] imgid ={
//            R.drawable.ic_schedule,
//            R.drawable.ic_current_course,
//            R.drawable.ic_inbox,
//    };
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        final Calendar cal = Calendar.getInstance();
//        year_x = cal.get(Calendar.YEAR);
//        month_x = cal.get(Calendar.MONTH);
//        day_x = cal.get(Calendar.DAY_OF_MONTH);
//        final View view = inflater.inflate(R.layout.activity_announce,container,false);
//        adapter = new CustomListAdapter(getActivity(), itemname, imgid, year_x,month_x,day_x,courseSelected,subitemname);
//        list=(ListView)view.findViewById(R.id.list);
//        list.setAdapter(adapter);
//        for(int i=0;i<test.getTeachers().get(0).getCourses().size();i++){
//            courses.add(test.getTeachers().get(0).getCourses().get(i).getName());
//        }
//        final EditText score = (EditText) view.findViewById(R.id.assignmentMaxScore);
//        final TextView score2 = (TextView) view.findViewById(R.id.assignmentmax);
//        score.setVisibility(View.INVISIBLE);
//        score2.setVisibility(View.INVISIBLE);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        SelectDateFragment newFragment = new SelectDateFragment();
//                        newFragment.show(getFragmentManager(), "DatePicker");
//                        TextView txt = (TextView) view.findViewById(R.id.subItem);
//                        String abcd = txt.getText().toString();
//                        subitemname[0] = subitemname[0].replaceAll(subitemname[1], abcd);
//                        adapter.notifyDataSetChanged();
//                        break;
//
//                    case 1:
//                        //final CharSequence[] course = {" Digital ", " Advance Digital ", " Computer Organization ", " Computer Architecture "};
//
//                        final CharSequence[] charSequences = courses.toArray(new CharSequence[courses.size()]);
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("Select Course");
//                        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                courseSelected = charSequences[which].toString();
//                                subitemname[1] = subitemname[1].replaceAll(subitemname[1], courseSelected);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", null);
//                        builder.create();
//                        builder.show();
//                        break;
//
//                    case 2:
//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
//                        builder1.setTitle("Select Global");
//                        score.setVisibility(View.INVISIBLE);
//                        score2.setVisibility(View.INVISIBLE);
//                        builder1.setItems(global, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                String Selecteditem = global[which].toString();
//                                if (which == 1) {
//                                    score.setVisibility(View.INVISIBLE);
//                                    score2.setVisibility(View.INVISIBLE);
//                                    score.setVisibility(View.VISIBLE);
//                                    score2.setVisibility(View.VISIBLE);
//                                }
//                                subitemname[2] = subitemname[2].replaceAll(subitemname[2], Selecteditem);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                        builder1.setNegativeButton("Cancel", null);
//                        builder1.create();
//                        builder1.show();
//                        break;
//                }
//            }
//        });
//
//        return  view;
//    }
//
//}

