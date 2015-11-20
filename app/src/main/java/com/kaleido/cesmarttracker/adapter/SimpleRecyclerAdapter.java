package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.kaleido.cesmarttracker.EnrollActivity;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Section;

import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.VersionViewHolder> {
    List<Course> courses;
    Context context;
    OnItemClickListener clickListener;
    int i=1;
    int suggested = 0;
    int color,dark_color;
    RoundCornerProgressBar roundCornerProgressBar;

    public SimpleRecyclerAdapter(List<Course> courses, EnrollActivity enrollActivity, int suggested) {
        this.courses = courses;
        this.context = enrollActivity;
        this.suggested = suggested;
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
        //versionViewHolder.subTitle.setText(courses.get(i).getCategory());
        versionViewHolder.subTitle.setText(courses.get(i).getClassDay());
        if(i<suggested)
            versionViewHolder.recommended.setText("(recommended)");
        // roundCornerProgressBar.setMax(courses.get(i).getAllSection().get(0).getMaxSeat());
        // roundCornerProgressBar.setProgress((courses.get(i).getAllSection().get(0).getMaxSeat())-(courses.get(i).getAllSection().get(0).getAvailableSeat()));
        int takenSeat = 0, maxSeat = 0;
        for(Section sec : courses.get(i).getSections()) {
            takenSeat += sec.getTakenSeat();
            maxSeat += sec.getMaxSeat();
        }
        roundCornerProgressBar.setMax(maxSeat);
        roundCornerProgressBar.setProgress(takenSeat);
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView title;
        TextView subTitle;
        TextView recommended;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.listitem_name);
            subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);
            roundCornerProgressBar = (RoundCornerProgressBar)itemView.findViewById(R.id.maxseat_progress_bar);
            recommended = (TextView)itemView.findViewById(R.id.recommended);

            itemView.setOnClickListener(this);

            switch (i%8){
                case 1:
                    color = (R.color.course_red1);
                    dark_color = (R.color.course_red1_dark);
                    break;
                case 2:
                    color = (R.color.course_orange1);
                    dark_color = (R.color.course_orange1_dark);
                    break;
                case 3:
                    color = (R.color.course_green1);
                    dark_color = (R.color.course_green1_dark);
                    break;
                case 4:
                    color = (R.color.course_green2);
                    dark_color = (R.color.course_green2_dark);
                    break;
                case 5:
                    color = (R.color.course_skyblue1);
                    dark_color = (R.color.course_skyblue1_dark);
                    break;
                case 6:
                    color = (R.color.course_blue1);
                    dark_color = (R.color.course_blue1_dark);
                    break;
                case 7:
                    color = (R.color.course_light_purple1);
                    dark_color = (R.color.course_light_purple1_dark);
                    break;
                case 0:
                    color = (R.color.course_purple1);
                    dark_color = (R.color.course_purple1_dark);
                    break;

            }




            cardItemLayout.setBackgroundResource(color);
            roundCornerProgressBar.setProgressColor(context.getResources().getColor(color));
            roundCornerProgressBar.setProgressBackgroundColor(context.getResources().getColor(dark_color));
            roundCornerProgressBar.setPadding(10);
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
        i = 1;

        this.clickListener = itemClickListener;
    }

}
