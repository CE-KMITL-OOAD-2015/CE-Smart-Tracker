package com.kaleido.cesmarttracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kaleido.cesmarttracker.data.Assignment;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

//คลาสนี้ก็โชว นร ต้องรับข้อมูลจากเซิฟเหมือนเดิม ทั้ง 3
//        ArrayList<String> studentsId;
//        ArrayList<String> studentsName;
//private ArrayList<ArrayList<Assignment>> listOfAssignment;
public class ScoreAssignment extends AppCompatActivity {

    private ArrayList<Student> students;
    private ScrollView scroll;
    ArrayList<String> studentsId;
    ArrayList<String> studentsName;
    ArrayList<String> scores = new ArrayList<String>();
    ArrayList<String> scoresForSend = new ArrayList<String>();
    ArrayList<ObjectId> refs = new ArrayList<ObjectId>();
    String assName;

    TextView maxScore;

    ArrayList<Assignment> listForStu = new ArrayList<Assignment>();


    private ArrayList<ArrayList<Assignment>> listOfAssignment;
    Course getCourse;
    int scored = 0;
    int allStudent = 0;
    int amountOfSubmitted = 20;

    TextView showScored;
    TextView showSubmitted;
    TextView showAss;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_assignment);
        scroll = (ScrollView) findViewById(R.id.scrollView2);
        addStudent();




        Bundle bundle = getIntent().getExtras();
        assName = bundle.getString("assName");
        getCourse = bundle.getParcelable("course");
        System.out.print(assName);
        studentsName = getIntent().getExtras().getStringArrayList("students");
        studentsId = getIntent().getExtras().getStringArrayList("id");

        showScored = (TextView) findViewById(R.id.scored);
        showSubmitted = (TextView) findViewById(R.id.submitted);

        showAss = (TextView) findViewById(R.id.course);
        submitButton = (Button) findViewById(R.id.submitButton);




        getData();


    }

    private void setting() {
        final Context context = this;

        listForStu = getAssignmentForStu();
        amountOfSubmitted = getAmountOfSubmitted(listForStu);

        showSubmitted.setText(amountOfSubmitted + "/" + studentsName.size());
        showScored.setText(scored+"/"+amountOfSubmitted);
        showAss.setText(assName);

        for(int x = 0 ; x < studentsName.size() ; x++){
            scores.add(x,"");
        }
        for(int x = 0 ; x < studentsName.size() ; x++){
            scoresForSend.add(x,"");
        }




        LinearLayout layoutInScroll = new LinearLayout(this);
        layoutInScroll.setOrientation(LinearLayout.VERTICAL);
        layoutInScroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));//wrap-match
        for(int i = 0 ; i < studentsName.size() ; i++){
            final int j = i;
            LinearLayout layout_buntad = new LinearLayout(this);
            layout_buntad.setId(j);
            layout_buntad.setPadding(10, 10, 10, 0);
            layout_buntad.setOrientation(LinearLayout.HORIZONTAL);

            layout_buntad.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if(j%2==0){
                layout_buntad.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }

            ImageView image = new ImageView(this);

            image.setImageResource(R.drawable.ic_calculator);
            image.setPadding(20, 0, 20, 0);

            LinearLayout nameAndID = new LinearLayout(this);
            nameAndID.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(this);
            name.setTextSize(15);
            name.setText("" + studentsName.get(i));

            LinearLayout idAndScore = new LinearLayout(this);
            idAndScore.setOrientation(LinearLayout.HORIZONTAL);

            TextView id = new TextView(this);
            id.setTextSize(12);
            id.setText(studentsId.get(i));


            //getscore

            idAndScore.addView(id);


            nameAndID.addView(name);
            nameAndID.addView(idAndScore);




            FrameLayout relay = new FrameLayout(this);
            relay.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));//match-wrap

            FrameLayout frame = new FrameLayout(this);



            LinearLayout grade = new LinearLayout(this);





            grade.setOrientation(LinearLayout.HORIZONTAL);
            grade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            grade.setGravity(Gravity.RIGHT);





            final TextView showGrade = new TextView(this);

            showGrade.setTextSize(12);
            showGrade.setText("");
            showGrade.setTextColor(Color.parseColor("#ffffff"));
            showGrade.setGravity(Gravity.CENTER);

            final int[] buttonStatus = {0};

            showGrade.setBackgroundResource(R.drawable.grey_circle);
            showGrade.setId(i);


            Space space = new Space(this);
            space.setMinimumWidth(20);

            TextView gradeButton = new TextView(this);

            gradeButton.setGravity(Gravity.CENTER);

            gradeButton.setBackgroundResource(R.drawable.blue_circle);

            gradeButton.setTextSize(12);
            gradeButton.setTextColor(Color.parseColor("#FFFFFF"));
            gradeButton.setText("Score");

            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            final int finalI3 = i;
            if(listForStu.get(i).isSubmitted()){
                gradeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.score_assignment_dialog);
                        dialog.setTitle("Title...");


                        maxScore = (TextView) dialog.findViewById(R.id.maxScore);
                        TextView name = (TextView) dialog.findViewById((R.id.nameOfStudent));
                        final EditText score = (EditText) dialog.findViewById(R.id.score);

                        name.setText("" + studentsName.get(finalI));
                        maxScore.setText("/" + listForStu.get(0).getMaxScore());
                        Button confirm;
                        Button cancel;


                        confirm = (Button) dialog.findViewById(R.id.confirmButton);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (buttonStatus[0] == 0) {
                                    if (!(score.getText().toString().contentEquals(""))) {
                                        buttonStatus[0] = 1;
                                        scored += 1;
                                    }

                                }


                                if (score.getText().toString().contentEquals("")) {

                                    if ((!(showGrade.getText().toString().contentEquals(""))) && scored > 0) {
                                        scored -= 1;
                                    }

                                    showGrade.setText("");
                                    showGrade.setBackgroundResource(R.drawable.grey_circle);
                                    buttonStatus[0] = 0;

                                } else {
                                    int a = Integer.parseInt(score.getText().toString());
                                    if(a> listForStu.get(0).getMaxScore()){
                                        showGrade.setText(listForStu.get(0).getMaxScore() + "/" + listForStu.get(0).getMaxScore());
                                    }
                                    else {
                                        showGrade.setText(score.getText() + "/" + listForStu.get(0).getMaxScore());
                                    }
                                    showGrade.setBackgroundResource(R.drawable.green_circle);
                                }
                                showScored.setText(scored + "/" + amountOfSubmitted);
                                scores.set(finalI, showGrade.getText().toString());
                                Toast.makeText(getApplicationContext(),showGrade.getText().toString() , Toast.LENGTH_SHORT).show();
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
            }


            grade.addView(showGrade);
            grade.addView(space);
            grade.addView(gradeButton);

            frame.addView(grade);
            frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
            frame.setPadding(0, 0, 20, 0);


            relay.addView(frame);

            layout_buntad.addView(image);
            layout_buntad.addView(nameAndID);
            layout_buntad.addView(relay);

            layoutInScroll.addView(layout_buntad);





        }
        scroll.removeAllViews();
        scroll.addView(layoutInScroll);







        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int m = 0; m < scores.size(); m++) {
                    if (scores.get(m).toString().contentEquals("")) {
                        scoresForSend.set(m, "");
                    } else {
                        int index = scores.get(m).toString().indexOf("/");
                        scoresForSend.set(m, scores.get(m).toString().substring(0, index));
                    }
                }
                ArrayList<String> assignIds = new ArrayList<String>();
                for (Assignment asm : listForStu)
                    assignIds.add(asm.getObjectId());
                RequestParams params = new RequestParams();
                JSONArray json1 = new JSONArray(assignIds);
                JSONArray json2 = new JSONArray(scoresForSend);
                params.put("assignIds", json1.toString());
                params.put("scores", json2.toString());
                ConnectHttp.post("courses/submit/assignment", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        String response = "";
//                        for (int i = 0; i < responseBody.length; i++)
//                            response += (char) responseBody[i];
//                        if (response.equals("true"))
//                            showErrorMessage("SUCCESS!");
//                        else
//                            showErrorMessage("Failed! Please try again.");
                        System.out.println("HELLO");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        showErrorMessage("Failed to connect : " + statusCode);
                    }
                });
            }
        });
    }

    public void addStudent(){
        students = new ArrayList<Student>();
        students.add(new Student("56010555","Bank"));
        students.add(new Student("56010555","Bank1"));
        students.add(new Student("56010555", "Bank2"));
        students.add(new Student("56010555", "Bank3"));
        students.add(new Student("56010555", "Bank4"));
        students.add(new Student("56010555", "Bank5"));
        students.add(new Student("56010555", "Bank6"));
    }


    //ส่งเกรดไปแบบ ArrayList<String> ตามอินเด็กของนักเรียนทั้งหมด ถ้าไม่มีคะแนนจะเป็น "" นะ
    public ArrayList<String> getScoresForSend() {
        return scoresForSend;
    }

    public ArrayList<Assignment> getAssignmentForStu(){
        ArrayList<Assignment> list = new ArrayList<Assignment>() ;
        for(int i = 0; i < listOfAssignment.size(); i++){
            for(int j = 0 ; j < listOfAssignment.get(i).size() ; j++){
                if(listOfAssignment.get(i).get(j).getTitle().contentEquals(assName)){
                    list.add(listOfAssignment.get(i).get(j));

                }
            }
        }
        return list;
    }
    public int getAmountOfSubmitted(ArrayList<Assignment> ass){
        int index = 0;
        for(int i = 0 ; i < ass.size() ; i++){
            if(ass.get(i).isSubmitted()){
                index += 1;
            }
        }
        return index;
    }

    public void getData() {
        ConnectHttp.get("courses/" + getCourse.getId() + "/studentdetail", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                for (int i = 0; i < responseBody.length; i++)
                    response += (char) responseBody[i];
                Log.i("res", response);
                if (!response.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray arrName = json.getJSONArray("names");
                        JSONArray arrId = json.getJSONArray("ids");
                        JSONArray arrAs = json.getJSONArray("assigns");
                        studentsName = new ArrayList<String>();
                        studentsId = new ArrayList<String>();
                        listOfAssignment = new ArrayList<ArrayList<Assignment>>();
                        for(int i=0; i<arrName.length(); i++) {
                            studentsId.add(arrId.getString(i));
                            studentsName.add(arrName.getString(i));
                            if(!arrAs.getJSONArray(i).toString().equals("[]")) {
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                Type assignmentType = new TypeToken<ArrayList<Assignment>>() {}.getType();
                                ArrayList<Assignment> retrievedAssignments = gson.fromJson(arrAs.getJSONArray(i).toString(), assignmentType);
                                listOfAssignment.add(retrievedAssignments);
                            }
                            else
                                listOfAssignment.add(new ArrayList<Assignment>());
                        }
                        setting();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println("Error : retrieved null data from server.");
//                stopLoadingDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                stopLoadingDialog();
                System.out.println("Error : "+statusCode);
            }
        });
    }

    private void showErrorMessage(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
