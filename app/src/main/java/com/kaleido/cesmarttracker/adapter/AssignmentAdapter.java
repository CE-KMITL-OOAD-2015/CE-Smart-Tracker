package com.kaleido.cesmarttracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;

public class AssignmentAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] subitemname;
    int year,month,date;
    String cSelected = "None";

    public AssignmentAdapter(Activity context, String[] itemname, Integer[] imgid,String[] subitemname) {
        super(context, R.layout.assignment_list, itemname);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.subitemname = subitemname;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.assignment_list, parent, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        EditText txt = (EditText)rowView.findViewById(R.id.subItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txt.setText(subitemname[position]);
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);

        return rowView;
    }
}