
package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.kaleido.cesmarttracker.adapter.MyAdapter;
import com.kaleido.cesmarttracker.fragment.AnnounceFragment;
import com.kaleido.cesmarttracker.fragment.ProgressTeacherFragment;
import com.kaleido.cesmarttracker.fragment.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//TODO (ripple animation when click on the toolbar)

    private Toolbar toolbar;
    String menus[] = {"Schedule","Inbox","Current Courses","Announce","Progress","GPA Calculator","Logout"};
    String menus2[] = {"Current Courses","Logout"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_course,R.drawable.ic_register,R.drawable.ic_progress,R.drawable.ic_calculator,R.drawable.ic_check};
    int icons2[] = {R.drawable.ic_course,R.drawable.ic_inbox};
    String navName;
    String navId;
    int profile = R.drawable.user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    int role = 0;

    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.flContent).setVisibility(View.VISIBLE);

        userLocalStore = new UserLocalStore(getApplicationContext());

        role = userLocalStore.getRole();
        //NAVIGATION DRAWER START HERE!
        if(role == 1) { //TEACHER'S CASE
            navName = userLocalStore.getTeacherData().getName();
            navId = userLocalStore.getTeacherData().getId();
        }
        else if(role ==2) { //STUDENT'S CASE
            navName = userLocalStore.getStudentData().getName();
            navId = userLocalStore.getStudentData().getId();
        }
        else
            navName = "Life sucks...";

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new ScheduleFragment());
        tx.commit();

        if(role == 2) { //STUDENT'S CASE
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new MyAdapter(menus, icons, navName, navId, profile, this);
            mRecyclerView.setAdapter(mAdapter);

            //-------------------

            final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });


            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    MainActivity mainActivity = MainActivity.this;
                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        drawer.closeDrawers();
                        switch (recyclerView.getChildPosition(child)) {
                            case 1:
                                Fragment MainFragment = new ScheduleFragment();
                                fragmentTransaction.replace(R.id.flContent, MainFragment);
                                fragmentTransaction.commit();
                                break;
                            case 2:
                                Fragment fixtureFragment = new ScheduleFragment();
                                fragmentTransaction.replace(R.id.flContent, fixtureFragment);
                                fragmentTransaction.commit();
                                break;
                            case 3:
                                Intent intent = new Intent(MainActivity.this, EnrollActivity.class);
                                startActivity(intent);
                                break;
                            case 4:
                                Fragment AnnounceFragment = new AnnounceFragment();
                                fragmentTransaction.replace(R.id.flContent, AnnounceFragment);
                                fragmentTransaction.commit();
                                break;
                            case 5:
                                Fragment ProgressFragment = new ProgressTeacherFragment();
                                fragmentTransaction.replace(R.id.flContent, ProgressFragment);
                                fragmentTransaction.commit();
                                break;
                            case 6:
//                                Fragment ProFragment = new GPACalculator();
//                                fragmentTransaction.replace(R.id.flContent, ProFragment);
//                                fragmentTransaction.commit();
                                Intent intent1 = new Intent(MainActivity.this, GPACalculatorActivity.class);
                                startActivity(intent1);
                                break;
                            case 7:
                                userLocalStore.clearUserData();
                                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finish();
                        }
                        // Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                        return true;

                    }

                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });

            //-------------------

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
        }

        else if(role == 1) { //TEACHER'S CASE
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new MyAdapter(menus2, icons2, navName, navId, profile, this);
            mRecyclerView.setAdapter(mAdapter);

            //-------------------

            final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });


            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    MainActivity mainActivity = MainActivity.this;
                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        drawer.closeDrawers();
                        switch (recyclerView.getChildPosition(child)) {
                            case 1:
                                Intent intent = new Intent(MainActivity.this, EnrollActivity.class);
                                startActivity(intent);
                                break;
//                            case 2:
//                                Fragment fixtureFragment = new ScheduleFragment();
//                                fragmentTransaction.replace(R.id.flContent, fixtureFragment);
//                                fragmentTransaction.commit();
//                                break;
//                            case 3:
//                                Intent intent = new Intent(MainActivity.this, EnrollActivity.class);
//                                startActivity(intent);
//                                break;
//                            case 4:
//                                Fragment AnnounceFragment = new AnnounceFragment();
//                                fragmentTransaction.replace(R.id.flContent, AnnounceFragment);
//                                fragmentTransaction.commit();
//                                break;
//                            case 5:
//                                Fragment ProgressFragment = new ProgressTeacherFragment();
//                                fragmentTransaction.replace(R.id.flContent, ProgressFragment);
//                                fragmentTransaction.commit();
//                                break;
//                            case 6:
//                                Fragment ProFragment = new ProgressFragment();
//                                fragmentTransaction.replace(R.id.flContent, ProFragment);
//                                fragmentTransaction.commit();
//                                break;
                            case 2:
                                userLocalStore.clearUserData();
                                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finish();
                        }
                        // Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                        return true;

                    }

                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });

            //-------------------

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
        }
    }

    @Override
    public void onClick(View v) {
        //switch (v.getId())
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

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
