package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.UserLocalStore;
import com.kaleido.cesmarttracker.adapter.ProgressTeacherAdapter;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Teacher;

import java.util.ArrayList;

/**
 * Created by monkiyes on 10/29/2015 AD.
 */
public class ProgressTeacherFragment extends Fragment {
    RecyclerView recyclerView1;
    Test test = new Test();
    Teacher a = test.getTeachers().get(0);
    ArrayList<Course> courses = a.getCourses();
    LinearLayoutManager mLayoutManager;
    ProgressTeacherAdapter progressTeacherAdapter;
    UserLocalStore userLocalStore;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.activity_teacher_progress,container,false);
        userLocalStore = new UserLocalStore(getContext());
        a = userLocalStore.getTeacherData();
        courses = a.getCourses();

        recyclerView1 = (RecyclerView)view.findViewById(R.id.progress_teacher_list_view);
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(mLayoutManager);

        if (progressTeacherAdapter == null) {
            progressTeacherAdapter = new ProgressTeacherAdapter(courses);
            recyclerView1.setAdapter(progressTeacherAdapter);
        }
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        progressTeacherAdapter.setOnItemClickListener(new ProgressTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Fragment progressFragment = new ProgressTeacherTabFragment(courses.get(position));
                ft.replace(R.id.flContent, progressFragment);
                ft.commit();
            }
        });

        return  view;
    }


}