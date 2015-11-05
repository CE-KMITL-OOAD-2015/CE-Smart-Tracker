package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Section;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;


/**
 * Created by monkiyes on 10/30/2015 AD.
 */
public class Progress3 extends Fragment {
    Course c;
    ArrayList<String> scores = new ArrayList<>();
    String name;
    private ArrayAdapter<String> adapter;
    public Progress3(Course c){
        this.c = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.progress2,container,false);
        //Create Line Chart
        LineChart chart = (LineChart) view.findViewById(R.id.chart1);
        ArrayList<Section> sections = c.getAllSection();
        ArrayList<Student> students = sections.get(0).getAllStudent();
        name = students.get(0).getName();
        scores = new ArrayList<>(students.get(0).getTranscript().getSemesters());
        LineData data= new LineData(scores);
        ArrayList<Entry> entries = new ArrayList<>();
        //TODO add entries list
        for(int i=0;i<students.get(0).getTranscript().getSemesters().size();i++) {
            entries.add(new Entry((float)students.get(0).getTranscript().getGPS(students.get(0).getTranscript().getSemesters().get(i)), i));
        }
        LineDataSet set = new LineDataSet(entries, "Scores :");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        data.addDataSet(set);
        chart.setData(data);
        chart.setDescription("");
        /*
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                courseNames.clear();
                if (e == null)
                    return;
                int c = e.getXIndex();
                HashMap<Course, Double> coursesBySemester = s1.getTranscript().getTakenCoursesBySemester(semesters.get(c));
                ArrayList<Course> courseArrayList = new ArrayList<>(coursesBySemester.keySet());
                for(int i=0;i<courseArrayList.size();i++){
                    courseNames.add(courseArrayList.get(i).getName());
                }
                list.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected() {

            }
        });
        */
        return  view;
    }
}
