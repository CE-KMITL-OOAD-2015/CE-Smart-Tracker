package com.kaleido.cesmarttracker.fragment;

/**
 * Created by pirushprechathavanich on 11/18/15.
 */

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.ConnectHttp;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.adapter.StudentListByScoreAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ProgressTeacherFragment1 extends Fragment {

    protected LineChart mChart;
    private UserLocalStore userLocalStore;
    Teacher t1;
    //TODO
    private Test test=new Test();
    Student s1= test.getStudents().get(0);
    View view;
    Course selectedCourse;
    RubberLoaderView rubberLoaderView;
    Dialog dialog;

    public ProgressTeacherFragment1(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
    ArrayList<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=inflater.inflate(R.layout.progress_teacher_graph_card,container,false);
        userLocalStore = new UserLocalStore(view.getContext());
        t1 = userLocalStore.getTeacherData();

        showLoadingDialog();
        ConnectHttp.get("courses/" + selectedCourse.getId() + "/students", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                for (int i = 0; i < responseBody.length; i++)
                    response += (char) responseBody[i];
                Log.i("res", response);
                if (!response.isEmpty()) {
                    try {
                        //JSONObject json = new JSONObject(response);
                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            @Override
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        Gson gson = builder.create();
                        Type studentType = new TypeToken<ArrayList<Student>>() {
                        }.getType();
                        ArrayList<Student> retrievedStudents = gson.fromJson(response, studentType);
                        students = retrievedStudents;
                        setting();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println("Error : retrieved null data from server.");
                stopLoadingDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                stopLoadingDialog();
                System.out.println("Error : " + statusCode);
            }
        });
        return  view;
    }

    public void setting(){


        //Array studentNames for selectStudent
        final ArrayList<String> studentNames = new ArrayList<>();
        for (int i = 0; i < studentNames.size(); i++) {
            studentNames.add(students.get(i).getName());
        }

        //Array totalGrades for totalGradeOfStudent
        ArrayList<Integer> totalGrades = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            totalGrades.add(students.get(i).getTotalGradeAssignment(selectedCourse));
        }

        //if student be selected
        //selectedStudent
        //Array assignmentTitles

        //Array for graph

        int[] x= new int[21];
        for(int i=0;i<21;i++){
            x[i]=0;
        }
        int min=0;
        int max=0;
        int average=0;
        for(Student s:students) {
            if(min==0)
                min=s.getTotalGradeAssignment(selectedCourse);
            else if(min>s.getTotalGradeAssignment(selectedCourse))
                min=s.getTotalGradeAssignment(selectedCourse);
            if(max<s.getTotalGradeAssignment(selectedCourse))
                max=s.getTotalGradeAssignment(selectedCourse);
            average+=s.getTotalGradeAssignment(selectedCourse);
            for (int j = 0; j < 21; j++) {
                if(s.getMaxGradeAssignment(selectedCourse)!=0) {
                    if ((5 * j) - 2 < s.getTotalGradeAssignment(selectedCourse) * 100 / s.getMaxGradeAssignment(selectedCourse) && s.getTotalGradeAssignment(selectedCourse) * 100 / s.getMaxGradeAssignment(selectedCourse) < (5 * j) + 3) {
                        x[j]++;
                    }
                }
            }
        }
        average=average/students.size();
        TextView minText= (TextView) view.findViewById(R.id.textView37);
        minText.setText(""+min);
        TextView maxText= (TextView) view.findViewById(R.id.textView39);
        maxText.setText(""+max);
        TextView averageText= (TextView) view.findViewById(R.id.textView41);
        averageText.setText(""+average);
        int most=0;
        for(int i=0;i<21;i++) {
            if (most < x[i])
                most=i*5;
        }
        TextView mostPercent= (TextView) view.findViewById(R.id.textView34);
        mostPercent.setText(""+most);
        LineChart lineChart= (LineChart) view.findViewById(R.id.lineChart);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(false);
        lineChart.animateX(2000);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(getResources().getColor(R.color.grey_title));
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setLabelCount(8);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setLabelCount(8);
        rightAxis.setSpaceTop(15f);

        Legend l = lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        String[] str=new String[21];
        for(int i=0;i<21;i++){
            str[i]=""+i*5;
        }
        LineData data= new LineData(str);
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0;i<21;i++) {
            entries.add(new Entry(x[i], i));
        }

        LineDataSet set = new LineDataSet(entries, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setDrawFilled(true);
        set.setDrawCircles(false);
        set.setLineWidth(1f);
        set.setCircleSize(4f);
        set.setCircleColor(Color.BLUE);
        set.setHighLightColor(Color.BLUE);
        set.setColor(Color.WHITE);
        set.setFillColor(Color.BLUE);
        set.setFillAlpha(100);
        set.setDrawValues(false);
        data.addDataSet(set);
        lineChart.setData(data);
        lineChart.setDescription("");
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
                int c = e.getXIndex();
                ArrayList<String> studentList=new ArrayList<>();
                ArrayList<Integer> scores=new ArrayList<Integer>();
                for(Student s:students) {
                    if (s.getTotalGradeAssignment(selectedCourse) * 100 / s.getMaxGradeAssignment(selectedCourse) > (5 * c) - 2 && s.getTotalGradeAssignment(selectedCourse) * 100 / s.getMaxGradeAssignment(selectedCourse) < (5 * c) + 3) {
                        studentList.add(s.getName());
                        scores.add(s.getTotalGradeAssignment(selectedCourse));
                    }
                }
                StudentListByScoreAdapter adapter = new StudentListByScoreAdapter(getActivity(),studentList,scores);
                ListView listView = (ListView) view.findViewById(R.id.studentByScoreList);
                listView.setAdapter(adapter);
                TextView text1= (TextView) view.findViewById(R.id.textView43);
                if(studentList.size()<2)
                    text1.setText("There's ");
                else text1.setText("There're ");
                TextView num= (TextView) view.findViewById(R.id.textView44);
                num.setText(""+studentList.size());
                TextView text2= (TextView) view.findViewById(R.id.textView45);
                if(studentList.size()<2)
                    text2.setText(" student");
                else text2.setText(" students");
                TextView mostPercent= (TextView) view.findViewById(R.id.textView47);
                mostPercent.setText(c*5+" percent");
            }
            @Override
            public void onNothingSelected() {}
        });
    }

    private void showLoadingDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View loadingDialog = inflater.inflate(R.layout.loading_dialog, null);
        rubberLoaderView = (RubberLoaderView)loadingDialog.findViewById(R.id.loader1);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setContentView(R.layout.loading_dialog);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(loadingDialog);
        rubberLoaderView.startLoading();
        dialog.show();
    }

    private void stopLoadingDialog() {
        dialog.cancel();
    }
}
