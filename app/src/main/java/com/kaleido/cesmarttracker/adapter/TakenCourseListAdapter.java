package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class TakenCourseListAdapter extends RecyclerView.Adapter<TakenCourseListAdapter.VersionViewHolder> {
    List<Course> courses;
    ArrayList<String> semesters;
    Context context;
    OnItemClickListener clickListener;
    View view;
    Student s;
    UserLocalStore userLocalStore;

    public TakenCourseListAdapter(List<Course> courses,ArrayList<String> semesters,Context context) {
        this.semesters = semesters;
        this.courses = courses;
        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_list_taken, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        userLocalStore = new UserLocalStore(context);
        s = userLocalStore.getStudentData();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        String gradeString = getStringGrade(s.getTranscript().getGrade(semesters.get(i), courses.get(i)));
        versionViewHolder.grade.setText(gradeString);
        versionViewHolder.courseName.setText(courses.get(i).getName());

        ImageView courseType = (ImageView)view.findViewById(R.id.course_type);
        if(!courses.get(i).getId().substring(0,2).contentEquals("90")){ //major
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#d7c6cf"));
        } else if(courses.get(i).getId().substring(0,3).contentEquals("902")){ //language
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#8caba8"));
        } else if(courses.get(i).getId().substring(0,3).contentEquals("903")){ //human
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#6fa6bc"));
        } else if(courses.get(i).getId().substring(0,3).contentEquals("906")){ //selective //TODO: selective still not right
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#f0d0cb"));
        } else if(courses.get(i).getId().substring(0,3).contentEquals("904")){ //social
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#c39e45"));
        } else if(courses.get(i).getId().substring(0,3).contentEquals("901")){ //science
            courseType.setBackgroundResource(R.drawable.circle);
            final GradientDrawable drawable = (GradientDrawable) courseType.getBackground();
            drawable.setColor(Color.parseColor("#d94444"));
        }

    }

    private String getStringGrade(double grade) {
        if(grade>0 && grade<1){
            return "F";
        }else if(grade>=1 && grade<1.5){
            return "D";
        }else if(grade>=1.5 && grade<2){
            return "D+";
        }else if(grade>=2 && grade<2.5){
            return "C";
        }else if(grade>=2.5 && grade<3){
            return "C+";
        }else if(grade>=3 && grade<3.5){
            return "B";
        }else if(grade>=3.5 && grade<4){
            return "B+";
        }else if(grade==4){
            return  "A";
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView grade;
        TextView courseName;
        ImageView courseType;

        public VersionViewHolder(View itemView) {
            super(itemView);

            courseType = (ImageView) itemView.findViewById(R.id.course_type);
            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            grade = (TextView) itemView.findViewById(R.id.grade);
            courseName = (TextView) itemView.findViewById(R.id.course_name_semester);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
