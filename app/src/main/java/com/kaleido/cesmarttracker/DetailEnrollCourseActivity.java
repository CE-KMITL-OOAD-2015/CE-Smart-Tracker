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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Section;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetailEnrollCourseActivity extends AppCompatActivity{
    //TODO get student
    Test test=new Test();
    Student s1 = null;
    UserLocalStore userLocalStore;
    //-------
    int color;
    int dark_color = 0;
    int rated;
    Course c;
    Section selectedSection = null;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        userLocalStore = new UserLocalStore(getApplicationContext());
        s1 = userLocalStore.getStudentData();
        //  c= s1.getCurrentCourses().get(0);
        Bundle bundle = getIntent().getExtras();
        //  color= R.color.course_red;
        c = bundle.getParcelable("courseName");
        color = bundle.getInt("color");
        comment = (EditText) findViewById(R.id.comment);
//        switch (color) {
//            case R.color.course_red:
//                dark_color = getResources().getColor(R.color.course_red_dark);
//                break;
//            case R.color.course_yellow:
//                dark_color = getResources().getColor(R.color.course_yellow_dark);
//                break;
//            case R.color.course_blue:
//                dark_color = getResources().getColor(R.color.course_blue_dark);
//                break;
//            case R.color.course_orange:
//                dark_color = getResources().getColor(R.color.course_orange_dark);
//                break;
//            case R.color.course_green:
//                dark_color = getResources().getColor(R.color.course_green_dark);
//                break;
//            case R.color.course_purple:
//                dark_color = getResources().getColor(R.color.course_purple_dark);
//                break;
//            case R.color.course_cyan:
//                dark_color = getResources().getColor(R.color.course_cyan_dark);
//                break;
//            case R.color.course_skyblue:
//                dark_color = getResources().getColor(R.color.course_skyblue_dark);
//                break;
//        }
        switch (color){
            case R.color.course_red1:
                dark_color = getResources().getColor(R.color.course_red1_dark);
                break;
            case R.color.course_orange1:
                dark_color = getResources().getColor(R.color.course_orange1_dark);
                break;
            case R.color.course_green1:
                dark_color = getResources().getColor(R.color.course_green1_dark);
                break;
            case R.color.course_green2:
                dark_color = getResources().getColor(R.color.course_green2_dark);
                break;
            case R.color.course_skyblue1:
                dark_color = getResources().getColor(R.color.course_skyblue1_dark);
                break;
            case R.color.course_blue1:
                dark_color = getResources().getColor(R.color.course_blue1_dark);
                break;
            case R.color.course_light_purple1:
                dark_color = getResources().getColor(R.color.course_light_purple1_dark);
                break;
            case R.color.course_purple1:
                dark_color = getResources().getColor(R.color.course_purple1_dark);
                break;
        }

        int takenSeat = 0, maxSeat = 0;
        for(Section sec : c.getSections()) {
            takenSeat += sec.getTakenSeat();
            maxSeat += sec.getMaxSeat();
        }

        //Set Head Color
        LinearLayout head = (LinearLayout) findViewById(R.id.head);
        head.setBackgroundColor(getResources().getColor(color));

        //Set Progress
        DonutProgress progress = (DonutProgress) findViewById(R.id.donut_progress);
        progress.setProgress(takenSeat);
        progress.setTextColor(Color.WHITE);
        progress.setTextSize(90f);
        progress.setUnfinishedStrokeWidth(50f);
        progress.setUnfinishedStrokeColor(dark_color);
        progress.setFinishedStrokeColor(Color.WHITE);
        progress.setFinishedStrokeWidth(50f);
        progress.setMax(maxSeat);
        progress.setSuffixText("/"+maxSeat);

        //Set Course Name
        TextView courseName = (TextView) findViewById(R.id.nameCourse);
        courseName.setText(c.getName());
        courseName.setTextSize(18f);
        courseName.setTextColor(Color.DKGRAY);
        courseName.setTextAlignment(1);

        //Set Description Course
        final TextView descriptionCourse = (TextView) findViewById(R.id.descriptionCourse);
        descriptionCourse.setText(c.getCategory());
        descriptionCourse.setTextColor(Color.WHITE);
        descriptionCourse.setBackgroundResource(R.drawable.rounded_corner);
        final GradientDrawable drawable = (GradientDrawable) descriptionCourse.getBackground();
        drawable.setColor(dark_color);

        System.out.println(c.getSections().get(0).getTakenSeat());
        //Set Rate&Comment button
        ImageView rate = (ImageView) findViewById(R.id.rateCommentButton);
        rateCommentDialog(rate);

        //set DetailCourse button
        ImageView detailCourse = (ImageView) findViewById(R.id.eventButton);
        detailCourseDialog(detailCourse);

        boolean canDrop = false;
        for (Course course : s1.getSchedule().getCurrentCourses()) {
            if (c.getId().contentEquals(course.getId())) {
                canDrop = true;
            }
        }

        //set enrollCourse button
        ImageView enrollCourse = (ImageView)findViewById(R.id.enrollButton);
        if(!canDrop) {
            enrollCourseDialog(enrollCourse);
        }
        else{
            //TODO change button
        }
        //set withdrawCourse button
        ImageView withdrawCourse = (ImageView)findViewById(R.id.dropButton);
        if(canDrop) {
            withdrawCourseDialog(withdrawCourse);
        }
        else{
            //TODO change button
        }

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
        params.put("rate", rate);
        params.put("comment", comment);
        ConnectHttp.post("courses/" + c.getId() + "/review/add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showErrorMessage("SUCCESS!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showErrorMessage("FAIL : " + statusCode);
            }
        });
    }

    private void rateCommentDialog(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);

