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
import com.kaleido.cesmarttracker.adapter.TabsPagerAdapter;


public class ProgressFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_progress,container,false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getActivity().getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);



        return  view;
    }

}

//package com.kaleido.cesmarttracker.fragment;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.PagerTabStrip;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.kaleido.cesmarttracker.adapter.PagerAdapter;
//import com.kaleido.cesmarttracker.R;
//
//
//public class ProgressFragment extends Fragment {
//
//    ViewPager pager;
//    PagerTabStrip tab_strp;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view=inflater.inflate(R.layout.activity_progress,container,false);
//        PagerAdapter mapager = new PagerAdapter(getActivity().getSupportFragmentManager());
//        pager=(ViewPager)view.findViewById(R.id.pager);
//
//        pager.setAdapter(mapager);
//        tab_strp=(PagerTabStrip)view.findViewById(R.id.tab_strip);
//        tab_strp.setTextColor(Color.WHITE);
//        return  view;
//    }
//
//}