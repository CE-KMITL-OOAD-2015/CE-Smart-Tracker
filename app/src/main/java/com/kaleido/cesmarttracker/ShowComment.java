package com.kaleido.cesmarttracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.kaleido.cesmarttracker.adapter.CommentAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Teacher;

import java.util.ArrayList;

public class ShowComment extends AppCompatActivity {
    Test t = new Test();
    ArrayList<Teacher> a = t.getTeachers();
    ArrayList<Course> courses = a.get(0).getCourses();
    DonutProgress totalRate;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);
        totalRate = (DonutProgress)findViewById(R.id.total_rate);
        totalRate.setProgress(15);
        totalRate.setTextColor(Color.WHITE);
        totalRate.setTextSize(90f);
        totalRate.setUnfinishedStrokeWidth(50f);
        totalRate.setUnfinishedStrokeColor(R.color.comment_head_yellow_light);
        totalRate.setFinishedStrokeColor(Color.WHITE);
        totalRate.setFinishedStrokeWidth(50f);
        totalRate.setMax(50);
        totalRate.setSuffixText("/50");

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_comment);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CommentAdapter(courses.get(0));
        mRecyclerView.setAdapter(mAdapter);

        /*
        adapter = new CommentListAdapter(this,list,rate);
        listView = (ListView)findViewById(R.id.comment_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_comment, menu);
        return true;
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
