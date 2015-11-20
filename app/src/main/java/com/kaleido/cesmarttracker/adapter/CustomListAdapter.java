package com.kaleido.cesmarttracker.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] subitemname;
    int year,month,date;
    String cSelected = "None";

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid,int year,int month,int date,String cSelected,String[] subitemname) {
        super(context, R.layout.announce_list, itemname);
        // TODO Auto-generated constructor stub
        this.year=year;
        this.month=month;
        this.date=date;
        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.subitemname = subitemname;
        this.cSelected=cSelected;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.announce_list, parent, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txt = (TextView)rowView.findViewById(R.id.subItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txt.setText(subitemname[position]);
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        imageView.setColorFilter(Color.argb(255, 80, 80, 80));
        switch (position) {
            case 0:
                //extratxt.setText("Course : " + cSelected);
                break;
            case 1:

                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;

        }
        return rowView;
    }
}