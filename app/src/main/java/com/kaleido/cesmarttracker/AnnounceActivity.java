/*

package com.kaleido.cesmarttracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleido.cesmarttracker.data.Course;

import java.util.ArrayList;
import java.util.Calendar;
public class AnnounceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,OnClickListener{
    Test test = new Test();
    Course select;
    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;

    private String dueDate;

    private Toolbar toolbar;
    String menus[] = {"Schedule","Inbox","Current Courses","Announce","Progress","GPA Calculator"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_course,R.drawable.ic_register,R.drawable.ic_progress,R.drawable.ic_calculator};
    String navName = "Bank Thanawat";
    String navEmail = "bankza514@gmail.com";
    int profile = R.drawable.user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        //NAVIGATION DRAWER START HERE!
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(menus,icons,navName,navEmail,profile,this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        drawer = (DrawerLayout)findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        // NAVIGATION DRAWER END HERE!

        ArrayList<String> courses = new ArrayList<>();
        for(int i=0;i<test.getTeachers().get(0).getCourses().size();i++){
            courses.add(test.getTeachers().get(0).getCourses().get(i).getName());
        }
        Spinner spinner = (Spinner) findViewById(R.id.list_subject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        ib.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
            dueDate =(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        select = test.getTeachers().get(0).getCourses().get(position);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void buttonOnClick(View v) {
        EditText etTitle = (EditText) findViewById(R.id.textTitle);
        String title = etTitle.getText().toString();
        EditText etContent = (EditText) findViewById(R.id.textContent);
        String content = etContent.getText().toString();
        TextView textOut1 = (TextView) findViewById(R.id.textView8);
        test.getTeachers().get(0).announce(select,title,content,dueDate);
        textOut1.setText("Successful");
        Button button=(Button) v;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

*/