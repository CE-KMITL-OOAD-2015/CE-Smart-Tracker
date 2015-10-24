package com.kaleido.cesmarttracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.kaleido.cesmarttracker.data.User;

/**
 * Created by pirushprechathavanich on 9/10/15 AD.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("fullname", user.getFullname());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("regCode", user.getRegCode());
        spEditor.putInt("id", user.getID());
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String fullname = userLocalDatabase.getString("fullname", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        String regCode = userLocalDatabase.getString("regCode", "");
        int id = userLocalDatabase.getInt("id", 0);

        return new User(fullname,username,password,regCode,email,id);
    }

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
        spEditor.commit();
    }
}
