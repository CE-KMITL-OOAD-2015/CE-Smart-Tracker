package com.kaleido.cesmarttracker;

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
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.adapter.SimpleRecyclerAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

public class EnrollActivity extends AppCompatActivity {
    Test t = new Test();
    ArrayList<Teacher> a = t.getTeachers();
    ArrayList<Course> courses = a.get(0).getCourses();
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView1;
    int mutedColor = R.attr.colorPrimary;
    SimpleRecyclerAdapter simpleRecyclerAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    RubberLoaderView rubberLoaderView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");

        ImageView header = (ImageView) findViewById(R.id.header);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.course_head);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        getAllCourse();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.color.ColorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.ColorPrimaryDark);
            }
        });

        recyclerView1 = (RecyclerView) findViewById(R.id.scrollableview1);
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(mLayoutManager);

        List<Course> listData = new ArrayList<Course>();
        int ct = 0;
        for (int i = 0; i < courses.size() ; i++) {
            listData.add(courses.get(i));
            ct++;
            if (ct == courses.size()) {
                ct = 0;
            }
        }

        if (simpleRecyclerAdapter == null) {
            simpleRecyclerAdapter = new SimpleRecyclerAdapter(listData);
            recyclerView1.setAdapter(simpleRecyclerAdapter);
        }

        simpleRecyclerAdapter.SetOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int color = 0;
                switch (position%10){
                    case 1:
                        color = (R.color.course_red);
                        break;
                    case 2:
                        color = (R.color.course_yellow);
                        break;
                    case 3:
                        color = (R.color.course_blue);
                        break;
                    case 4:
                        color = (R.color.course_orange);
                        break;
                    case 5:
                        color = (R.color.course_green);
                        break;
                    case 6:
                        color = (R.color.course_red);
                        break;
                    case 7:
                        color = (R.color.course_purple);
                        break;
                    case 8:
                        color = (R.color.course_cyan);
                        break;
                    case 9:
                        color = (R.color.course_yellow);
                        break;
                    case 0:
                        color = (R.color.course_skyblue);
                        break;
                }
                Course c = courses.get(position);
                Intent intent = new Intent(EnrollActivity.this, DetailEnrollCourseActivity.class);
                intent.putExtra("courseName",c);
                intent.putExtra("color", color);
                //Toast.makeText(getBaseContext(), courses.get(position).getName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EnrollActivity.this);
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
                        Gson gson = new Gson();
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