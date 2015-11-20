package com.kaleido.cesmarttracker.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;

import java.util.ArrayList;

/**
 * Created by Touch on 11/17/2015.
 */
public class ProgressTeacherStudentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemNames;
    private final ArrayList<String> sections;
    private final ArrayList<String> itemIDs;
    private final ArrayList<Integer> scores;
    private final ArrayList<Integer> maxScores;

    public ProgressTeacherStudentListAdapter(Activity context, ArrayList<String> itemNames, ArrayList<String> sections, ArrayList<String> itemIDs, ArrayList<Integer> scores, ArrayList<Integer> maxScores) {
        super(context, R.layout.progress_teacher_student_list_item, itemNames);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemNames=itemNames;
        this.sections=sections;
        this.itemIDs=itemIDs;
        this.scores=scores;
        this.maxScores=maxScores;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.progress_teacher_student_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView50);
        txtTitle.setText(itemNames.get(position));

        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView57);
        txtTitle2.setText(sections.get(position));

        TextView txtTitle3 = (TextView) rowView.findViewById(R.id.textView56);
        txtTitle3.setText(itemIDs.get(position));

        TextView txtTitle4 = (TextView) rowView.findViewById(R.id.textView59);
        txtTitle4.setText(scores.get(position)+"/"+maxScores.get(position));

        RelativeLayout relativeLayout=(RelativeLayout) rowView.findViewById(R.id.studentListLayout);

        switch (position%2) {
            case 0:
                break;
            case 1:
                relativeLayout.setBackgroundColor(Color.parseColor("#e4e4e4"));
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;

        }
        return rowView;
    }

}