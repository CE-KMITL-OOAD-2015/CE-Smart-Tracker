package com.kaleido.cesmarttracker.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.adapter.TakenCourseListAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;


/**
 * Created by monkiyes on 10/30/2015 AD.
 */
public class Progress3 extends Fragment {
    Test t = new Test();
    Student s = t.getStudents().get(0);
    RecyclerView recyclerView1;
    ArrayList<String> semesters;
    LinearLayoutManager mLayoutManager;
    TakenCourseListAdapter takenCourseListAdapter;
    View view;
    UserLocalStore userLocalStore;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.progress3,container,false);
        userLocalStore = new UserLocalStore(getContext());
        s = userLocalStore.getStudentData();

        TextView totalCourse = (TextView)view.findViewById(R.id.total_course);
        totalCourse.setText("You have taken "+s.getTranscript().getAllTakenCourses().size()+" course in total.");

        recyclerView1 = (RecyclerView) view.findViewById(R.id.taken_list_course);
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(mLayoutManager);

        semesters = s.getTranscript().getSemesters();

        ArrayList<Course> takenCourses = s.getTranscript().getAllTakenCourses();
        takenCourseListAdapter = new TakenCourseListAdapter(takenCourses,semesters,getContext());
        recyclerView1.setAdapter(takenCourseListAdapter);

        setColorsSpot();

        return  view;
    }

    public void setColorsSpot(){
        ImageView major_spot = (ImageView)view.findViewById(R.id.major);
        ImageView language_spot = (ImageView)view.findViewById(R.id.language);
        ImageView human_spot = (ImageView)view.findViewById(R.id.human);
        ImageView selective_spot = (ImageView)view.findViewById(R.id.selective);
        ImageView social_spot = (ImageView)view.findViewById(R.id.social);
        ImageView science_spot = (ImageView)view.findViewById(R.id.science);

        major_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable = (GradientDrawable) major_spot.getBackground();
        drawable.setColor(Color.parseColor("#d7c6cf"));

        language_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable1 = (GradientDrawable) language_spot.getBackground();
        drawable1.setColor(Color.parseColor("#8caba8"));

        human_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable2 = (GradientDrawable) human_spot.getBackground();
        drawable2.setColor(Color.parseColor("#6fa6bc"));

        selective_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable3 = (GradientDrawable) selective_spot.getBackground();
        drawable3.setColor(Color.parseColor("#f0d0cb"));

        social_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable4 = (GradientDrawable) social_spot.getBackground();
        drawable4.setColor(Color.parseColor("#c39e45"));

        science_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable5 = (GradientDrawable) science_spot.getBackground();
        drawable5.setColor(Color.parseColor("#d94444"));

    }
}