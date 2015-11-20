package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.adapter.TeacherListAdapter;
import com.kaleido.cesmarttracker.data.Course;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/31/2015 AD.
 */
public class Progress4 extends Fragment {

    Course c;

    TeacherListAdapter adapter;

    public Progress4(Course c){
        this.c = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress4, container, false);
        TextView subName = (TextView) view.findViewById(R.id.cname);
        TextView subId = (TextView) view.findViewById(R.id.cid);
        subName.setText(c.getName());
        subId.setText(c.getId());

        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<String> studentID = new ArrayList<>();
        ArrayList<Double> studentTotalGrade = new ArrayList<>();
//        for (int i = 0; i < c.getAllStudent().size(); i++) { //TODO: getAllStudent()
//            studentNames.add(c.getAllStudent().get(i).getName());
//            studentID.add(c.getAllStudent().get(i).getId());
////            studentTotalGrade.add(c.getAllStudent().get(i).getTotalGradeAssignment(c));
//        }
//        adapter = new TeacherListAdapter(getActivity(),studentNames,studentID,studentTotalGrade);
//        ListView list=(ListView)view.findViewById(R.id.Studentlist);
//        list.setAdapter(adapter);
        return view;
    }
}


//package com.kaleido.cesmarttracker.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.kaleido.cesmarttracker.R;
//import com.kaleido.cesmarttracker.adapter.TeacherListAdapter;
//
///**
// * Created by monkiyes on 10/31/2015 AD.
// */
//public class Progress4 extends Fragment {
//
//    String[] itemname ={
//            "Course",
//            "Date",
//            "Global",
//    };
//
//    Integer[] imgid ={
//            R.drawable.ic_course,
//            R.drawable.ic_schedule,
//            R.drawable.ic_inbox,
//    };
//
//    String[] itemid ={
//            "001",
//            "002",
//            "003"
//    };
//
//    TeacherListAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.progress4, container, false);
//        adapter = new TeacherListAdapter(getActivity(),itemname,imgid,itemid);
//        ListView list=(ListView)view.findViewById(R.id.Studentlist);
//        list.setAdapter(adapter);
//        return view;
//    }
//}
