package com.kaleido.cesmarttracker;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Transcript;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GPACalculator extends Fragment implements AdapterView.OnItemSelectedListener {

    Test test= new Test();
    Transcript transcript = test.getStudents().get(0).getTranscript();
    //Transcript transcript = new Transcript();

    private double grade = 0.00;

    //private Spinner c2;
    private ArrayList<String> c1 = new ArrayList<String>();
    private ArrayList<Course> currentCourse = new ArrayList<Course>();
    private TextView gps;
    private TextView GPA;
    private TextView newGPA;
    private TextView newGPACir;
    private RelativeLayout relativeLay;
    private View view;

    //private String aa;
    private double[][] creditAndGradeThis = new double[test.getStudents().get(0).getCurrentCourses().size()][2];
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gpacalculator,container,false);

        currentCourse = test.getStudents().get(0).getCurrentCourses();
        relativeLay = (RelativeLayout) view.findViewById(R.id.relative);


        super.onCreate(savedInstanceState);

        newGPACir = (TextView) view.findViewById(R.id.newGPACir);
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
            layin.setPadding(60, 0, 0, 0);
            TextView text = new TextView(getActivity());
            text.setWidth(470);

            text.setTextSize(15);

            //text.setHeight(100);


            TextView credit = new TextView(getActivity());
            credit.setWidth(320);
            credit.setTextSize(15);
            //credit.setHeight(100);

            Spinner c3 = new Spinner(getActivity());
            c3.setId(i);
            c3.setMinimumWidth(30);
            c3.setMinimumHeight(100);
            c3.setAdapter(list);
            c3.setOnItemSelectedListener(this);

            text.setText("" + currentCourse.get(i).getName());
            credit.setText("" + currentCourse.get(i).getCredit());
            newGPACir.setText(""+calNewGPA());

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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_gpacalculator, menu);
//        return true;
//    }


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
        changeColor();
        newGPACir.setText(""+calNewGPA());


    }


    public void changeColor(){

        double newGPA = calNewGPA();
        LinearLayout bgLight = (LinearLayout) view.findViewById(R.id.bgLight);
        LinearLayout bgHard1 = (LinearLayout) view.findViewById(R.id.bgHard1);
        LinearLayout bgHard2 = (LinearLayout) view.findViewById(R.id.bgHard2);
        FrameLayout space = (FrameLayout) view.findViewById(R.id.bg);
        FrameLayout space2 = (FrameLayout) view.findViewById(R.id.bg2);
        if(newGPA>3.00){
            //green
            bgLight.setBackgroundColor(Color.parseColor("#b4d376"));
            space.setBackgroundColor(Color.parseColor("#b4d376"));
            space2.setBackgroundColor(Color.parseColor("#b4d376"));
            newGPACir.setTextColor(Color.parseColor("#b4d376"));
            bgHard1.setBackgroundColor(Color.parseColor("#9bb665"));
            bgHard2.setBackgroundColor(Color.parseColor("#8da460"));

        }
        else if(newGPA>2.00){
            //yellow
            bgLight.setBackgroundColor(Color.parseColor("#f2ce53"));
            space.setBackgroundColor(Color.parseColor("#f2ce53"));
            space2.setBackgroundColor(Color.parseColor("#f2ce53"));
            newGPACir.setTextColor(Color.parseColor("#f2ce53"));
            bgHard1.setBackgroundColor(Color.parseColor("#d1b552"));
            bgHard2.setBackgroundColor(Color.parseColor("#c8a851"));

        }

        else if(newGPA>1.00){
            //RedLi
            bgLight.setBackgroundColor(Color.parseColor("#d37778"));
            space.setBackgroundColor(Color.parseColor("#d37778"));
            space2.setBackgroundColor(Color.parseColor("#d37778"));
            newGPACir.setTextColor(Color.parseColor("#d37778"));
            bgHard1.setBackgroundColor(Color.parseColor("#b75f5e"));
            bgHard2.setBackgroundColor(Color.parseColor("#aa5251"));

        }

        else {
            //RedHa
            bgLight.setBackgroundColor(Color.parseColor("#db5556"));
            space.setBackgroundColor(Color.parseColor("#db5556"));
            space2.setBackgroundColor(Color.parseColor("#db5556"));
            newGPACir.setTextColor(Color.parseColor("#db5556"));
            bgHard1.setBackgroundColor(Color.parseColor("#c44443"));
            bgHard2.setBackgroundColor(Color.parseColor("#b7393a"));

        }


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
            show = "(Up "+(diff)+")";
        }
        else if(newGPA<currentGPA){
            diff = currentGPA-newGPA;
            DecimalFormat df = new DecimalFormat("###.##");
            diff = Double.parseDouble(df.format(diff));
            show = "(Down "+(diff)+")";
        }
        else{
            show = "(Stable)";
        }




        return show;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
