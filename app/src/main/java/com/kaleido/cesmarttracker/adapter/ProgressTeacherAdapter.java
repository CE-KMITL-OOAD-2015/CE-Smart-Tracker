package com.kaleido.cesmarttracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Course;

import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class ProgressTeacherAdapter extends RecyclerView.Adapter<ProgressTeacherAdapter.VersionViewHolder> {
    List<Course> courses;
    OnItemClickListener clickListener;

    public ProgressTeacherAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card_layout, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        versionViewHolder.courseName.setText(courses.get(i).getName());
        versionViewHolder.courseID.setText("Course ID : "+courses.get(i).getId());
        versionViewHolder.courseCategory.setText("Course Category : "+courses.get(i).getCategory());
        versionViewHolder.students.setText(courses.get(i).getStudentAmount()+" Students.");
        versionViewHolder.sections.setText(courses.get(i).getSections().size()+" Sections.");
        versionViewHolder.teachers.setText(courses.get(i).getTeacherAmount()+ " Teachers.");
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout cardItemLayout;
        TextView courseID;
        TextView courseCategory;
        TextView courseName;
        TextView students;
        TextView sections;
        TextView teachers;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (LinearLayout) itemView.findViewById(R.id.cardView);
            courseID = (TextView) itemView.findViewById(R.id.course_id_progress_teacher);
            courseName = (TextView) itemView.findViewById(R.id.course_name_progress_teacher);
            courseCategory = (TextView) itemView.findViewById(R.id.course_category_progress_teacher);
            students = (TextView) itemView.findViewById(R.id.student_amount);
            sections = (TextView) itemView.findViewById(R.id.section_amount);
            teachers = (TextView) itemView.findViewById(R.id.teacher_amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}