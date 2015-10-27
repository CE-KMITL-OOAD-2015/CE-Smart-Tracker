package com.kaleido.cesmarttracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class ProgressFragment extends Fragment {

    ViewPager pager;
    PagerTabStrip tab_strp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.activity_progress,container,false);
        PagerAdapter mapager = new PagerAdapter(getActivity().getSupportFragmentManager());
        pager=(ViewPager)view.findViewById(R.id.pager);

        pager.setAdapter(mapager);
        tab_strp=(PagerTabStrip)view.findViewById(R.id.tab_strip);
        tab_strp.setTextColor(Color.WHITE);
        return  view;
    }

}