package com.kaleido.cesmarttracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InboxActivity extends Activity implements AdapterView.OnItemClickListener{
    Test test=new Test();
    private static final String MY_PREFS = "my_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        SharedPreferences shared = getSharedPreferences(MY_PREFS,
                Context.MODE_PRIVATE);
  //      test.getStudents().get(0).updateEvents();
        ArrayList<String> events=new ArrayList<>();
        for (int i=0;i<test.getStudents().get(0).getEvents().size();i++){
            events.add(test.getStudents().get(0).getEvents().get(i).getTitle()+" : "+test.getStudents().get(0).getEvents().get(i).isRead());
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
        // Specify the layout to use when the list of choices appears
        // Apply the adapter to the spinner
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Showing selected spinner item
        SharedPreferences shared = getSharedPreferences(MY_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("eventSelect", position+"");
        editor.commit();
        Intent i = new Intent(InboxActivity.this, EventActivity.class);
        startActivity(i);

    }


}
