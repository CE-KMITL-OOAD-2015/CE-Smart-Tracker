package com.kaleido.cesmarttracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.ConnectHttp;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.adapter.AssignmentListAdapter;
import com.kaleido.cesmarttracker.adapter.ProgressTeacherStudentListAdapter;
import com.kaleido.cesmarttracker.data.Assignment;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Touch on 11/17/2015.
 */
public class ProgressTeacherFragment2 extends Fragment {
    private Course selectedCourse;

    private Test test=new Test();
    Teacher t1=test.getTeachers().get(0);
    //TODO Selected course
    //course selectedCourse
    //Course selectedCourse = t1.getCourses().get(0);
    Activity act=getActivity();
    RubberLoaderView rubberLoaderView;
    Dialog dialog;

    public ProgressTeacherFragment2(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    View view;
    ArrayList<Student> students;


    Student selectStudent = new Student("1", "x");
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=inflater.inflate(R.layout.progress_student_list_card,container,false);

        //TODO Array of Students in course
        // = selectedCourse.getAllStudent();
        showLoadingDialog();
        ConnectHttp.get("courses/" + selectedCourse.getId() + "/students", null, new AsyncHttpResponseHandler() {
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
                        Type studentType = new TypeToken<ArrayList<Student>>() {
                        }.getType();
                        ArrayList<Student> retrievedStudents = gson.fromJson(response, studentType);
                        students = retrievedStudents;
                        setting();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println("Error : retrieved null data from server.");
                stopLoadingDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                stopLoadingDialog();
                System.out.println("Error : " + statusCode);
            }
        });

        //Array studentNames

        return view;
    }
    private void setting(){
        final ArrayList<String> studentNames = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            studentNames.add(students.get(i).getName());
        }

        //Array studentIDs
        final ArrayList<String> studentIDs = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            studentIDs.add(students.get(i).getId());
        }
        //Array studentSections
        final ArrayList<String> studentSections  = new ArrayList<>();
        for (Student s:students) {
            for(int i=0;i<s.getSchedule().getCurrentCourses().size();i++){
                if(s.getSchedule().getCurrentCourses().get(i).getId().contentEquals(selectedCourse.getId()))
                    studentSections.add(""+s.getSchedule().getCurrentSections().get(i).getId());
            }
        }
        TextView size=(TextView) view.findViewById(R.id.textView52);
        size.setText(""+students.size());
        //Array totalGrades for totalGradeOfStudent
        ArrayList<Integer> totalGrades = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            totalGrades.add(students.get(i).getTotalGradeAssignment(selectedCourse));
        }
        //Array totalGrades for maxGradeOfStudent
        ArrayList<Integer> maxGrades = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            maxGrades.add(students.get(i).getMaxGradeAssignment(selectedCourse));
        }

        //if student be selected
        //selectedStudent
        Student selectedStudent = test.getStudents().get(0);
        //Array assignmentTitles
        ArrayList<String> assignmentTitles = new ArrayList<>();
        for(int i=0;i<selectedStudent.getAssignments().size();i++){
            assignmentTitles.add(selectedStudent.getAssignments().get(i).getTitle());
        }

        //Array grades of Student's assignments
        ArrayList<Integer> grades=new ArrayList<>();
        for (int i = 0; i < selectedStudent.getAssignments().size(); i++) {
            grades.add(selectedStudent.getAssignments().get(i).getScore());
        }


        ProgressTeacherStudentListAdapter adapter = new ProgressTeacherStudentListAdapter(getActivity(),studentNames,studentSections,studentIDs,totalGrades,maxGrades);
        ListView listView = (ListView) view.findViewById(R.id.studentListTeacher);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectStudent = students.get(position);
                studentDetailDialog(view);

            }
        });
    }
    private void studentDetailDialog(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.student_detail_dialog, null);
                dialog.setView(dialogLayout);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                final TextView r1, r2, r3, r4;
                r1 = (TextView) dialog.findViewById(R.id.textView63);
                r1.setText(selectStudent.getName());
                r2 = (TextView) dialog.findViewById(R.id.textView65);
                r2.setText(selectStudent.getId());
                r3 = (TextView) dialog.findViewById(R.id.textView67);
                for (int i = 0; i < selectStudent.getSchedule().getCurrentCourses().size(); i++) {
                    if (selectStudent.getSchedule().getCurrentCourses().get(i).getId().contentEquals(selectedCourse.getId()))
                        r3.setText("" + selectStudent.getSchedule().getCurrentSections().get(i).getId());
                }
                ArrayList<Assignment> assignments = new ArrayList<>();
                ArrayList<String> assignmentName = new ArrayList<>();
                ArrayList<Integer> assignmentScores = new ArrayList<>();
                ArrayList<Integer> assignmentMaxScores = new ArrayList<>();

                int totalScores = 0;
                int totalMaxScores = 0;
                if(selectStudent.getAssignments()!=null) {
                    for (Assignment a : selectStudent.getAssignments()) {
                        if (a.getCourseName().contentEquals(selectedCourse.getName()))
                            assignments.add(a);
                    }
                }
                if(assignments!=null) {
                    for (Assignment a : assignments) {
                        assignmentName.add(a.getTitle());
                    }
                    for (Assignment a : assignments) {
                        assignmentScores.add(a.getScore());
                        totalScores += a.getScore();

                    }

                    for (Assignment a : assignments) {
                        assignmentMaxScores.add(a.getMaxScore());
                        totalMaxScores += a.getMaxScore();
                    }
                }
                AssignmentListAdapter adapter2 = new AssignmentListAdapter(getContext(), assignmentName, assignmentScores, assignmentMaxScores);
                ListView listView2 = (ListView) dialog.findViewById(R.id.assignmentListView);
                listView2.setAdapter(adapter2);

                r4 = (TextView) dialog.findViewById(R.id.textView62);
                r4.setText(totalScores + "/" + totalMaxScores);
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.imageView5);
                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
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

    private void showLoadingDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View loadingDialog = inflater.inflate(R.layout.loading_dialog, null);
        rubberLoaderView = (RubberLoaderView)loadingDialog.findViewById(R.id.loader1);
        dialog = new Dialog(getContext());
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
