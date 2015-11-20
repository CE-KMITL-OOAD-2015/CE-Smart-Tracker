package com.kaleido.cesmarttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    UserLocalStore userLocalStore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        userLocalStore = new UserLocalStore(getApplicationContext());
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent i = new Intent();
                    if(userLocalStore.getUserLoggedIn()) {
                        i.setClassName("com.kaleido.cesmarttracker", "com.kaleido.cesmarttracker.MainActivity");
                        if(userLocalStore.getRole()==2)
                            userLocalStore.updateStudent();
                    }
                    if(!userLocalStore.getUserLoggedIn())
                        i.setClassName("com.kaleido.cesmarttracker","com.kaleido.cesmarttracker.LoginActivity");
                    startActivity(i);
                }
            }
        };
        splashThread.start();
    }
}