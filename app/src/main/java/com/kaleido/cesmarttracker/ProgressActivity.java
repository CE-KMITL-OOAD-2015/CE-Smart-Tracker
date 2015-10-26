package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;

public class ProgressActivity extends Activity implements OnChartValueSelectedListener {
    Test test= new Test();
    Student s1= test.getStudents().get(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        //progressBar
        NumberProgressBar bar= (NumberProgressBar) findViewById(R.id.number_progress_bar);
        int limit=120;
        bar.setProgress(s1.getAllCredits() * 100 / limit);
        TextView textView=(TextView) findViewById(R.id.textView);
        textView.setText(s1.getAllCredits()+"/"+limit);

        //lineChart
        LineChart chart = (LineChart) findViewById(R.id.chart1);
        ArrayList<String> semesters= new ArrayList<>(s1.getTranscript().getSemesters());
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
        chart.setOnChartValueSelectedListener(this);

        // TODO: 10/26/2015 pieChart
        ArrayList<Course> courses=new ArrayList<>(s1.getTranscript().getAllTakenCourses().keySet());
        //Array courseCategories for pieChart
        ArrayList<String> courseCategories=new ArrayList<>();
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

    }
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: " + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
