package com.kaleido.cesmarttracker.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/29/2015 AD.
 */
public class Progress1 extends Fragment {
    UserLocalStore userLocalStore;
    Test test = new Test();
    Student s1 = test.getStudents().get(0);
    ArrayList<Course> courses = new ArrayList<>(s1.getTranscript().getAllTakenCourses());
    ArrayList<String> courseCategories = new ArrayList<>();
    ArrayList<Float> percent;
    private LinearLayout mainLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(getContext());
        s1 = userLocalStore.getStudentData();
        courses = s1.getTranscript().getAllTakenCourses();
        courseCategories = s1.getAllCategory();
        View view = inflater.inflate(R.layout.progress1, container, false);
        //NumberProgressBar bar= (NumberProgressBar)view.findViewById(R.id.number_progress_bar);
        int limit=120;
        //bar.setProgress(s1.getAllCredits() * 100 / limit);
        TextView textView=(TextView)view.findViewById(R.id.credits);
        textView.setText(s1.getTotalCredit() + "/" + limit);

        TextView textView2=(TextView)view.findViewById(R.id.percent);
        textView2.setText(s1.getTotalCredit() * 100 / limit+"%");
        textView2.bringToFront();

        TextView textView1=(TextView)view.findViewById(R.id.text_for_graduate);
        textView1.setText("You need more "+(limit-s1.getTotalCredit())+" credits before graduated.");

        RoundCornerProgressBar roundCornerProgressBar = (RoundCornerProgressBar)view.findViewById(R.id.rounded_corner_progress_bar);
        roundCornerProgressBar.setMax(limit);
        roundCornerProgressBar.setProgress(s1.getTotalCredit());

        for(int i=0;i<courses.size();i++){
            if(!courseCategories.contains(courses.get(i).getCategory())) {
                courseCategories.add(courses.get(i).getCategory());
            }
        }
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

        PieChart mPieChart = (PieChart)view.findViewById(R.id.piechart);
        mPieChart.setInnerPaddingOutline(0);
        mPieChart.setInnerPaddingColor(Color.WHITE);
        // mPieChart.setInnerValueString(courseCategories.get(0));

        mPieChart.addPieSlice(new PieModel(courseCategories.get(0), percent.get(0), Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel(courseCategories.get(1), percent.get(1), Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel(courseCategories.get(2), percent.get(2), Color.parseColor("#CDA67F")));

        mPieChart.startAnimation();


        ImageView hw_spot = (ImageView)view.findViewById(R.id.hw);
        ImageView sw_spot = (ImageView)view.findViewById(R.id.sw);
        ImageView nw_spot = (ImageView)view.findViewById(R.id.nw);

        hw_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable = (GradientDrawable) hw_spot.getBackground();
        drawable.setColor(Color.parseColor("#FE6DA8"));

        sw_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable1 = (GradientDrawable) sw_spot.getBackground();
        drawable1.setColor(Color.parseColor("#56B7F1"));

        nw_spot.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable2 = (GradientDrawable) nw_spot.getBackground();
        drawable2.setColor(Color.parseColor("#CDA67F"));

        return  view;
    }
}