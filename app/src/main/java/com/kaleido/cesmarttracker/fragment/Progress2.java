package com.kaleido.cesmarttracker.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.adapter.SemesterCourseListAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */
public class Progress2 extends Fragment {
    Test test = new Test();
    Student s1 = test.getStudents().get(0);
    private ArrayAdapter<String> adapter;
    ArrayList<String> semesters = new ArrayList<>();
    ArrayList<String> courseNames = new ArrayList<>();
    SemesterCourseListAdapter semesterCourseListAdapter;
    GridLayoutManager mLayoutManager;
    RecyclerView recyclerView1;
    View view;
    UserLocalStore userLocalStore;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.progress2,container,false);
        userLocalStore = new UserLocalStore(getContext());
        s1 = userLocalStore.getStudentData();
        semesters = s1.getTranscript().getSemesters();

        TextView currentGPA = (TextView)view.findViewById(R.id.current_gpa);
        currentGPA.setText("Your current GPA is "+s1.getTranscript().getGPA());
        //Create Line Chart
        LineChart chart = (LineChart)view.findViewById(R.id.chart1);
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(getResources().getColor(R.color.lightGray));
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(getResources().getColor(R.color.lightGray));
        leftAxis.setDrawLabels(false);
        rightAxis.setDrawLabels(false);
        chart.setPinchZoom(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<String> semesters_chart = new ArrayList<>(s1.getTranscript().getAllSemesters());
        LineData data = new LineData(semesters_chart);
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < s1.getTranscript().getAllSemesters().size();i++) {
            entries.add(new Entry((float) s1.getTranscript().getGPS(s1.getTranscript().getAllSemesters().get(i)), i));
        }
        LineDataSet set = new LineDataSet(entries, "GPS");
        set.setColor(getResources().getColor(R.color.course_skyblue));
        set.setLineWidth(3f);
        set.setCircleColor(getResources().getColor(R.color.course_skyblue));
        set.setCircleSize(8f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setValueTextSize(10f);
        set.setValueTextColor(getResources().getColor(R.color.course_skyblue));

        data.addDataSet(set);
        chart.setData(data);
        chart.setDescription("");
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                courseNames.clear();
                if (e == null)
                    return;
                int c = e.getXIndex();
                TextView semester = (TextView) view.findViewById(R.id.semester);
                semester.setText(semesters.get(c));

                recyclerView1 = (RecyclerView) view.findViewById(R.id.semester_list_course);
                recyclerView1.setHasFixedSize(true);
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerView1.setLayoutManager(mLayoutManager);

                ArrayList<Course> courseArrayList = s1.getTranscript().getTakenCoursesBySemester(semesters.get(c));

                semesterCourseListAdapter = new SemesterCourseListAdapter(courseArrayList,s1,semesters.get(c));
                recyclerView1.setAdapter(semesterCourseListAdapter);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return  view;
    }

}