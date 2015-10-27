package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class AnnounceActivity extends AppCompatActivity {


    private Toolbar toolbar;
    String menus[] = {"Schedule","Inbox","Current Courses","Register","Progress","GPA Calculator"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_course,R.drawable.ic_register,R.drawable.ic_progress,R.drawable.ic_calculator};
    String navName = "Bank Thanawat";
    String navEmail = "bankza514@gmail.com";
    int profile = R.drawable.user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    int year_x,month_x,day_x;
        CustomListAdapter adapter;
        String courseSelected;
        static final int DIALOG_ID=0;
        ListView list;

        String[] itemname ={
                "Course",
                "Date",
                "Global",
        };

        Integer[] imgid ={
                R.drawable.ic_course,
                R.drawable.ic_schedule,
                R.drawable.ic_inbox,
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_announce);

            //NAVIGATION DRAWER START HERE!
            toolbar = (Toolbar)findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new MyAdapter(menus,icons,navName,navEmail,profile);
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

            final Calendar cal = Calendar.getInstance();
            year_x = cal.get(Calendar.YEAR);
            month_x = cal.get(Calendar.MONTH);
            day_x = cal.get(Calendar.DAY_OF_MONTH);

            adapter = new CustomListAdapter(this, itemname, imgid,year_x,month_x,day_x,courseSelected);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    switch (position) {
                        case 0:
                            final CharSequence[] course = {" Object Oriented Analysis and Design "," Theory of Computing "," Software Engineering "," Computer Network "};

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(AnnounceActivity.this);
                            builder.setTitle("Select Course");
                            builder.setItems(course, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position) {
                                    courseSelected = (String) course[position];
                                    //Toast.makeText(getApplicationContext(), courseSelected, Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", null);
                            builder.create();

                            builder.show();
                            break;
                        case 1:
                            showDialog(DIALOG_ID);
                            break;
                        case 2:
                            String Selecteditem= itemname[+position];
                            Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        @Override
        protected Dialog onCreateDialog(int id) {
            if (id == DIALOG_ID) {
                return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
            } else {
                return null;
            }
        }

        private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year_x = year;
                month_x = monthOfYear+1;
                day_x = dayOfMonth;
            }
        };



}