//                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        addReview(rated, comment.getText().toString());
//                    }
//                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
                rated = 0;
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.rate_comment_dialog, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                Button button1 = (Button) dialog.findViewById(R.id.button5);
                Button button2 = (Button) dialog.findViewById(R.id.button6);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addReview(rated, comment.getText().toString());
                        dialog.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); //to dismiss the Dialog
                    }
                });
                final TextView r1, r2, r3, r4, r5;

                comment = (EditText) dialogLayout.findViewById(R.id.comment);

                r1 = (TextView) dialog.findViewById(R.id.rate1);
                r2 = (TextView) dialog.findViewById(R.id.rate2);
                r3 = (TextView) dialog.findViewById(R.id.rate3);
                r4 = (TextView) dialog.findViewById(R.id.rate4);
                r5 = (TextView) dialog.findViewById(R.id.rate5);

                final ArrayList<GradientDrawable> drawables = new ArrayList<GradientDrawable>();
                drawables.add((GradientDrawable) r1.getBackground());
                drawables.add((GradientDrawable) r2.getBackground());
                drawables.add((GradientDrawable) r3.getBackground());
                drawables.add((GradientDrawable) r4.getBackground());
                drawables.add((GradientDrawable) r5.getBackground());
                for (int i = 0; i < 5; i++) {
                    drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                }
                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated = 1;
                        for (int i = 0; i < 5; i++) {
                            if (i == 0) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            } else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated = 2;
                        for (int i = 0; i < 5; i++) {
                            if (i == 1) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            } else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated = 3;
                        for (int i = 0; i < 5; i++) {
                            if (i == 2) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            } else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }

                    }
                });


                r4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated = 4;
                        for (int i = 0; i < 5; i++) {
                            if (i == 3) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            } else {
                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
                            }
                        }
                    }
                });


                r5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rated = 5;
                        for (int i = 0; i < 5; i++) {
                            if (i == 4) {
                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
                            } else {
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
    private void detailCourseDialog(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
                final AlertDialog dialog2 = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.detail_course_dialog, null);
                dialog2.setView(dialogLayout);
                dialog2.setCanceledOnTouchOutside(true);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.show();
                final TextView r1,r2,r3,r4,r5;
                r1 = (TextView)dialog2.findViewById(R.id.textView19);
                r1.setText(c.getName());
                r2 = (TextView)dialog2.findViewById(R.id.textView18);
                r2.setText("" + c.getCredit());
                LinearLayout sectionLayout = (LinearLayout) dialog2.findViewById(R.id.sectionLayout);
                for(Section s:c.getSections()) {
                    LinearLayout linearLayout = new LinearLayout(dialog2.getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    TextView sec = new TextView(dialog2.getContext());
                    sec.setTextSize(14);
                    sec.setText("" + s.getId() + " : " + s.getPeriod().toString());
                    TextView seat = new TextView(dialog2.getContext());
                    seat.setTextSize(14);
                    seat.setText("("+s.getTakenSeat()+")");
                    seat.setTextColor(getResources().getColor(R.color.red));
                    if(s.getTakenSeat()!=0){
                        seat.setTextColor(getResources().getColor(R.color.green));
                    }
                    linearLayout.addView(sec);
                    linearLayout.addView(seat);
                    sectionLayout.addView(linearLayout);
                }
                r3 = (TextView)dialog2.findViewById(R.id.textView23);
                //TODO get Teacher
                ArrayList<Teacher> teachers = new ArrayList<>(test.getTeachers());
                //----------
                String teacherName="";
                for(Teacher t:teachers){
                    teacherName+= t.getName()+"\n";
                }
                r3.setText(teacherName);

                dialog2.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog2.findViewById(R.id.goProDialogImage);
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
    private void enrollCourseDialog(final View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
                final AlertDialog dialog3 = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.enroll_dialog, null);
                dialog3.setView(dialogLayout);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog3.show();
                Button button1 = (Button) dialog3.findViewById(R.id.button3);
                Button button2 = (Button) dialog3.findViewById(R.id.button4);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Enroll Course c
                        final AlertDialog.Builder alert = new AlertDialog.Builder(DetailEnrollCourseActivity.this);

                        RequestParams params = new RequestParams();
                        params.put("courseId",c.getId());
                        params.put("secId",selectedSection.getId());
                        System.out.println(c.getName()+" : "+selectedSection.getId() +" : "+s1.getId());
                        ConnectHttp.get("students/"+s1.getId() + "/enroll", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String s = "";
                                for (int i = 0; i < responseBody.length; i++)
                                    s += (char) responseBody[i];
                                boolean res = Boolean.parseBoolean(s);
                                if(res == true) {
                                    alert.setMessage("You've been registered to the course.");
                                    alert.setPositiveButton("OK", null);
                                    alert.show();
                                    userLocalStore.updateStudent();
                                    finish(); //TODO: finish?
                                }
                                else {
                                    alert.setMessage("Error! Cannot register.");
                                    alert.setPositiveButton("OK", null);
                                    alert.show();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                alert.setMessage("Error code : "+statusCode);
                                alert.setPositiveButton("OK", null);
                                alert.show();
                            }
                        });
                        // example s1.enroll(c);
//                        AlertDialog.Builder alert = new AlertDialog.Builder(DetailEnrollCourseActivity.this
                        dialog3.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss(); //to dismiss the Dialog
                    }
                });
                Spinner spinner= (Spinner)dialog3.findViewById(R.id.spinner);
                ArrayList<String> sections = new ArrayList<String>();
                for(Section s: c.getSections())
                    sections.add("   Section "+s.getId() + " : " + s.getPeriod().toString());
                ArrayAdapter<String> list = new ArrayAdapter<String>(dialog3.getContext(), android.R.layout.simple_dropdown_item_1line,sections);
                spinner.setAdapter(list);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selectedSection =c.getSections().get(position);
                        TextView t1 = (TextView) dialog3.findViewById(R.id.textView32);
                        t1.setText(selectedSection.getTakenSeat() + "/" + selectedSection.getMaxSeat());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                dialog3.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog3.findViewById(R.id.goProDialogImage);
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
    private void withdrawCourseDialog(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
                final AlertDialog dialog4 = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.withdraw_dialog, null);
                dialog4.setView(dialogLayout);
                dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog4.show();
                Button button1 = (Button) dialog4.findViewById(R.id.button);
                Button button2 = (Button) dialog4.findViewById(R.id.button2);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(DetailEnrollCourseActivity.this);

                        RequestParams params = new RequestParams("courseId",c.getId());
                        System.out.println(c.getId()+" : "+s1.getId());
                        ConnectHttp.get("students/" + s1.getId() + "/drop", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String s = "";
                                for (int i = 0; i < responseBody.length; i++)
                                    s += (char) responseBody[i];
                                boolean res = Boolean.parseBoolean(s);
                                if (res == true) {
                                    alert.setMessage("You've been withdrawn from the course.");
                                    alert.setPositiveButton("OK", null);
                                    alert.show();
                                    userLocalStore.updateStudent();
                                    finish();
                                } else {
                                    alert.setMessage("Error! Cannot drop.");
                                    alert.setPositiveButton("OK", null);
                                    alert.show();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                alert.setMessage("Error code : " + statusCode);
                                alert.setPositiveButton("OK", null);
                                alert.show();
                            }
                        });
                        dialog4.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss(); //to dismiss the Dialog
                    }
                });
                TextView t1,t2,t3,t4,t5;
                t1 = (TextView)dialog4.findViewById(R.id.textView26);
                t1.setText(c.getName());
                t2 = (TextView)dialog4.findViewById(R.id.textView28);
                Section section=null;
                for(int i=0;i<s1.getSchedule().getCurrentCourses().size();i++){
                    if(c.getId().contentEquals(s1.getSchedule().getCurrentCourses().get(i).getId())){
                        section=s1.getSchedule().getCurrentSections().get(i);
                    }
                }
                t2.setText(""+section.getId()+" : "+section.getPeriod().toString());
                t3 = (TextView)dialog4.findViewById(R.id.textView30);
                t3.setText("" + section.getTakenSeat() + "/" + section.getMaxSeat());
                dialog4.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog4.findViewById(R.id.goProDialogImage);
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
}


