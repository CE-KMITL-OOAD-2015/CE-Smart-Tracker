package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */
public class Progress2 extends Fragment {
    Test test = new Test();
    ListView list;
    Student s1 = test.getStudents().get(0);
    private ArrayAdapter<String> adapter;
    ArrayList<String> semesters = new ArrayList<>();
    ArrayList<String> courseNames = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.progress2,container,false);
        //Create Line Chart
        LineChart chart = (LineChart) view.findViewById(R.id.chart1);

        //For ListView
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,courseNames);
        list = (ListView) view.findViewById(R.id.listCourse);

        semesters= new ArrayList<>(s1.getTranscript().getSemesters());
        LineData data= new LineData(semesters);
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0;i<s1.getTranscript().getSemesters().size();i++) {
            entries.add(new Entry((float)s1.getTranscript().getGPS(s1.getTranscript().getSemesters().get(i)), i));
        }
        LineDataSet set = new LineDataSet(entries, "GPS");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
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

                ArrayList<Course> courseArrayList = s1.getTranscript().getTakenCoursesBySemester(semesters.get(c));
                for(int i=0;i<courseArrayList.size();i++){
                    courseNames.add(courseArrayList.get(i).getName());
                }
                list.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected() {

            }
        });
        return  view;
    }
}
