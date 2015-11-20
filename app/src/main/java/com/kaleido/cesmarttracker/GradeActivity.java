package com.kaleido.cesmarttracker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GradeActivity extends AppCompatActivity {

    private TextView course;
    private Button autoGrade;
    private Button criteria;
    private ScrollView scroll;
    private String gradee;
    private ArrayList<String> c1;
    private String gradeForSend;
    private ImageButton backButton;

    RubberLoaderView rubberLoaderView;
    Dialog dialog;

    private ArrayList<String> tempGrade = new ArrayList<String>();
    ArrayAdapter<String> list;
    //test
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Double> score1;
    private ArrayList<Integer> criteriaSet;
    private ArrayList<Student> studentsForSend;
    private ArrayList<Double> listGradeForSend;
    private Course c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade2);
        for(int i=0; i<8; i++)
            tempGrade.add("");
        showLoadingDialog();
        Bundle bundle = getIntent().getExtras();
        c = bundle.getParcelable("course");
        autoGrade = (Button) findViewById(R.id.autoGrade);
        criteria = (Button) findViewById(R.id.criteria);
        c1 = new ArrayList<String>();
        score1 =  new ArrayList<Double>();
        criteriaSet = new ArrayList<Integer>();
        studentsForSend = new ArrayList<Student>();
        listGradeForSend = new ArrayList<Double>();

        backButton = (ImageButton)findViewById(R.id.imageButton);
        setWidgetEventListener();

        setUp();
        //refresh();
        getAllStudent();
        setButton();

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

    public void setUp(){
        criteriaSet.add(80);
        criteriaSet.add(75);
        criteriaSet.add(70);
        criteriaSet.add(65);
        criteriaSet.add(60);
        criteriaSet.add(55);
        criteriaSet.add(50);
        criteriaSet.add(49);

        Student s1 = new Student("56010555","Thana1");
        Student s2 = new Student("56010550","Thanu2");
        Student s3 = new Student("56010455","Thand3");
        Student s4 = new Student("56010110","Thana4");
        Student s5 = new Student("56010555","Thaadn5");
        Student s6 = new Student("56010550","Thadanuay6");
        // Student s7 = new Student("56010555","Thadna7");
        // Student s8 = new Student("56010550","Thanduay8");

        score1.add(60.0);
        score1.add(61.0);
        score1.add(65.1);
        score1.add(97.0);
        score1.add(60.0);
        score1.add(54.0);
        //score1.add(60.0);
        //score1.add(32.0);

        //gradee = "A";

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.add(s6);
        //students.add(s7);
        //students.add(s8);
    }

    public void setButton(){

        final Context context = this;

        autoGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO: kuy
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_auto_grading);
                dialog.setTitle("Title...");

                Button confirm = (Button) dialog.findViewById(R.id.confirmButton);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        for(int i =0 ; i< students.size() ; i++){
                            TextView showGrade;
                            showGrade = (TextView) findViewById(i);
                            studentsForSend.add(students.get(i));
                            listGradeForSend.add(checkGrade(showGrade.getText().toString()));
                        }
                        students.clear();
                        refresh();

                        dialog.dismiss();

                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancelButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        criteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_criteria_setup);
                dialog.setTitle("Title...");

                final EditText minA = (EditText) dialog.findViewById(R.id.minA);
                final EditText minBPlus = (EditText) dialog.findViewById(R.id.minBPlus);
                final EditText minB = (EditText) dialog.findViewById(R.id.minB);
                final EditText minCPlus = (EditText) dialog.findViewById(R.id.minCPlus);
                final EditText minC = (EditText) dialog.findViewById(R.id.minC);
                final EditText minDPlus = (EditText) dialog.findViewById(R.id.minDPlus);
                final EditText minD = (EditText) dialog.findViewById(R.id.minD);
                final EditText minF = (EditText) dialog.findViewById(R.id.minF);
                final TextView warningmsg = (TextView) dialog.findViewById(R.id.warningmsg);

                minA.setText(tempGrade.get(0));
                minBPlus.setText(tempGrade.get(1));
                minB.setText(tempGrade.get(2));
                minCPlus.setText(tempGrade.get(3));
                minC.setText(tempGrade.get(4));
                minDPlus.setText(tempGrade.get(5));
                minD.setText(tempGrade.get(6));
                minF.setText(tempGrade.get(7));

                Button confirm = (Button) dialog.findViewById(R.id.confirmButton);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        criteriaSet.clear();

                        if (minA.getText().toString().matches("") || minBPlus.getText().toString().matches("") || minB.getText().toString().matches("") || minCPlus.getText().toString().matches("") || minC.getText().toString().matches("") || minDPlus.getText().toString().matches("") || minD.getText().toString().matches("") || minF.getText().toString().matches(""))
                            warningmsg.setText("All fields must be filled.");
                        else {
                            criteriaSet.add(Integer.parseInt(minA.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minBPlus.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minB.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minCPlus.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minC.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minDPlus.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minD.getText().toString()));
                            criteriaSet.add(Integer.parseInt(minF.getText().toString()));
                            tempGrade.set(0, minA.getText().toString());
                            tempGrade.set(1, minBPlus.getText().toString());
                            tempGrade.set(2, minB.getText().toString());
                            tempGrade.set(3, minCPlus.getText().toString());
                            tempGrade.set(4, minC.getText().toString());
                            tempGrade.set(5, minDPlus.getText().toString());
                            tempGrade.set(6, minD.getText().toString());
                            tempGrade.set(7, minF.getText().toString());

                            for (int i = 0; i < students.size(); i++) {
                                TextView showGrade = (TextView) findViewById(i);
                                if (score1.get(i) >= criteriaSet.get(0)) {
                                    showGrade.setBackgroundResource(R.drawable.green_circle);
                                    showGrade.setText("A");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(1)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("B+");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(2)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("B");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(3)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("C+");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(4)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("C");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(5)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("D+");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) >= criteriaSet.get(6)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("D");
                                    showGrade.setGravity(Gravity.CENTER);
                                } else if (score1.get(i) < criteriaSet.get(7)) {
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    showGrade.setText("F");
                                    showGrade.setGravity(Gravity.CENTER);
                                }
                            }


                            dialog.dismiss();
                        }
                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancelButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });
    }

    public void matchID(){
        course = (TextView) findViewById(R.id.course);

        //getCourseName naja
        course.setText(c.getName());

        autoGrade = (Button) findViewById(R.id.autoGrade);
        criteria = (Button) findViewById(R.id.criteria);

        scroll = (ScrollView) findViewById(R.id.scrollView2);
    }

    public void refresh(){

        addGrade();
        list = new ArrayAdapter<> (this, android.R.layout.simple_dropdown_item_1line, c1);

        final Context context = this;


        matchID();
        setting();
//        LinearLayout layoutInScroll = new LinearLayout(this);
//        layoutInScroll.setOrientation(LinearLayout.VERTICAL);
//        layoutInScroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        for(int i = 0 ; i < students.size() ; i++){
//            LinearLayout layout_buntad = new LinearLayout(this);
//            layout_buntad.setOrientation(LinearLayout.HORIZONTAL);
//            layout_buntad.setPadding(0, 10, 0, 0);
//            layout_buntad.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            ImageView image = new ImageView(this);
//
//            image.setImageResource(R.drawable.ic_calculator);
//            image.setPadding(20,0,20,0);
//
//            LinearLayout nameAndID = new LinearLayout(this);
//            nameAndID.setOrientation(LinearLayout.VERTICAL);
//
//            TextView name = new TextView(this);
//            name.setTextSize(15);
//            name.setText("" + students.get(i).getName());
//
//            LinearLayout idAndScore = new LinearLayout(this);
//            idAndScore.setOrientation(LinearLayout.HORIZONTAL);
//
//            TextView id = new TextView(this);
//            id.setTextSize(12);
//            id.setText(students.get(i).getId());
//
//            TextView score = new TextView(this);
//            score.setPadding(30, 0, 0, 0);
//            score.setTextSize(12);
//            //getscore
//            score.setText(""+score1.get(i)+"/100" );
//            idAndScore.addView(id);
//            idAndScore.addView(score);
//
//            nameAndID.addView(name);
//            nameAndID.addView(idAndScore);
//
//
//
//
//            FrameLayout relay = new FrameLayout(this);
//            relay.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
//
//            FrameLayout frame = new FrameLayout(this);
//
//
//
//            LinearLayout grade = new LinearLayout(this);
//
//
//
//
//
//            grade.setOrientation(LinearLayout.HORIZONTAL);
//            grade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            grade.setGravity(Gravity.RIGHT);
//
//
//
//
//
//            TextView showGrade = new TextView(this);
//
//            showGrade.setTextSize(15);
//            showGrade.setId(i);
//
//            if(score1.get(i)>=criteriaSet.get(0)){
//                showGrade.setBackgroundResource(R.drawable.green_circle);
//                showGrade.setText("A");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(1)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("B+");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(2)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("B");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(3)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("C+");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(4)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("C");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(5)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("D+");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)>=criteriaSet.get(6)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("D");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//            else if(score1.get(i)<criteriaSet.get(7)){
//                showGrade.setBackgroundResource(R.drawable.grey_circle);
//                showGrade.setText("F");
//                showGrade.setGravity(Gravity.CENTER);
//            }
//
//
//            Space space = new Space(this);
//            space.setMinimumWidth(20);
//
//            TextView gradeButton = new TextView(this);
//
//            gradeButton.setGravity(Gravity.CENTER);
//            gradeButton.setBackgroundResource(R.drawable.pink_circle);
//
//            gradeButton.setTextSize(15);
//
//            gradeButton.setText("Grade");
//
//            final int finalI = i;
//            final int finalI1 = i;
//            final int finalI2 = i;
//            final int finalI3 = i;
//            gradeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Dialog dialog = new Dialog(context);
//                    dialog.setContentView(R.layout.activity_grading);
//                    dialog.setTitle("Title...");
//
//                    TextView name = (TextView) dialog.findViewById((R.id.nameOfStudent));
//                    Spinner grade = (Spinner) dialog.findViewById(R.id.spinner);
//                    TextView score = (TextView) dialog.findViewById(R.id.score);
//                    name.setText(""+students.get(finalI).getName());
//                    score.setText(""+score1.get(finalI)+"/100");
//                    Button confirm;
//                    Button cancel;
//                    Student s1;
//
//
//
//                    addGrade();
//
//                    grade.setAdapter(list);
//                    grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                            gradeForSend = ((TextView) view).getText().toString();
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//
//
//                    confirm = (Button) dialog.findViewById(R.id.confirmButton);
//                    confirm.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            studentsForSend.add(students.get(finalI1));
//                            listGradeForSend.add(checkGrade(gradeForSend));
//                            students.remove(finalI3);
//                            score1.remove(finalI3);
//                            refresh();
//                            dialog.dismiss();
//                        }
//                    });
//
//                    cancel = (Button) dialog.findViewById(R.id.cancelButton);
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();
//                }
//            });
//
//
//            grade.addView(showGrade);
//            grade.addView(space);
//            grade.addView(gradeButton);
//
//            frame.addView(grade);
//            frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.RIGHT));
//            frame.setPadding(0,0,20,0);
//
//
//            relay.addView(frame);
//
//            layout_buntad.addView(image);
//            layout_buntad.addView(nameAndID);
//            layout_buntad.addView(relay);
//
//            layoutInScroll.addView(layout_buntad);
//        }
//        scroll.removeAllViews();
//        scroll.addView(layoutInScroll);

    }

    public void addGrade(){
        c1.clear();
        c1.add("A");
        c1.add("B+");
        c1.add("B");
        c1.add("C+");
        c1.add("C");
        c1.add("D+");
        c1.add("D");
        c1.add("F");
    }

    public Double checkGrade(String grade){
        if(grade=="A"){
            return 4.00;
        }
        else if(grade=="B+"){
            return 3.50;
        }
        else if(grade == "B"){
            return 3.00;
        }else if(grade == "C+"){
            return 2.50;
        }else if(grade == "C"){
            return 2.00;
        }
        else if(grade == "D+"){
            return 1.50;
        }
        else if(grade == "D"){
            return 1.00;
        }
        else if(grade == "F"){
            return 0.00;
        }
        return 0.0000;
    }

    private void setWidgetEventListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAllStudent() {

        ConnectHttp.get("courses/"+c.getId()+"/students", null, new AsyncHttpResponseHandler() {
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
                        Type studentListType = new TypeToken<List<Student>>() {}.getType();
                        List<Student> studentList = gson.fromJson(response, studentListType);
                        //Student s = new ObjectMapper().readValue(response, Student.class);
                        //showErrorMessage(response);
                        students = (ArrayList<Student>) studentList;
                        for (Student s : students)
                            System.out.println(s.getName());
                        System.out.println("SUCCESS!");
                        refresh();
                        stopLoadingDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    //showErrorMessage("Error!");
                    System.out.println("Error!");
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

    private void setting() {
        final Context context = this;

        LinearLayout layoutInScroll = new LinearLayout(this);
        layoutInScroll.setOrientation(LinearLayout.VERTICAL);
        layoutInScroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for(int i = 0 ; i < students.size() ; i++){
            LinearLayout layout_buntad = new LinearLayout(this);

            //TODO gu add function here
            //LinearLayout layout_buntad = new LinearLayout(this);
            final int iNaja = i;
            layout_buntad.setId(iNaja);
            if(iNaja%2==0){
                layout_buntad.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }


            layout_buntad.setOrientation(LinearLayout.HORIZONTAL);
            layout_buntad.setPadding(0, 10, 0, 0);
            layout_buntad.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            ImageView image = new ImageView(this);

            image.setImageResource(R.drawable.ic_account_circle_white_48dp);
            image.setColorFilter(Color.argb(255,103,103,103));
            //image.setPadding(20,0,20,0);
            image.setPadding(80,0,20,0);

            LinearLayout nameAndID = new LinearLayout(this);
            nameAndID.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(this);
            name.setTextSize(15);
            name.setText("" + students.get(i).getName());

            LinearLayout idAndScore = new LinearLayout(this);
            idAndScore.setOrientation(LinearLayout.HORIZONTAL);

            TextView id = new TextView(this);
            id.setTextSize(12);
            id.setText(students.get(i).getId());

            TextView score = new TextView(this);
            score.setPadding(30, 0, 0, 0);
            score.setTextSize(12);
            //getscore
            score.setText(""+score1.get(i)+"/100" );
            idAndScore.addView(id);
            idAndScore.addView(score);

            nameAndID.addView(name);
            nameAndID.addView(idAndScore);




            FrameLayout relay = new FrameLayout(this);
            relay.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            FrameLayout frame = new FrameLayout(this);



            LinearLayout grade = new LinearLayout(this);





            grade.setOrientation(LinearLayout.HORIZONTAL);
            grade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            grade.setGravity(Gravity.RIGHT);
            grade.setVerticalGravity(Gravity.CENTER_VERTICAL);





            TextView showGrade = new TextView(this);

            showGrade.setTextSize(15);
            showGrade.setId(i);
            showGrade.setTextColor(Color.parseColor("#FFFFFF"));
            if(score1.get(i)>=criteriaSet.get(0)){
                showGrade.setBackgroundResource(R.drawable.green_circle);
                showGrade.setText("A");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(1)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("B+");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(2)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("B");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(3)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("C+");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(4)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("C");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(5)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("D+");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)>=criteriaSet.get(6)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("D");
                showGrade.setGravity(Gravity.CENTER);
            }
            else if(score1.get(i)<criteriaSet.get(7)){
                showGrade.setBackgroundResource(R.drawable.grey_circle);
                showGrade.setText("F");
                showGrade.setGravity(Gravity.CENTER);
            }


            Space space = new Space(this);
            space.setMinimumWidth(20);

            TextView gradeButton = new TextView(this);

            gradeButton.setGravity(Gravity.CENTER);
            gradeButton.setBackgroundResource(R.drawable.pink_circle);

            gradeButton.setTextSize(12);
            gradeButton.setTextColor(Color.parseColor("#FFFFFF"));
            gradeButton.setText("grade");

            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            final int finalI3 = i;
            gradeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.activity_grading);
                    dialog.setTitle("Title...");

                    addGrade();

                    TextView name = (TextView) dialog.findViewById((R.id.nameOfStudent));
                    Spinner grade = (Spinner) dialog.findViewById(R.id.spinner);
                    TextView score = (TextView) dialog.findViewById(R.id.score);
                    name.setText(""+students.get(finalI).getName());
                    score.setText(""+score1.get(finalI)+"/100");
                    Button confirm;
                    Button cancel;
                    Student s1;



                    grade.setAdapter(list);
                    grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            gradeForSend = ((TextView) view).getText().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                    confirm = (Button) dialog.findViewById(R.id.confirmButton);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            studentsForSend.add(students.get(finalI1));
                            listGradeForSend.add(checkGrade(gradeForSend));
                            students.remove(finalI3);
                            score1.remove(finalI3);
                            refresh();
                            dialog.dismiss();
                        }
                    });

                    cancel = (Button) dialog.findViewById(R.id.cancelButton);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });


            grade.addView(showGrade);
            grade.addView(space);
            grade.addView(gradeButton);

            frame.addView(grade);
            frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.RIGHT));
            frame.setPadding(0,0,20,0);


            relay.addView(frame);

            layout_buntad.addView(image);
            layout_buntad.addView(nameAndID);
            layout_buntad.addView(relay);

            layoutInScroll.addView(layout_buntad);
        }
        scroll.removeAllViews();
        scroll.addView(layoutInScroll);
    }

}
