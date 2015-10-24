package com.kaleido.cesmarttracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaleido.cesmarttracker.data.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;
    EditText etUsername,etPassword;
    Button bLogin,bRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.loUsername);
        etPassword = (EditText)findViewById(R.id.loPassword);
        bLogin = (Button)findViewById(R.id.buttonLogin);
        bRegister = (Button)findViewById(R.id.buttonRegister);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(username,password);
                authenticate(user);

                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                break;
            case R.id.buttonRegister:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null)
                    showErrorMessage();
                else
                    logUserIn(returnedUser);
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect username/password");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this,MainActivity.class));
    }
}
