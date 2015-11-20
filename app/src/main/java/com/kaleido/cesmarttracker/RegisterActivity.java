package com.kaleido.cesmarttracker;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaleido.cesmarttracker.adapter.MyAdapter;
import com.kaleido.cesmarttracker.data.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonConfirm;
    EditText fullname,password,password2,email,username;
    TextView warningmsg;

    private Toolbar toolbar;

    String menus[] = {"Schedule","Inbox","Current Courses","Register","Progress","GPA Calculator"};
    int icons[] = {R.drawable.ic_schedule,R.drawable.ic_inbox,R.drawable.ic_course,R.drawable.ic_announce,R.drawable.ic_progress,R.drawable.ic_calculator};
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
        mAdapter = new MyAdapter(menus,icons,navName,navEmail,profile,this);
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
        username = (EditText)findViewById(R.id.regUsername);
        password = (EditText)findViewById(R.id.regPassword);
        password2 = (EditText)findViewById((R.id.regRePassword));
        email = (EditText)findViewById((R.id.regEmail));

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
                    String regUsername = username.getText().toString();
                    String regPassword = password.getText().toString();
                    String regEmail = email.getText().toString();
                    //Temporary use: Username is regCode and ID is 0;
                    User registeredUser = new User(regFullname,regEmail,regUsername,regPassword,0);
                    registerUser(regUsername,regPassword,regFullname,regEmail);
                }
                else
                    warningmsg.setText("Please re-enter password.");
                break;
        }
    }

    private void registerUser(String username, String password, String fullname, String email) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("username",username);
        requestParams.put("password",password);
        requestParams.put("name",fullname);
        requestParams.put("email", email);
        ConnectHttp.setAuthen("authen", "password");
        ConnectHttp.get("register/student", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = "";
                for (int i = 0; i < responseBody.length; i++)
                    s += (char) responseBody[i];
                Log.i("res", s);
                System.out.println(s);
                int res = Integer.parseInt(s);
                if(res==4)
                    finish();//TODO: show dialog finish!
                else if(res==3)
                    warningmsg.setText("This email was already registered.");
                else if(res==2)
                    warningmsg.setText("This username was already registered.");
                else
                    warningmsg.setText("Problem occurred! Please try again.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                warningmsg.setText("Problem occurred! Please check your internet connection.");
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
