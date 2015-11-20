
package com.kaleido.cesmarttracker;

import android.app.Dialog;
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
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.adapter.MyAdapter;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.fragment.AnnounceFragment;
import com.kaleido.cesmarttracker.fragment.InboxFragment;
import com.kaleido.cesmarttracker.fragment.ProgressFragment;
import com.kaleido.cesmarttracker.fragment.ProgressTeacherFragment;
import com.kaleido.cesmarttracker.fragment.ScheduleFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//TODO (ripple animation when click on the toolbar)

    private Toolbar toolbar;
    String menus[] = {"Schedule","Inbox","Current Courses","Course Offerings","Progress","GPA Calculator","Logout"};
    String menus2[] = {"Schedule","Current Courses","Grading","Score Assignment","Progress","Logout"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_current_course,R.drawable.ic_school_black_48dp,R.drawable.ic_progress,R.drawable.ic_exposure_black_48dp,R.drawable.ic_logout};
    int icons2[] = {R.drawable.ic_schedule,R.drawable.ic_current_course,R.drawable.ic_grading,R.drawable.ic_progress,R.drawable.ic_grading,R.drawable.ic_logout};
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

    protected RubberLoaderView rubberLoaderView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.flContent).setVisibility(View.VISIBLE);
        System.gc();

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
                    final FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        drawer.closeDrawers();
                        switch (recyclerView.getChildPosition(child)) {
                            case 1:
                                Fragment MainFragment = new ScheduleFragment();
                                fragmentTransaction.replace(R.id.flContent, MainFragment);
                                fragmentTransaction.commit();
                                break;
                            case 2:
                                Fragment inboxFragment = new InboxFragment();
                                fragmentTransaction.replace(R.id.flContent, inboxFragment);
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
//                            case 5:
//                                Fragment ProgressFragment = new ProgressFragment();
//                                fragmentTransaction.replace(R.id.flContent, ProgressFragment);
//                                fragmentTransaction.commit();
//                                break;
                            case 5:
                                showLoadingDialog();
                                System.gc();
                                ConnectHttp.get("students/" + userLocalStore.getStudentData().getId(), null, new AsyncHttpResponseHandler() {
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
                                                Type studentType = new TypeToken<Student>() {}.getType();
                                                Student updatedStudent = gson.fromJson(response, studentType);
                                                userLocalStore.storeStudentData(updatedStudent);
                                                Fragment ProgressFragment = new ProgressFragment();
                                                fragmentTransaction.replace(R.id.flContent,ProgressFragment);
                                                fragmentTransaction.commit();
                                                stopLoadingDialog();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else
                                            System.out.println("Error! Cannot update student data.");
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        System.out.println("Error : "+statusCode+" , cannot update student data.");
                                    }
                                });
                                break;
                            case 6:
//                                Fragment ProFragment = new GPACalculator();
//                                fragmentTransaction.replace(R.id.flContent, ProFragment);
//                                fragmentTransaction.commit();
                                userLocalStore.print();
//                                Intent intent1 = new Intent(MainActivity.this, GPACalculatorActivity.class);
                                Intent intent1 = new Intent(MainActivity.this, CurrentCourseActivity.class);
                                startActivity(intent1);
                                break;
                            case 7:
                                userLocalStore.clearUserData();
                                userLocalStore.setUserLoggedIn(false);
                                LoginManager.getInstance().logOut();
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
                            case 2:
                                Fragment fixtureFragment = new ScheduleFragment();
                                fragmentTransaction.replace(R.id.flContent, fixtureFragment);
                                fragmentTransaction.commit();
                                break;
                            case 3:
                                Intent intent1 = new Intent(MainActivity.this, SelectCourseForGrading.class);
                                startActivity(intent1);
                                break;
//                            case 3:
//                                Intent intent = new Intent(MainActivity.this, EnrollActivity.class);
//                                startActivity(intent);
//                                break;
//                            case 4:
//                                Fragment AnnounceFragment = new AnnounceFragment();
//                                fragmentTransaction.replace(R.id.flContent, AnnounceFragment);
//                                fragmentTransaction.commit();
//                                break;
                            case 5:
                                Fragment ProgressFragment = new ProgressTeacherFragment();
                                fragmentTransaction.replace(R.id.flContent, ProgressFragment);
                                fragmentTransaction.commit();
                                break;
                            case 4:
                                Intent intent2 = new Intent(MainActivity.this, SelectCourseForGradingAssignment.class);
                                startActivity(intent2);
                                break;
//                            case 6:
//                                Fragment ProFragment = new ProgressFragment();
//                                fragmentTransaction.replace(R.id.flContent, ProFragment);
//                                fragmentTransaction.commit();
//                                break;
                            case 6:
                                userLocalStore.clearUserData();
                                Intent intent5 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent5);
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

    private void showLoadingDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View loadingDialog = inflater.inflate(R.layout.loading_dialog, null);
        rubberLoaderView = (RubberLoaderView)loadingDialog.findViewById(R.id.loader1);
        dialog = new Dialog(this);
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