//package com.kaleido.cesmarttracker;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.GradientDrawable;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.github.lzyzsd.circleprogress.DonutProgress;
//import com.kaleido.cesmarttracker.data.Course;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import java.util.ArrayList;
//
//import cz.msebera.android.httpclient.Header;
//
//public class DetailEnrollCourseActivity extends AppCompatActivity {
//    int color;
//    int dark_color = 0;
//    int rated;
//    Course c;
//    EditText comment;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_course);
//        Bundle bundle = getIntent().getExtras();
//        c = bundle.getParcelable("courseName");
//        color = bundle.getInt("color");
//        comment = (EditText)findViewById(R.id.comment);
//
//        switch (color){
//            case R.color.course_red:
//                dark_color = getResources().getColor(R.color.course_red_dark);
//                break;
//            case R.color.course_yellow:
//                dark_color = getResources().getColor(R.color.course_yellow_dark);
//                break;
//            case R.color.course_blue:
//                dark_color = getResources().getColor(R.color.course_blue_dark);
//                break;
//            case R.color.course_orange:
//                dark_color = getResources().getColor(R.color.course_orange_dark);
//                break;
//            case R.color.course_green:
//                dark_color = getResources().getColor(R.color.course_green_dark);
//                break;
//            case R.color.course_purple:
//                dark_color = getResources().getColor(R.color.course_purple_dark);
//                break;
//            case R.color.course_cyan:
//                dark_color = getResources().getColor(R.color.course_cyan_dark);
//                break;
//            case R.color.course_skyblue:
//                dark_color = getResources().getColor(R.color.course_skyblue_dark);
//                break;
//        }
//
//        //Set Head Color
//        LinearLayout head = (LinearLayout)findViewById(R.id.head);
//        head.setBackgroundColor(getResources().getColor(color));
//
//        //Set Progress
//        DonutProgress progress = (DonutProgress)findViewById(R.id.donut_progress);
//        progress.setProgress(c.getStudentAmount());
//        progress.setTextColor(Color.WHITE);
//        progress.setTextSize(90f);
//        progress.setUnfinishedStrokeWidth(50f);
//        progress.setUnfinishedStrokeColor(dark_color);
//        progress.setFinishedStrokeColor(Color.WHITE);
//        progress.setFinishedStrokeWidth(50f);
//        progress.setMax(50);
//        progress.setSuffixText("/50");
//
//        //Set Course Name
//        TextView courseName = (TextView)findViewById(R.id.nameCourse);
//        courseName.setText(c.getName());
//        courseName.setTextSize(18f);
//        courseName.setTextColor(Color.DKGRAY);
//        courseName.setTextAlignment(1);
//
//        //Set Description Course
//        final TextView descriptionCourse = (TextView)findViewById(R.id.descriptionCourse);
//        descriptionCourse.setText(c.getCategory());
//        descriptionCourse.setTextColor(Color.WHITE);
//        descriptionCourse.setBackgroundResource(R.drawable.rounded_corner);
//        final GradientDrawable drawable = (GradientDrawable) descriptionCourse.getBackground();
//        drawable.setColor(dark_color);
//
//        System.out.println(c.getSections().get(0).getTakenSeat());
//        //Set Rate&Comment button
//        ImageView rate = (ImageView)findViewById(R.id.rateCommentButton);
//        rate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
//
//                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        addReview(rated,comment.getText().toString());
//                    }
//                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                rated=0;
//                final AlertDialog dialog = builder.create();
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogLayout = inflater.inflate(R.layout.rate_comment_dialog, null);
//                dialog.setView(dialogLayout);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.show();
//                final Button r1,r2,r3,r4,r5;
//
//                comment = (EditText)dialogLayout.findViewById(R.id.comment);
//
//                r1 = (Button)dialog.findViewById(R.id.rate1);
//                r2 = (Button)dialog.findViewById(R.id.rate2);
//                r3 = (Button)dialog.findViewById(R.id.rate3);
//                r4 = (Button)dialog.findViewById(R.id.rate4);
//                r5 = (Button)dialog.findViewById(R.id.rate5);
//
//                final ArrayList<GradientDrawable> drawables = new ArrayList<GradientDrawable>();
//                drawables.add((GradientDrawable) r1.getBackground());
//                drawables.add((GradientDrawable) r2.getBackground());
//                drawables.add((GradientDrawable) r3.getBackground());
//                drawables.add((GradientDrawable) r4.getBackground());
//                drawables.add((GradientDrawable) r5.getBackground());
//                for(int i=0;i<5;i++) {
//                    drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                }
//                r1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rated=1;
//                        for(int i=0;i<5;i++) {
//                            if(i==0) {
//                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
//                            }
//                            else {
//                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                            }
//                        }
//                    }
//                });
//
//
//                r2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rated=2;
//                        for(int i=0;i<5;i++) {
//                            if(i==1) {
//                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
//                            }
//                            else {
//                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                            }
//                        }
//                    }
//                });
//
//
//                r3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rated=3;
//                        for(int i=0;i<5;i++) {
//                            if(i==2) {
//                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
//                            }
//                            else {
//                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                            }
//                        }
//
//                    }
//                });
//
//
//                r4.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rated=4;
//                        for(int i=0;i<5;i++) {
//                            if(i==3) {
//                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
//                            }
//                            else {
//                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                            }
//                        }
//                    }
//                });
//
//
//                r5.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rated=5;
//                        for(int i=0;i<5;i++) {
//                            if(i==4) {
//                                drawables.get(i).setColor(getResources().getColor(R.color.amber));
//                            }
//                            else {
//                                drawables.get(i).setColor(getResources().getColor(R.color.divider_grey));
//                            }
//                        }
//                    }
//                });
//
//                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface d) {
//                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
//                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.course_head);
//                        float imageWidthInPX = (float) image.getWidth();
//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
//                                Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));
//                        image.setLayoutParams(layoutParams);
//                    }
//                });
//
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail_course, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch(id){
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showErrorMessage(String msg) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailEnrollCourseActivity.this);
//        dialogBuilder.setMessage(msg);
//        dialogBuilder.setPositiveButton("Ok", null);
//        dialogBuilder.show();
//    }
//
//    private void addReview(int rate, String comment) {
//        RequestParams params = new RequestParams();
//        params.put("rate",rate);
//        params.put("comment",comment);
//        ConnectHttp.post("courses/" + c.getId() + "/review/add", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                showErrorMessage("SUCCESS!");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                showErrorMessage("FAIL : "+statusCode);
//            }
//        });
//    }
//}
