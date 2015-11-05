package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.adapter.TeacherListAdapter;

/**
 * Created by monkiyes on 10/31/2015 AD.
 */
public class Progress4 extends Fragment {

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

    String[] itemid ={
            "001",
            "002",
            "003"
    };

    TeacherListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress4, container, false);
        adapter = new TeacherListAdapter(getActivity(),itemname,imgid,itemid);
        ListView list=(ListView)view.findViewById(R.id.Studentlist);
        list.setAdapter(adapter);
        return view;
    }
}
