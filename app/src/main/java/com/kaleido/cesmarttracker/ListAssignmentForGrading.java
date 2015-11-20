package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kaleido.cesmarttracker.data.Assignment;
import com.kaleido.cesmarttracker.data.Course;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


//คลาสนี้แสดงลิสของแอสไสทั้งหมดที่วิขานั้นมี หน้านี้จะรับข้อมูลจากเซิฟเวอร์ 3 ชุด อย่างที่ทีมบอก มี ชื่อดังนี้
//// private ArrayList<String> studentName;//TODO 3 list that u said
//private ArrayList<String> studentId;
//private ArrayList<ArrayList<Assignment>> listOfAssignment;
public class ListAssignmentForGrading extends AppCompatActivity {

    private ArrayList<Course> courses = new ArrayList<Course>();
    private int selected = 0;
    private Button button;
    private UserLocalStore userLocalStore;
    private TextView course;
    private Course getCourse;

    private ArrayList<String> studentName;//TODO 3 list that u said
    private ArrayList<String> studentId;
    private ArrayList<ArrayList<Assignment>> listOfAssignment;
    private ArrayList<Assignment> listAss1;
    private ArrayList<Assignment> listAss2;
    private ArrayList<Assignment> listAss3;
    private ArrayList<Assignment> listAssToShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_assignment_for_grading);
        userLocalStore = new UserLocalStore(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        getCourse = bundle.getParcelable("course");
        courses = userLocalStore.getTeacherData().getCourses();
        course = (TextView) findViewById(R.id.course);
        course.setText(getCourse.getName());
        button  = (Button) findViewById(R.id.button);


        getData();

        //setting();

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

    public void setting(){
        String courseName = getCourse.getName();
        listAssToShow = new ArrayList<Assignment>();
        for(int i = 0 ; i < listOfAssignment.size() ; i++){
            for(int j = 0 ; j < listOfAssignment.get(i).size() ; j++){
                if(courseName.equals(listOfAssignment.get(i).get(j).getCourseName())){
                    boolean exist = false;
                    for(int k = 0 ; k < listAssToShow.size() ; k++){
                        if(listAssToShow.get(k).getTitle().equals(listOfAssignment.get(i).get(j).getTitle())){
                            exist = true;
                        }

                    }
                    if(!exist) {
                        listAssToShow.add(listOfAssignment.get(i).get(j));
                    }
                }
            }
        }




        LinearLayout inScroll = (LinearLayout) findViewById(R.id.layInScroll);

        for(int i = 0;i<listAssToShow.size();i++){
            TextView course = new TextView(this);
            course.setText(listAssToShow.get(i).getTitle());
            course.setTextSize(15);
            course.setTextColor(Color.parseColor("#1B1B1B"));
            course.setGravity(Gravity.CENTER_VERTICAL);
            course.setId(i);
            course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0 ; j < listAssToShow.size() ; j++){
                        TextView course = (TextView) findViewById(j);
                        course.setBackgroundColor(Color.parseColor("#FFE7E8E6"));
                    }
                    v.setBackgroundColor(Color.parseColor("#EBD88D"));
                    selected = v.getId();
                }
            });
            course.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
            course.setBackgroundColor(Color.parseColor("#FFE7E8E6"));
            course.setPadding(300, 0, 0, 0);
            inScroll.addView(course);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ScoreAssignment.class);
                String s = listAssToShow.get(selected).getTitle();
                i.putExtra("assName",s);
                i.putStringArrayListExtra("students",studentName);
                i.putStringArrayListExtra("id",studentId);
                i.putExtra("course",getCourse);
                startActivity(i);

            }
        });
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
                        studentName = new ArrayList<String>();
                        studentId = new ArrayList<String>();
                        listOfAssignment = new ArrayList<ArrayList<Assignment>>();
                        for(int i=0; i<arrName.length(); i++) {
                            studentId.add(arrId.getString(i));
                            studentName.add(arrName.getString(i));
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
}
