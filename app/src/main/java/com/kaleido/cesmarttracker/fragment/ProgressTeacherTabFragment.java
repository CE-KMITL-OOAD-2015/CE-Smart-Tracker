package com.kaleido.cesmarttracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.adapter.ProgressPagerAdapter;
import com.kaleido.cesmarttracker.data.Course;

/**
 * Created by Touch on 11/19/2015.
 */
public class ProgressTeacherTabFragment extends Fragment {
    Course c;

    public ProgressTeacherTabFragment(Course c) {
        this.c = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_progress,container,false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ProgressPagerAdapter(getActivity().getSupportFragmentManager(),c));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        return  view;
    }
}
