package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.adapter.CurrentCourseAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Schedule;
import com.kaleido.cesmarttracker.data.Section;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CurrentCourseActivity extends AppCompatActivity {
    Test t = new Test();
    ArrayList<Teacher> a = t.getTeachers();
    ArrayList<Course> courses ;//= a.get(0).getCourses();
    ArrayList<Section> sections;
    final Activity act = this;
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView1;
    int mutedColor = R.attr.colorPrimary;
    CurrentCourseAdapter simpleRecyclerAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    int role = 2;

    RubberLoaderView rubberLoaderView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_course2);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());

        role = userLocalStore.getRole();
        Schedule sch = userLocalStore.getStudentData().getSchedule();
        courses = new ArrayList<Course>();
        courses = sch.getCurrentCourses();
        sections = new ArrayList<Section>();




        sections = sch.getCurrentSections();
        mLayoutManager = new LinearLayoutManager(this);

        getAllCourse();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitle("");

                ImageView header = (ImageView) findViewById(R.id.header);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.course_head);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @SuppressWarnings("ResourceType")
                    @Override
                    public void onGenerated(Palette palette) {
                        mutedColor = palette.getMutedColor(R.color.ColorPrimary);
                        collapsingToolbar.setContentScrimColor(mutedColor);

                        //collapsingToolbar.setTitle("Current Course");





                        collapsingToolbar.setStatusBarScrimColor(R.color.ColorPrimaryDark);
                    }
                });

                recyclerView1 = (RecyclerView) findViewById(R.id.scrollableview1);
                recyclerView1.setHasFixedSize(true);
                //mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

                recyclerView1.setLayoutManager(mLayoutManager);

                List<Course> listData = new ArrayList<Course>();
                List<Section> listSection = new ArrayList<Section>();
                int ct = 0;
                for (int i = 0; i < sections.size() ; i++) {
                    listData.add(courses.get(i));
                    listSection.add(sections.get(i));
                    ct++;

                    if (ct == courses.size()) {
                        ct = 0;
                    }
                }



                if (simpleRecyclerAdapter == null) {
                    simpleRecyclerAdapter = new CurrentCourseAdapter(listData,listSection);
                    recyclerView1.setAdapter(simpleRecyclerAdapter);
                }

                simpleRecyclerAdapter.SetOnItemClickListener(new CurrentCourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int color = 0;
                        position = position+1;
                        switch (position%10){
                            case 1:
                                color = (R.color.course_red1);
                                break;
                            case 2:
                                color = (R.color.course_orange1);
                                break;
                            case 3:
                                color = (R.color.course_green1);
                                break;
                            case 4:
                                color = (R.color.course_green2);
                                break;
                            case 5:
                                color = (R.color.course_skyblue1);
                                break;
                            case 6:
                                color = (R.color.course_blue1);
                                break;
                            case 7:
                                color = (R.color.course_light_purple1);
                                break;
                            case 8:
                                color = (R.color.course_purple1);
                                break;
                            case 9:
                                color = (R.color.course_red1);
                                break;
                            case 0:
                                color = (R.color.course_orange1);
                                break;
                        }



                        Course c = courses.get(position);
                        Intent intent;
                        if(role==1) {
                            intent = new Intent(CurrentCourseActivity.this, ShowComment.class);
                            intent.putExtra("courseName", c);
                            startActivity(intent);
                        }
                        else if(role==2) {
                            intent = new Intent(CurrentCourseActivity.this, DetailEnrollCourseActivity.class);
                            intent.putExtra("courseName",c);
                            intent.putExtra("color", color);
                            startActivity(intent);
                        }
                    }
                });
            }
        }, 1000);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorMessage(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CurrentCourseActivity.this);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void getAllCourse() {
        ConnectHttp.get("courses", null, new AsyncHttpResponseHandler() {
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
                        Type courseListType = new TypeToken<List<Course>>() {
                        }.getType();
                        List<Course> courseList = gson.fromJson(response, courseListType);
                        //Student s = new ObjectMapper().readValue(response, Student.class);
                        //showErrorMessage(response);
                        courses = (ArrayList<Course>) courseList;
                        for (Course ca : courses)
                            System.out.println(ca.getName());
                        System.out.println("SUCCESS!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    showErrorMessage("Error!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error code : " + statusCode);
            }
        });
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
