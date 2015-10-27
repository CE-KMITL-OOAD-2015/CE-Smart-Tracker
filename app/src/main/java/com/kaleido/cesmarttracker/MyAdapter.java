package com.kaleido.cesmarttracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pirushprechathavanich on 10/16/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];
    private String name;
    private int profile;
    private String email;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderId;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;
        TextView email;
        Context context;

        public ViewHolder(View itemView, int viewType , Context context) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            if(viewType == TYPE_ITEM) {
                textView = (TextView)itemView.findViewById(R.id.rowText);
                imageView = (ImageView)itemView.findViewById(R.id.rowIcon);
                holderId = 1;
            }
            else {
                name = (TextView)itemView.findViewById(R.id.name);
                email = (TextView)itemView.findViewById(R.id.email);
                profile = (ImageView)itemView.findViewById(R.id.circleView);
                holderId = 0;
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    public MyAdapter(String titles[], int icons[], String name, String email, int profile,Context context) {
        mNavTitles = titles;
        mIcons = icons;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType,context);
            return vhItem;
        }
        else if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType,context);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        if(holder.holderId == 1) {
            holder.textView.setText(mNavTitles[position-1]);
            holder.imageView.setImageResource(mIcons[position-1]);
        }
        else {
            holder.profile.setImageResource(profile);
            holder.name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }
}
