package com.kaleido.cesmarttracker.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.adapter.InboxAdapter;
import com.kaleido.cesmarttracker.data.Event;
import com.kaleido.cesmarttracker.data.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by monkiyes on 11/11/2015 AD.
 */
public class InboxFragment extends Fragment {
    public InboxAdapter mAdapter;
    Test test = new Test();
    Student s1 = test.getStudents().get(0);
    ArrayList<Event> events = s1.getEvents();
    RecyclerView recyclerView1;
    LinearLayoutManager mLayoutManager;
    InboxAdapter inboxAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_inbox, container, false);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.taken_list_course);
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(mLayoutManager);

        inboxAdapter = new InboxAdapter(events);
        recyclerView1.setAdapter(inboxAdapter);
        inboxAdapter.SetOnItemClickListener(new InboxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView icon = (ImageView) view.findViewById(R.id.icon_inbox);
                if (events.get(position).getType().contentEquals("Assignment")) {
                    icon.setImageResource(R.drawable.ic_read_assignment);
                    assignmentDialog(view,position);
                } else if (events.get(position).getType().contentEquals("Message")){
                    icon.setImageResource(R.drawable.ic_read_msg);
                    messageDialog(view,position);
                } else if (events.get(position).getType().contentEquals("Result")){
                    icon.setImageResource(R.drawable.ic_read_grade);
                    resultDialog(view,position);
                }

                RelativeLayout r = (RelativeLayout) view.findViewById(R.id.InboxLayout);
                r.setBackgroundColor(Color.parseColor("#efefee"));
                events.get(position).setRead(true);
            }
        });
        return view;
    }

    public void assignmentDialog(View view,int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialogAssignment = builder.create();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogLayout = inflater.inflate(R.layout.assignment_inbox_dialog, null);
        dialogAssignment.setView(dialogLayout);
        dialogAssignment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAssignment.show();
        Button button1 = (Button) dialogAssignment.findViewById(R.id.button_send);
        Button button2 = (Button) dialogAssignment.findViewById(R.id.buttonClose);
        TextView textView = (TextView) dialogAssignment.findViewById(R.id.event_course_title);
        textView.setText(events.get(position).getTitle());
        TextView textView1 = (TextView) dialogAssignment.findViewById(R.id.event_date_announced);
        textView1.setText(events.get(position).getAnnounceDate());
        TextView textView2 = (TextView) dialogAssignment.findViewById(R.id.event_course_name);
        textView2.setText(events.get(position).getCourseName());
        TextView textView3 = (TextView) dialogAssignment.findViewById(R.id.content);
        textView3.setText(events.get(position).getContent());
        TextView textView4 = (TextView) dialogAssignment.findViewById(R.id.event_due_date);
        textView4.setText(events.get(position).getDueDate());
        EditText url = (EditText) dialogAssignment.findViewById(R.id.url);
        String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        //String todayDate = "19/10/2015";
        if(events.get(position).getDueDate().compareTo(todayDate) < 0 ){
            url.setEnabled(false);
            url.setFocusable(false);
            url.setHint("Closed");
            button1.setEnabled(false);
            //TODO set submitButton's color to GRAY
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAssignment.dismiss();
                //TODO send url to assignment na ka & show success dialog
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAssignment.dismiss(); //to dismiss the Dialog
            }
        });
    }

    public void messageDialog(View view,int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialogAssignment = builder.create();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogLayout = inflater.inflate(R.layout.message_inbox_dialog, null);
        dialogAssignment.setView(dialogLayout);
        dialogAssignment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAssignment.show();
        Button button2 = (Button) dialogAssignment.findViewById(R.id.buttonClose);
        TextView textView = (TextView) dialogAssignment.findViewById(R.id.event_course_title);
        textView.setText(events.get(position).getTitle());
        TextView textView1 = (TextView) dialogAssignment.findViewById(R.id.event_date_announced);
        textView1.setText(events.get(position).getAnnounceDate());
        TextView textView2 = (TextView) dialogAssignment.findViewById(R.id.event_course_name);
        textView2.setText(events.get(position).getCourseName());
        TextView textView3 = (TextView) dialogAssignment.findViewById(R.id.content);
        textView3.setText(events.get(position).getContent());
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAssignment.dismiss(); //to dismiss the Dialog
            }
        });
    }

    public void resultDialog(View view,int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialogAssignment = builder.create();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogLayout = inflater.inflate(R.layout.result_inbox_dialog, null);
        dialogAssignment.setView(dialogLayout);
        dialogAssignment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAssignment.show();
        Button button2 = (Button) dialogAssignment.findViewById(R.id.buttonClose);
        TextView textView = (TextView) dialogAssignment.findViewById(R.id.event_course_title);
        textView.setText(events.get(position).getTitle());
        TextView textView1 = (TextView) dialogAssignment.findViewById(R.id.event_date_announced);
        textView1.setText(events.get(position).getAnnounceDate());
        TextView textView2 = (TextView) dialogAssignment.findViewById(R.id.event_course_name);
        textView2.setText(events.get(position).getCourseName());
        TextView textView3 = (TextView) dialogAssignment.findViewById(R.id.content);
        textView3.setText(events.get(position).getContent());
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAssignment.dismiss(); //to dismiss the Dialog
            }
        });
    }
}