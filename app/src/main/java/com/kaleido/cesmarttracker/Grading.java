package com.kaleido.cesmarttracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;

public class Grading extends AppCompatActivity {

    private TextView name;
    private Spinner grade;
    private TextView score;
    private Button confirm;
    private Button cancel;
    private Student s1;
    private ArrayList<String> c1;
    private String gradeForSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        c1 = new ArrayList<String>();

        matchID();
        addGrade();

        gradeForSend = "A";

        s1 = new Student("56010555","Touch Junsom");

        name.setText(s1.getName());
        score.setText("80/100");

        ArrayAdapter<String> list = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, c1);


        grade.setAdapter(list);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                gradeForSend = "" + ((TextView) view).getText();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gradeForSend,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void matchID(){
        name = (TextView)findViewById(R.id.nameOfStudent);
        score = (TextView) findViewById(R.id.score);
        grade = (Spinner) findViewById(R.id.spinner);
        confirm = (Button) findViewById(R.id.confirmButton);
        cancel = (Button) findViewById(R.id.cancelButton);
    }

    public void addGrade(){
        c1.add("A");
        c1.add("B+");
        c1.add("B");
        c1.add("C+");
        c1.add("C");
        c1.add("D+");
        c1.add("D");
        c1.add("F");
    }
}
