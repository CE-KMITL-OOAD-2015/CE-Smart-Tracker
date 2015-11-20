package com.kaleido.cesmarttracker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kaleido.cesmarttracker.fragment.Progress1;
import com.kaleido.cesmarttracker.fragment.Progress2;
import com.kaleido.cesmarttracker.fragment.Progress3;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Category", "GPA", "Taken Course" };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Progress1 p1 = new Progress1();
                return p1;
            case 1:
                Progress2 p2 = new Progress2();
                return p2;
            case 2:
                Progress3 p3 = new Progress3();
                return p3;
        }
        return new Progress1();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
