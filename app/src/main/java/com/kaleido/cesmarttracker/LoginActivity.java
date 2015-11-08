package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;
import com.kaleido.cesmarttracker.data.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserLocalStore userLocalStore;
    private EditText etUsername,etPassword;
    private Button bLogin,bRegister;

    private LoginButton fbLogin;
    private CallbackManager callbackManager;

    protected RubberLoaderView rubberLoaderView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FACEBOOK LOGIN :
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        fbLogin = (LoginButton)findViewById(R.id.fblogin_button);
        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //TODO: check if fbId matches, then authen&login.
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        //setContentView(R.layout.login);

        //LOGIN FORM :
        etUsername = (EditText)findViewById(R.id.loUsername);
        etPassword = (EditText)findViewById(R.id.loPassword);
        bLogin = (Button)findViewById(R.id.buttonLogin);
        bRegister = (Button)findViewById(R.id.buttonRegister);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

<<<<<<< Updated upstream
        userLocalStore = new UserLocalStore(this);
        rubberLoaderView = (RubberLoaderView)findViewById(R.id.loader1);

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rubberLoaderView.startLoading();
        dialog.show();
=======
        userLocalStore = new UserLocalStore(getApplicationContext());
        //rubberLoaderView = (RubberLoaderView)findViewById(R.id.loader1);

//        LayoutInflater inflater = getLayoutInflater();
//        View loadingDialog = inflater.inflate(R.layout.loading_dialog, null);
//        rubberLoaderView = (RubberLoaderView)loadingDialog.findViewById(R.id.loader1);
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //dialog.setContentView(R.layout.loading_dialog);
//        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(loadingDialog);
//        rubberLoaderView.startLoading();
//        dialog.show();
>>>>>>> Stashed changes
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonLogin:
                //showLoadingDialog();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //rubberLoaderView.startLoading();
                authenticate(username, password);
                break;
            case R.id.buttonRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void authenticate(String username, String password) {
        final Activity thisAct = this;
        ConnectHttp.setAuthen("authen", "password");
        ConnectHttp.get("login/" + username + "/", new RequestParams("password", password), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                for (int i = 0; i < responseBody.length; i++)
                    response += (char) responseBody[i];
                Log.i("res", response);
                if (!response.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject(response);
                        int role = json.getInt("role");
                        if (role == 2) //Student
                            storeStudent(json.getString("id"));
                        else if (role == 1) //Teacher
                            storeTeacher(json.getString("id"));
                        userLocalStore.setUserLoggedIn(true);
<<<<<<< Updated upstream
                        startActivity(new Intent(thisAct, MainActivity.class));
                        finish();
=======
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //stopLoadingDialog();
                                startActivity(new Intent(thisAct, MainActivity.class));
                                finish();
                            }
                        }, 1000);
                        //startActivity(new Intent(thisAct, MainActivity.class));
>>>>>>> Stashed changes
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    showErrorMessage("Incorrect Username/Password.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //stopLoadingDialog();
                showErrorMessage("Error authentication : " + statusCode); //TODO : make toast
            }
        });
    }

    private void storeStudent(String id) {
        ConnectHttp.get("students/" + id + "/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = "";
                for(int i=0; i<responseBody.length; i++)
                    response += (char)responseBody[i];
                Log.i("res",response);
                if(!response.isEmpty()) {
                    try {
                        //JSONObject json = new JSONObject(response);
                        Gson gson = new Gson();
                        Type studentType = new TypeToken<Student>(){}.getType();
                        Student s = gson.fromJson(response, studentType);
                        //Student s = new ObjectMapper().readValue(response, Student.class);
                        //showErrorMessage(response);
                        userLocalStore.storeStudentData(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    showErrorMessage("Cannot match the requested student's ID.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showErrorMessage("Error : "+statusCode);
            }
        });
    }

    private void storeTeacher(String id) {
        ConnectHttp.get("teachers/" + id + "/", null, new AsyncHttpResponseHandler() {
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
                        Type teacherType = new TypeToken<Teacher>() {
                        }.getType();
                        Teacher t = gson.fromJson(response, teacherType);
                        //Teacher t = new ObjectMapper().readValue(json.toString(), Teacher.class);
                        userLocalStore.storeTeacherData(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    showErrorMessage("Cannot match the requested teacher's ID.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showErrorMessage("Error : " + statusCode);
            }
        });
    }

    private void showErrorMessage(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

//    private void showLoadingDialog() {
//        LayoutInflater inflater = getLayoutInflater();
//        View loadingDialog = inflater.inflate(R.layout.loading_dialog, null);
//        rubberLoaderView = (RubberLoaderView)loadingDialog.findViewById(R.id.loader1);
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //dialog.setContentView(R.layout.loading_dialog);
//        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(loadingDialog);
//        rubberLoaderView.startLoading();
//        dialog.show();
//    }
//
//    private void stopLoadingDialog() {
//        dialog.cancel();
//    }

    private void logUserIn(User returnedUser) {
        //userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
    }
}
