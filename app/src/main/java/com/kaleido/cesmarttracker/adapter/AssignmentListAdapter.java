package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;

import java.util.ArrayList;

/**
 * Created by Touch on 11/17/2015.
 */
public class AssignmentListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> itemNames;
    private final ArrayList<Integer> scores;
    private final ArrayList<Integer> maxScores;

    public AssignmentListAdapter(Context context, ArrayList itemNames, ArrayList<Integer> scores, ArrayList<Integer> maxScores) {
        super(context, R.layout.assignment_list_item, itemNames);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemNames=itemNames;
        this.scores=scores;
        this.maxScores=maxScores;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.assignment_list_item, parent, false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView68);
        txtTitle.setText(itemNames.get(position));
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView69);
        txtTitle2.setText(scores.get(position)+"/"+maxScores.get(position));
        switch (position) {
            case 0:
                //extratxt.setText("Course : " + cSelected);
                break;
            case 1:
                //extratxt.setText("Due Date : " + date+" / "+month+" / "+year);
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;

        }
        return rowView;
    }
}
