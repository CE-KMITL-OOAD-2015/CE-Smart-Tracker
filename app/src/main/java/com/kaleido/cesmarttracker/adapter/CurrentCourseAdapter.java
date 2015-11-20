package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Section;

import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class CurrentCourseAdapter extends RecyclerView.Adapter<CurrentCourseAdapter.VersionViewHolder> {
    List<Course> courses;
    List<Section> sections;
    OnItemClickListener clickListener;
    int i=1;
    int color;
    int darkColor;

    public CurrentCourseAdapter(List<Course> courses,List<Section> sections)  {
        this.courses = courses;
        this.sections = sections;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_for_currentcourse, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        versionViewHolder.title.setText(courses.get(i).getName());

        // Set section,time here TODO setHere
        versionViewHolder.subTitle.setText(sections.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView title;
        TextView subTitle;
        ImageView iamge;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            //cardItemLayout.setForegroundGravity(Gravity.CENTER);
//            title = (TextView) itemView.findViewById(R.id.listitem_name);
//            subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);

            title = (TextView) itemView.findViewById(R.id.name);
            subTitle = (TextView) itemView.findViewById(R.id.secAndTime);
            iamge = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            switch (i%10){
                case 1:
                    color = (R.color.course_red1);
                    darkColor = (R.color.course_red1_dark);
                    break;
                case 2:
                    color = (R.color.course_orange1);
                    darkColor = (R.color.course_orange1_dark);
                    break;
                case 3:
                    color = (R.color.course_green1);
                    darkColor = (R.color.course_green1_dark);
                    break;
                case 4:
                    color = (R.color.course_green2);
                    darkColor = (R.color.course_green2_dark);
                    break;
                case 5:
                    color = (R.color.course_skyblue1);
                    darkColor = (R.color.course_skyblue1_dark);
                    break;
                case 6:
                    color = (R.color.course_blue1);
                    darkColor = (R.color.course_blue1_dark);
                    break;
                case 7:
                    color = (R.color.course_light_purple1);
                    darkColor = (R.color.course_light_purple1_dark);
                    break;
                case 8:
                    color = (R.color.course_purple1);
                    darkColor = (R.color.course_purple1_dark);
                    break;
                case 9:
                    color = (R.color.course_red1);
                    darkColor = (R.color.course_red1_dark);
                    break;
                case 0:
                    color = (R.color.course_orange1);
                    darkColor = (R.color.course_orange1_dark);
                    break;
            }
            cardItemLayout.setBackgroundResource(R.drawable.rounded_corner2);
            final GradientDrawable drawable = (GradientDrawable) cardItemLayout.getBackground();
            iamge.setBackgroundColor(itemView.getResources().getColor(darkColor));
            drawable.setColor(itemView.getResources().getColor(color));
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
