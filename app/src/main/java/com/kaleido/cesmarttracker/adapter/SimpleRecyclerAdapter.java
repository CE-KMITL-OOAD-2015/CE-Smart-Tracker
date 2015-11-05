package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;

import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.VersionViewHolder> {
    List<Course> courses;
    Context context;
    OnItemClickListener clickListener;
    int i=0;
    int color;

    public SimpleRecyclerAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        versionViewHolder.title.setText(courses.get(i).getName());
        versionViewHolder.subTitle.setText(courses.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView title;
        TextView subTitle;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.listitem_name);
            subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);
            itemView.setOnClickListener(this);
            switch (i%10){
                case 1:
                    color = (R.color.course_red);
                    break;
                case 2:
                    color = (R.color.course_yellow);
                    break;
                case 3:
                    color = (R.color.course_blue);
                    break;
                case 4:
                    color = (R.color.course_orange);
                    break;
                case 5:
                    color = (R.color.course_green);
                    break;
                case 6:
                    color = (R.color.course_red);
                    break;
                case 7:
                    color = (R.color.course_purple);
                    break;
                case 8:
                    color = (R.color.course_cyan);
                    break;
                case 9:
                    color = (R.color.course_yellow);
                    break;
                case 0:
                    color = (R.color.course_skyblue);
                    break;
            }
            cardItemLayout.setBackgroundResource(color);
            i++;
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
