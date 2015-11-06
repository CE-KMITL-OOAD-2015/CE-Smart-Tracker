package com.kaleido.cesmarttracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.kaleido.cesmarttracker.data.Course;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetailEnrollCourseActivity extends AppCompatActivity {
    int color;
    int dark_color = 0;
    int rated;
    Course c;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        Bundle bundle = getIntent().getExtras();
        c = bundle.getParcelable("courseName");
        color = bundle.getInt("color");
        comment = (EditText)findViewById(R.id.comment);

        switch (color){
            case R.color.course_red:
                dark_color = getResources().getColor(R.color.course_red_dark);
                break;
            case R.color.course_yellow:
                dark_color = getResources().getColor(R.color.course_yellow_dark);
                break;
            case R.color.course_blue:
                dark_color = getResources().getColor(R.color.course_blue_dark);
                break;
            case R.color.course_orange:
                dark_color = getResources().getColor(R.color.course_orange_dark);
                break;
            case R.color.course_green:
                dark_color = getResources().getColor(R.color.course_green_dark);
                break;
            case R.color.course_purple:
                dark_color = getResources().getColor(R.color.course_purple_dark);
                break;
            case R.color.course_cyan:
                dark_color = getResources().getColor(R.color.course_cyan_dark);
                break;
            case R.color.course_skyblue:
                dark_color = getResources().getColor(R.color.course_skyblue_dark);
                break;
        }

        //Set Head Color
        LinearLayout head = (LinearLayout)findViewById(R.id.head);
        head.setBackgroundColor(getResources().getColor(color));

        //Set Progress
        DonutProgress progress = (DonutProgress)findViewById(R.id.donut_progress);
        progress.setProgress(c.getStudentAmount());
        progress.setTextColor(Color.WHITE);
        progress.setTextSize(90f);
        progress.setUnfinishedStrokeWidth(50f);
        progress.setUnfinishedStrokeColor(dark_color);
        progress.setFinishedStrokeColor(Color.WHITE);
        progress.setFinishedStrokeWidth(50f);
        progress.setMax(50);
        progress.setSuffixText("/50");

        //Set Course Name
        TextView courseName = (TextView)findViewById(R.id.nameCourse);
        courseName.setText(c.getName());
        courseName.setTextSize(18f);
        courseName.setTextColor(Color.DKGRAY);
        courseName.setTextAlignment(1);

        //Set Description Course
        final TextView descriptionCourse = (TextView)findViewById(R.id.descriptionCourse);
        descriptionCourse.setText(c.getCategory());
        descriptionCourse.setTextColor(Color.WHITE);
        descriptionCourse.setBackgroundResource(R.drawable.rounded_corner);
        final GradientDrawable drawable = (GradientDrawable) descriptionCourse.getBackground();
        drawable.setColor(dark_color);


        //Set Rate&Comment button
        ImageView rate = (ImageView)findViewById(R.id.rateCommentButton);
        rate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addReview(rated,comment.getText().toString());
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                rated=0;
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.rate_comment_dialog, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                final Button r1,r2,r3,r4,r5;

                comment = (EditText)dialogLayout.findViewById(R.id.comment);

                r1 = (Button)dialog.findViewById(R.id.rate1);
                r2 = (Button)dialog.findViewById(R.id.rate2);
                r3 = (Button)dialog.findViewById(R.id.rate3);
                r4 = (Button)dialog.findViewById(R.id.rate4);
                r5 = (Button)dialog.findViewById(R.id.rate5);

                final ArrayList<GradientDrawable> drawables = new ArrayList<GradientDrawable>();
                drawables.add((GradientDrawable) r1.getBackground());
                drawables.add((GradientDrawable) r2.getBackground());
                drawables.add((GradientDrawable) r3.getBackground());
                drawables.add((GradientDrawable) r4.getBackground());
                drawables.add((GradientDrawable) r5.getBackground());
                for(int i=0;i<5;i++) {
                    drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                }
                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated=1;
                        for(int i=0;i<5;i++) {
                            if(i==0) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            }
                            else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated=2;
                        for(int i=0;i<5;i++) {
                            if(i==1) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            }
                            else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated=3;
                        for(int i=0;i<5;i++) {
                            if(i==2) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            }
                            else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }

                    }
                });


                r4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated=4;
                        for(int i=0;i<5;i++) {
                            if(i==3) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            }
                            else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated=5;
                        for(int i=0;i<5;i++) {
                            if(i==4) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            }
                            else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.course_head);
                        float imageWidthInPX = (float) image.getWidth();
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));
                        image.setLayoutParams(layoutParams);
                    }
                });

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showErrorMessage(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void addReview(int rate, String comment) {
        RequestParams params = new RequestParams();
        params.put("rate",rate);
        params.put("comment",comment);
        ConnectHttp.post("courses/" + c.getId() + "/review/add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showErrorMessage("SUCCESS!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showErrorMessage("FAIL : "+statusCode);
            }
        });
    }
}
