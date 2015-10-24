package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.kaleido.cesmarttracker.data.Event;

public class EventActivity extends Activity {
    Test test=new Test();
    private static final String MY_PREFS = "my_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        SharedPreferences shared = getSharedPreferences(MY_PREFS,
                Context.MODE_PRIVATE);
      //  test.getStudents().get(0).updateEvents();
        String eventSelect = shared.getString("eventSelect", "1");
        Event event = test.getStudents().get(0).getEvents().get(Integer.parseInt(eventSelect));
        String subject = event.getCourseName();
        String title = event.getTitle();
        String content = event.getContent();
        String dueDate = event.getDueDate();
        TextView textOut = (TextView) findViewById(R.id.textView12);
        textOut.setText(subject);
        TextView textOut2 = (TextView) findViewById(R.id.textView14);
        textOut2.setText(title);
        TextView textOut3 = (TextView) findViewById(R.id.textView16);
        textOut3.setText(content);
        TextView textOut4 = (TextView) findViewById(R.id.textView7);
        textOut4.setText(dueDate);

    }
}
