package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Period;
import com.kaleido.cesmarttracker.data.Schedule;
import com.kaleido.cesmarttracker.data.Section;

public class StudyPlanFragment extends Fragment {

    private View view;
    private TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_study_plan,container,false);
        Schedule sc;
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayout1);
        tableLayout.setColumnShrinkable(1, true);
        //public Tabletestei(){
        sc = new Schedule();
        Course c = new Course("Theory Of Computation", "1", "Software",3);
        Period p = new Period("9.00", 3, "Monday");
        Section s = new Section("III",501, p, 10);

        Course c1 = new Course("Object Oreinted Analysis and Design", "2", "Software", 3);
        Period p1 = new Period("13.00", 3, "Friday");
        Section s1 = new Section("II",502, p1, 12);

        Course c2 = new Course("Computer Programming II", "3","Software", 3);
        Period p2 = new Period("16.30", 3, "Friday");
        Section s2 = new Section("I",505, p2, 100);

        Course c3 = new Course("Data Communication", "4","Software", 3);
        Period p3 = new Period("9.00", 3, "Tuesday");
        Section s3 = new Section("IIII",555, p3, 1000);


        // sc.getCurrentSections() = sections;
        // sc.getCurrentCourses() = courses;
        sc.addCourse(c, s);
        sc.addCourse(c1, s1);
        sc.addCourse(c2, s2);
        sc.addCourse(c3, s3);
        // courses = new ArrayList<>();
        // sections = new ArrayList<>();
        // courses = sc.getCurrentCourses();
        // sections = sc.getCurrentSections();
        addTableSubject(sc);
        // }
        return inflater.inflate(R.layout.fragment_study_plan, container, false);
    }

    public void addTableSubject(Schedule scc) {
        try {
            String p = "";
            String d = "";
            for (int i = 0; i < scc.getCurrentCourses().size(); i++) {
                Course cc = scc.getCurrentCourses().get(i);
                Section sec = scc.findSectionByCourse(cc);
                Period per = sec.getPeriod();
                String periodd = p.replaceAll(p, per.getClassStart());
                String day = d.replaceAll(d, per.getDay());
                //monday
                if (per.getClassStart().equals("9.00") && per.getDay().equals("Monday")) {
                    txt = (TextView) view.findViewById(R.id.mon1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && per.getDay().equals("Monday")) {
                    txt = (TextView) view.findViewById(R.id.mon2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Monday")) {
                    txt = (TextView) view.findViewById(R.id.mon3);
                    txt.setText(cc.getName());
                }
                //tuesday
                else if (per.getClassStart().equals("9.00") && per.getDay().equals("Tuesday")) {
                    txt = (TextView) view.findViewById(R.id.tue1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && per.getDay().equals("Tuesday")) {
                    txt = (TextView) view.findViewById(R.id.tue2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Tuesday")) {
                    txt = (TextView) view.findViewById(R.id.tue3);
                    txt.setText(cc.getName());
                }
                //wednesday
                else if (per.getClassStart().equals("9.00") && per.getDay().equals("Wednesday")) {
                    txt = (TextView) view.findViewById(R.id.wed1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && per.getDay().equals("Wednesday")) {
                    txt = (TextView) view.findViewById(R.id.wed2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Wednesday")) {
                    txt = (TextView) view.findViewById(R.id.wed3);
                    txt.setText(cc.getName());
                }
                //thursday
                else if (per.getClassStart().equals("9.00") && per.getDay().equals("Thursday")) {
                    txt = (TextView) view.findViewById(R.id.thu1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && per.getDay().equals("Thursday")) {
                    txt = (TextView) view.findViewById(R.id.thu2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Thursday")) {
                    txt = (TextView) view.findViewById(R.id.thu3);
                    txt.setText(cc.getName());
                }
                //friday
                else if (per.getClassStart().equals("9.00") && per.getDay().equals("Friday")) {
                    txt = (TextView) view.findViewById(R.id.fri1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && day.equals("Friday")) {
                    txt = (TextView) view.findViewById(R.id.fri2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Friday")) {
                    txt = (TextView) view.findViewById(R.id.fri3);
                    txt.setText(cc.getName());
                }
                //saturday
                else if (per.getClassStart().equals("9.00") && per.getDay().equals("Saturday")) {
                    txt = (TextView) view.findViewById(R.id.sat1);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("13.00") && per.getDay().equals("Saturday")) {
                    txt = (TextView) view.findViewById(R.id.sat2);
                    txt.setText(cc.getName());
                } else if (per.getClassStart().equals("16.30") && per.getDay().equals("Saturday")) {
                    txt = (TextView) view.findViewById(R.id.sat3);
                    txt.setText(cc.getName());
                }
            }

        } catch (NullPointerException e) {
            System.out.print("\n");
        }

    }

}
