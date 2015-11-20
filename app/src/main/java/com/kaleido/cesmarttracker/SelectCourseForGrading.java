package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.data.Course;

import java.util.ArrayList;

public class SelectCourseForGrading extends AppCompatActivity {

    private ArrayList<Course> courses = new ArrayList<Course>();
    private int selected = 0;
    private Button button;
    private UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_for_grading);
        userLocalStore = new UserLocalStore(getApplicationContext());

        courses = userLocalStore.getTeacherData().getCourses();

        button  = (Button) findViewById(R.id.button);

//        courses.add(c1);
//        courses.add(c2);
//        courses.add(c3);
//        courses.add(c4);
//        courses.add(c5);
//        courses.add(c6);

        LinearLayout inScroll = (LinearLayout) findViewById(R.id.layInScroll);

        for(int i = 0;i<courses.size();i++){
            TextView course = new TextView(this);
            course.setText(courses.get(i).getName());
            course.setTextSize(15);
            course.setTextColor(Color.parseColor("#1B1B1B"));
            course.setGravity(Gravity.CENTER_VERTICAL);
            course.setId(i);
            course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0 ; j < courses.size() ; j++){
                        TextView course = (TextView) findViewById(j);
                        course.setBackgroundColor(Color.parseColor("#FFE7E8E6"));
                    }
                    v.setBackgroundColor(Color.parseColor("#EBD88D"));
                    selected = v.getId();

                }
            });
            course.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
            course.setBackgroundColor(Color.parseColor("#FFE7E8E6"));
            course.setPadding(300, 0, 0, 0);
            inScroll.addView(course);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),GradeActivity.class);
                i.putExtra("course",courses.get(selected));
                startActivity(i);
            }
        });




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
