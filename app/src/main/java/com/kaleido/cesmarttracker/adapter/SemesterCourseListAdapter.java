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
import com.kaleido.cesmarttracker.data.Student;

import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class SemesterCourseListAdapter extends RecyclerView.Adapter<SemesterCourseListAdapter.VersionViewHolder> {
    List<Course> courses;
    Context context;
    String semester;
    Student s1;
    OnItemClickListener clickListener;
    int i=0;
    int color;

    public SemesterCourseListAdapter(List<Course> courses,Student s1,String semester) {
        this.courses = courses;
        this.s1 = s1;
        this.semester = semester;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_list_semester, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        String grade = getGrade(s1.getTranscript().getGrade(semester, courses.get(i)));
        versionViewHolder.grade.setText(grade);
        versionViewHolder.courseName.setText(courses.get(i).getName());
    }

    private String getGrade(double grade) {
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

        public VersionViewHolder(View itemView) {
            super(itemView);

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
