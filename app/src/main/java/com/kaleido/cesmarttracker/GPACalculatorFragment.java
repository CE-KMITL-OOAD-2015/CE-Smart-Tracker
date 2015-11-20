package com.kaleido.cesmarttracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Transcript;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by monkiyes on 10/31/2015 AD.
 */
public class GPACalculatorFragment extends Fragment {

    Test test= new Test();
    Transcript transcript;
    //Transcript transcript = new Transcript();
    UserLocalStore userLocalStore;

    private double grade = 0.00;

    //private Spinner c2;
    private ArrayList<String> c1 = new ArrayList<String>();
    private ArrayList<Course> currentCourse = new ArrayList<Course>();
    private TextView gps;
    private TextView GPA;
    private TextView newGPA;

    //private String aa;
    private double[][] creditAndGradeThis;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userLocalStore = new UserLocalStore(getContext());
        creditAndGradeThis = new double[userLocalStore.getStudentData().getCurrentCourses().size()][2];
        transcript = userLocalStore.getStudentData().getTranscript();

        View view = inflater.inflate(R.layout.activity_gpacalculator,container,false);
        currentCourse = userLocalStore.getStudentData().getCurrentCourses();
        super.onCreate(savedInstanceState);
        newGPA = (TextView) view.findViewById(R.id.newGPA);
        gps = (TextView) view.findViewById(R.id.GPS);
        GPA = (TextView) view.findViewById(R.id.GPA);
        //aa = Double.toString(transcript.gpaCalculate());
        GPA.setText("" + transcript.getGPA());

        //c2 = (Spinner) findViewById(R.id.spinner1);
        createGrade();
        plusGrade();


        ArrayAdapter<String> list = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, c1);
        //c2.setAdapter(list);
        //c2.setOnItemSelectedListener(this);


        for (int i = 0; i < test.getStudents().get(0).getCurrentCourses().size(); i++) {

            creditAndGradeThis[i][0] = (double)(currentCourse.get(i).getCredit());

            LinearLayout lay = (LinearLayout) view.findViewById(R.id.layout);
            LinearLayout layin = new LinearLayout(getActivity());
            layin.setOrientation(LinearLayout.HORIZONTAL);
            TextView text = new TextView(getActivity());
            text.setWidth(390);
            //text.setHeight(100);


            TextView credit = new TextView(getActivity());
            credit.setWidth(270);
            //credit.setHeight(100);

            Spinner c3 = new Spinner(getActivity());
            c3.setId(i);
            c3.setMinimumWidth(30);
            c3.setMinimumHeight(100);
            c3.setAdapter(list);
            c3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView text = (TextView) view;
                    String result = "0.00";


                    if (text.getText() == "A") {
                        result = "4.00";

                    } else if (text.getText() == "B+") {
                        result = "3.50";
                    } else if (text.getText() == "B") {
                        result = "3.00";
                    } else if (text.getText() == "C+") {
                        result = "2.50";
                    } else if (text.getText() == "C") {
                        result = "2.00";
                    } else if (text.getText() == "D+") {
                        result = "1.50";
                    } else if (text.getText() == "D") {
                        result = "1.00";
                    } else if (text.getText() == "F") {
                        result = "0.00";
                    }



                    for(int i=0 ; i< test.getStudents().get(0).getCurrentCourses().size() ; i++){
                        if(parent.getId()==i){
                            creditAndGradeThis[i][1] = Double.parseDouble(result);
                        }
                    }

                    gps.setText(""+calGPS());
                    newGPA.setText(""+calNewGPA() +"  "+ difference());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            text.setText("" + currentCourse.get(i).getName());
            credit.setText("" + currentCourse.get(i).getCredit());

            layin.addView(text);
            layin.addView(credit);
            layin.addView(c3);
            lay.addView(layin);

        }

        return view;
    }


    private void createGrade() {
        c1.add("A");
        c1.add("B+");
        c1.add("B");
        c1.add("C+");
        c1.add("C");
        c1.add("D+");
        c1.add("D");
        c1.add("F");
    }


    public String difference(){
        double newGPA = 0.00;
        double currentGPA = 0.00;
        double diff = 0.00;
        String show;
        newGPA = calNewGPA();
        currentGPA = transcript.getGPA();
        if(newGPA>currentGPA){
            diff = newGPA-currentGPA;
            DecimalFormat df = new DecimalFormat("###.##");
            diff = Double.parseDouble(df.format(diff));
            show = "(Increse "+(diff)+")";
        }
        else if(newGPA<currentGPA){
            diff = currentGPA-newGPA;
            DecimalFormat df = new DecimalFormat("###.##");
            diff = Double.parseDouble(df.format(diff));
            show = "(Decrease "+(diff)+")";
        }
        else{
            show = "(Stable)";
        }




        return show;
    }


    public double getCredit(){
        double credit = 0.00;
        for(int i = 0 ; i<test.getStudents().get(0).getCurrentCourses().size();i++){
            credit = credit + creditAndGradeThis[i][0];
        }
        return credit;
    }

    public double plusGrade(){
        double gradeBeforeDevide = 0.00;
        for(int i = 0 ; i < test.getStudents().get(0).getCurrentCourses().size() ; i++) {
            gradeBeforeDevide = gradeBeforeDevide + (creditAndGradeThis[i][0] * creditAndGradeThis[i][1]);
        }
        return gradeBeforeDevide;
    }

    public double calGPS(){
        double grade = 0.00;
        double credit = 0.00;
        credit = getCredit();
        grade = plusGrade()/credit;
        DecimalFormat df = new DecimalFormat("###.##");
        grade = Double.parseDouble(df.format(grade));
        return grade;
    }

    public double calNewGPA(){
        double oldGradeBeforeDevide = transcript.getGPA()*transcript.getTotalCredit();
        double gradeBeforeDevide = plusGrade();
        double credit = getCredit();
        double oldCredit = transcript.getTotalCredit();
        double newGPA = (oldGradeBeforeDevide+gradeBeforeDevide)/(credit+oldCredit);

        DecimalFormat df = new DecimalFormat("###.##");
        newGPA = Double.parseDouble(df.format(newGPA));


        return newGPA;



    }

}