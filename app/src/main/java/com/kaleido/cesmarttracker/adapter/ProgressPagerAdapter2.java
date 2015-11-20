package com.kaleido.cesmarttracker.adapter;

/**
 * Created by monkiyes on 10/21/2015 AD.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kaleido.cesmarttracker.fragment.Progress2;
import com.kaleido.cesmarttracker.fragment.Progress4;
import com.kaleido.cesmarttracker.data.Course;

import java.util.Locale;

public class ProgressPagerAdapter2 extends FragmentStatePagerAdapter {
    Course c;
    public ProgressPagerAdapter2(FragmentManager fm,Course c){
        super(fm);
        this.c = c;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                Progress2 p2 = new Progress2();
                return p2;
            case 1:
                Progress4 p4 = new Progress4(c);
                return p4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "ALL";
            case 1:

                return "Students";
        }
        return super.getPageTitle(position);
    }

}