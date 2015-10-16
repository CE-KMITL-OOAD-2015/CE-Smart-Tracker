package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonConfirm;
    EditText fullname,password,password2,email,regcode;
    TextView warningmsg;

    private Toolbar toolbar;

    String menus[] = {"Schedule","Inbox","Current Courses","Register","Progress","GPA Calculator"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_course,R.drawable.ic_register,R.drawable.ic_progress,R.drawable.ic_calculator};
    String navName = "Bank Thanawat";
    String navEmail = "bankza514@gmail.com";
    int profile = R.drawable.user;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //NAVIGATION DRAWER START HERE!
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(menus,icons,navName,navEmail,profile);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        drawer = (DrawerLayout)findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        // NAVIGATION DRAWER STOP HERE!


        warningmsg = (TextView)findViewById(R.id.regWarning);
        fullname = (EditText)findViewById(R.id.regFullname);
        password = (EditText)findViewById(R.id.regPassword);
        password2 = (EditText)findViewById((R.id.regRePassword));
        email = (EditText)findViewById((R.id.regEmail));
        regcode = (EditText)findViewById((R.id.regCode));

        buttonConfirm = (Button)findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.buttonConfirm:
                if(Objects.equals(password.getText().toString(), password2.getText().toString())) {
                    warningmsg.setText("Connecting...");
                    String regFullname = fullname.getText().toString();
                    String regPassword = password.getText().toString();
                    String regEmail = email.getText().toString();
                    String regCode = regcode.getText().toString();
                    //Temporary use: Username is regCode and ID is 0;
                    User registeredUser = new User(regFullname,regCode,regPassword,regCode,regEmail,0);
                    registerUser(registeredUser);
                }
                else
                    warningmsg.setText("Please re-enter password");
                break;
        }
    }

    private void registerUser(User user) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }
}
