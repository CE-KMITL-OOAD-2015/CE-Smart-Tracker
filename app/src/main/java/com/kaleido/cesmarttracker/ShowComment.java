package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaleido.cesmarttracker.adapter.CommentListAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Review;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ShowComment extends AppCompatActivity {
    Test t = new Test();
    ArrayList<Teacher> a = t.getTeachers();
    ArrayList<Course> courses = a.get(0).getCourses();
    DonutProgress totalRate;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    CommentListAdapter adapter;
    ListView listView;
    String courseName;
    Course c;
    ArrayList<Review> reviews = new ArrayList<Review>();
    int review;
    int color;
    int color2;
    double averageRate=0;
    ArrayList<String> comments = new ArrayList<String>();
    ArrayList<Integer> rates = new ArrayList<Integer>();

    Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        c = bundle.getParcelable("courseName");
        requestReview();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                for(Review r : reviews) {
                    comments.add(r.getComment());
                    rates.add(r.getRate());
                }
                review = reviews.size();
                int total=0;
                for(int i : rates){
                    total+=i;
                }
                averageRate = (double)total/(double)rates.size();
                DecimalFormat df = new DecimalFormat("###.##");
                averageRate = Double.parseDouble(df.format(averageRate));
                totalRate = (DonutProgress) findViewById(R.id.total_rate);
                totalRate.setProgress((int) (averageRate * 20.00));
                totalRate.setTextSize(90f);
                totalRate.setUnfinishedStrokeWidth(50f);

                if(averageRate<3) {
                    color=getResources().getColor(R.color.course_red);
                    color2=getResources().getColor(R.color.course_red_dark);
                }
                else if(averageRate<4){
                    color=getResources().getColor(R.color.comment_head_yellow_light);
                    color2=getResources().getColor(R.color.comment_head_yellow_dark);
                }
                else {
                    color=getResources().getColor(R.color.course_green);
                    color2=getResources().getColor(R.color.course_green_dark);
                }
                LinearLayout linearLayout=(LinearLayout) findViewById(R.id.rate_head);
                linearLayout.setBackgroundColor(color);
                RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.rate_head2);
                relativeLayout.setBackgroundColor(color2);
                totalRate.setTextColor(color);
                totalRate.setUnfinishedStrokeColor(color2);
                totalRate.setFinishedStrokeColor(Color.WHITE);
                totalRate.setFinishedStrokeWidth(50f);
                totalRate.setMax(100);
//        totalRate.setSuffixText("/5");
                TextView rateText = (TextView) findViewById(R.id.textView9);
                rateText.setText("" + averageRate);
                TextView courseText = (TextView) findViewById(R.id.textView6);
                courseText.setText(c.getName());
                TextView reviewText = (TextView) findViewById(R.id.textView8);
                reviewText.setText("" + review);

                adapter = new CommentListAdapter(act, comments, rates, color, color2);
                listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }
        }, 200);






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

    private void requestReview() {
        System.out.println(c.getId());
        ConnectHttp.get("courses/"+c.getId() + "/review", null, new AsyncHttpResponseHandler() {
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
                        Type reviewListType = new TypeToken<ArrayList<Review>>() {
                        }.getType();
                        reviews = gson.fromJson(response, reviewListType);
                        //Student s = new ObjectMapper().readValue(response, Student.class);
                        //showErrorMessage(response);
                        //reviews = (ArrayList<Review>) reviewList;
                        System.out.println(reviews.size());
                        System.out.println("SUCCESS!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println("Error!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("FAIL"+statusCode);
            }
        });
    }
}
