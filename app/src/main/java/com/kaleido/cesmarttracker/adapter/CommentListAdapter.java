package com.kaleido.cesmarttracker.adapter;

/**
 * Created by monkiyes on 11/5/2015 AD.
 */
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;

import java.util.ArrayList;

public class CommentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> comment;
    private final ArrayList<Integer> rate;
    private final int color;
    private final int color2;
    public CommentListAdapter(Activity context, ArrayList<String> comment, ArrayList<Integer> rate, int color, int color2) {
        super(context, R.layout.comment_list_item, comment);
        this.context = context;
        this.comment = comment;
        this.color=color;
        this.color2=color2;
        this.rate = rate;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.comment_list_item, null, true);

        TextView textView = (TextView) rowView.findViewById(R.id.rate_show);
        textView.setText("" + rate.get(position));
        textView.setBackgroundResource(R.drawable.circle);
        final GradientDrawable drawable = (GradientDrawable) textView.getBackground();
//        if(rate[position]<3){
//            drawable.setColor(rowView.getResources().getColor(R.color.red));
//        }
//        else if(rate[position]==3){
//            drawable.setColor(rowView.getResources().getColor(R.color.comment_head_yellow_dark));
//        }
//        else{
//            drawable.setColor(rowView.getResources().getColor(R.color.green));
//        }
        drawable.setColor(color2);
        TextView textView1 = (TextView) rowView.findViewById(R.id.comment_show);
        textView1.setText(comment.get(position));
        RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.rate_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);

        switch (position%2) {
            case 0:
                layout.setBackgroundResource(R.color.white);
                break;
            case 1:
                layout.setBackgroundColor(color);
                break;

        }
        return rowView;
    }
}