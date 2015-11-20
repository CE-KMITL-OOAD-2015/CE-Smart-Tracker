package com.kaleido.cesmarttracker.adapter;

import android.app.Activity;
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
public class StudentListByScoreAdapter extends ArrayAdapter<String>{

        private final Activity context;
        private final ArrayList<String> itemNames;
        private final ArrayList<Integer> scores;

        public StudentListByScoreAdapter(Activity context, ArrayList itemNames, ArrayList<Integer> scores) {
            super(context, R.layout.student_list_item_by_score, itemNames);
            // TODO Auto-generated constructor stub
            this.context=context;
            this.itemNames=itemNames;
            this.scores=scores;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.student_list_item_by_score, null, true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.textView48);

            txtTitle.setText(itemNames.get(position));
            TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView49);

            txtTitle2.setText(""+scores.get(position));
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
