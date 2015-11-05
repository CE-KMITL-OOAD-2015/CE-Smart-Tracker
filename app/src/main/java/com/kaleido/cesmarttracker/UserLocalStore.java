package com.kaleido.cesmarttracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;

/**
 * Created by pirushprechathavanich on 9/10/15 AD.
 */
public class UserLocalStore { //TODO: static class?

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
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
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.putInt("role",-1);
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
        spEditor.putInt("role",2);
        spEditor.commit();
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
        spEditor.putInt("role",1);
        spEditor.commit();
    }

    public Teacher getTeacherData() {
        Gson gson = new Gson();
        String json = userLocalDatabase.getString("teacher","");
        return gson.fromJson(json, Teacher.class);
    }

    public int getRole() {
        return userLocalDatabase.getInt("role",-1);
    }
}
