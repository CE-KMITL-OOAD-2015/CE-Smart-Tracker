package com.kaleido.cesmarttracker.adapter;

/**
 * Created by monkiyes on 10/21/2015 AD.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.fragment.ProgressTeacherFragment1;
import com.kaleido.cesmarttracker.fragment.ProgressTeacherFragment2;

public class ProgressPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Category", "GPA" };
    Course c;
    public ProgressPagerAdapter(FragmentManager fm,Course c){
        super(fm);
        this.c = c;
    }
    public ProgressPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                ProgressTeacherFragment1 p1 = new ProgressTeacherFragment1(c);
                return p1;
            case 1:
                ProgressTeacherFragment2 p2 = new ProgressTeacherFragment2(c);
                return p2;
//            case 2:
//                Progress3 p2 = new Progress2();
//                return p2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
