package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText)findViewById(R.id.loUsername);
        EditText password = (EditText)findViewById(R.id.loPassword);
        Button bLogin = (Button)findViewById(R.id.buttonLogin);
        Button bRegister = (Button)findViewById(R.id.buttonRegister);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonLogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.buttonRegister:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
