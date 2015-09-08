package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView warningmsg = (TextView)findViewById(R.id.regWarning);
        EditText fullname = (EditText)findViewById(R.id.regFullname);
        EditText password = (EditText)findViewById(R.id.regPassword);
        EditText password2 = (EditText)findViewById((R.id.regRePassword));
        EditText email = (EditText)findViewById((R.id.regEmail));
        EditText regCode = (EditText)findViewById((R.id.regCode));

        Button buttonConfirm = (Button)findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.buttonConfirm:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
