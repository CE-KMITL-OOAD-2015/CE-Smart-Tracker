package com.kaleido.cesmarttracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Section;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pirushprechathavanich on 9/10/15 AD.
 */
public class UserLocalStore { //TODO: static class?

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        //userLocalDatabase = PreferenceManager.getDefaultSharedPreferences(context);
    }

//    public void storeUserData(User user) {
//        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
//        spEditor.putString("fullname", user.getFullname());
//        spEditor.putString("username", user.getUsername());
//        spEditor.putString("password", user.getPassword());
//        spEditor.putString("email", user.getEmail());
//        spEditor.putString("regCode", user.getRegCode());
//        spEditor.putInt("id", user.getID());
//        spEditor.commit();
//    }

//    public User getLoggedInUser() {
//        String fullname = userLocalDatabase.getString("fullname", "");
//        String username = userLocalDatabase.getString("username", "");
//        String password = userLocalDatabase.getString("password", "");
//        String email = userLocalDatabase.getString("email", "");
//        String regCode = userLocalDatabase.getString("regCode", "");
//        int id = userLocalDatabase.getInt("id", 0);
//
//        return new User(fullname,username,password,regCode,email,id);
//    }

    public boolean getUserLoggedIn() {
        if(userLocalDatabase.getBoolean("loggedIn",false) == true)
            return true;
        else
            return false;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.remove("loggedIn");
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.putInt("role", -1);
        spEditor.commit();
    }

    public void storeStudentData(Student s) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear(); //clear old session before re-assign.
        spEditor.commit();
        Gson gson = new Gson();
        String json = gson.toJson(s);
        spEditor.putString("student", json); //store student in localDB
        spEditor.remove("role");
        spEditor.putInt("role", 2);
        spEditor.commit();
        System.out.println(userLocalDatabase.getString("student", ""));
        System.out.println(getStudentData().getName());
    }

    public Student getStudentData() {
        Gson gson = new Gson();
        String json = userLocalDatabase.getString("student","");
        return gson.fromJson(json, Student.class);
    }

    public void storeTeacherData(Teacher t) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear(); //clear old session before re-assign.
        spEditor.commit();
        Gson gson = new Gson();
        String json = gson.toJson(t);
        spEditor.putString("teacher", json);
        spEditor.remove("role");
        spEditor.putInt("role", 1);
        spEditor.commit();
    }

    public Teacher getTeacherData() {
        Gson gson = new Gson();
        String json = userLocalDatabase.getString("teacher","");
        return gson.fromJson(json, Teacher.class);
    }

    public int getRole() {
        return userLocalDatabase.getInt("role", -1);
    }

    public void print() {
        Student s = getStudentData();
        System.out.println("> Transcript :");
        for(Course c : s.getTranscript().getAllTakenCourses())
            System.out.println(c.getName());
        System.out.println("> Schedule section :");
        for(Section sec : s.getSchedule().getCurrentSections())
            System.out.println(sec.getId());
    }

    public void updateStudent() {
        Student s = getStudentData();
        ConnectHttp.get("students/" + s.getId(), null, new AsyncHttpResponseHandler() {
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
                        Type studentType = new TypeToken<Student>() {}.getType();
                        Student updatedStudent = gson.fromJson(response, studentType);
                        storeStudentData(updatedStudent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println("Error! Cannot update student data.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error : "+statusCode+" , cannot update student data.");
            }
        });
    }
}
