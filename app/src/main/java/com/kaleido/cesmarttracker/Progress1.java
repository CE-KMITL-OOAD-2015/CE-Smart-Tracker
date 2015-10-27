package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;


public class Progress1 extends Fragment{
    Test test = new Test();
    Student s1 = test.getStudents().get(0);
    ArrayList<Course> courses = new ArrayList<>(s1.getTranscript().getAllTakenCourses().keySet());
    ArrayList<String> courseCategories = new ArrayList<>();
    ArrayList<Float> percent;
    private RelativeLayout mainLayout;
    private PieChart mChart;

    //private float[] yData = { 5, 10, 15, 30, 40 };
    //private String[] xData = { "Hardware", "Software", "Network", "Security", "Embedded" };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress1,container,false);
        NumberProgressBar bar= (NumberProgressBar)view.findViewById(R.id.number_progress_bar);
        int limit=120;
        bar.setProgress(s1.getAllCredits() * 100 / limit);
        TextView textView=(TextView)view.findViewById(R.id.textView);
        textView.setText(s1.getAllCredits()+"/"+limit);

        mainLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
        mChart = new PieChart(getActivity() );
        mainLayout.addView(mChart);
        mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Categories of Subjects");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(14);
        mChart.setTransparentCircleRadius(17);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener

        // add data
        addData();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;
                int c = e.getXIndex();
                HashMap<Course, Double> courseByCategory = s1.getTranscript().getTakenCoursesByCategory(courseCategories.get(c));
                ArrayList<Course> courseArrayList = new ArrayList<>(courseByCategory.keySet());
                ArrayList<String> courseNames = new ArrayList<String>();
                for(int i=0;i<courseArrayList.size();i++){
                    courseNames.add(courseArrayList.get(i).getName());
                }
                ArrayList<Double> grades = new ArrayList<>(courseByCategory.values());
                CharSequence[] charSequence =  courseNames.toArray(new CharSequence[courseNames.size()]);
                //final CharSequence[] course = {" Digital ", " Advance Digital ", " Computer Organization ", " Computer Architecture "};

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle("Course");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create();
                builder.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        /*
        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        */
        return  view;
    }

    private void addData() {

        //Array courseCategories for pieChart
       // ArrayList<String> courseCategories=new ArrayList<>();
        for(int i=0;i<courses.size();i++){
            if(!courseCategories.contains(courses.get(i).getCategory())) {
                courseCategories.add(courses.get(i).getCategory());
            }
        }
        //Array percent for pieChart
        ArrayList<Float> percent=new ArrayList<>();
        for(int i=0;i<courseCategories.size();i++){
            float count=0;
            for(int j=0;j<courses.size();j++){
                if(courseCategories.get(i).contains(courses.get(j).getCategory())) {
                    count++;
                }
            }
            percent.add(count*100/courses.size());
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < courses.size(); i++)
            yVals1.add(new Entry(percent.get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < courses.size(); i++)
            xVals.add(courseCategories.get(i));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }

}