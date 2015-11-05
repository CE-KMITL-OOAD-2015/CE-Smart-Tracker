package com.kaleido.cesmarttracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Review;

import java.util.ArrayList;

/**
 * Created by monkiyes on 11/5/2015 AD.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        Course c;
        ArrayList<Review> review;

        public CommentAdapter(Course course) {
            super();
            this.c=course;
            review = c.getAllReview();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_list_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Review r = review.get(i);
            viewHolder.comment.setText(r.getComment());
            viewHolder.rate.setText(r.getRate());
        }

        @Override
        public int getItemCount() {

            return review.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public TextView rate;
            public TextView comment;

            public ViewHolder(View itemView) {
                super(itemView);
                //imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
                comment = (TextView)itemView.findViewById(R.id.rate_show);
                rate = (TextView)itemView.findViewById(R.id.comment_show);
            }
        }

}
