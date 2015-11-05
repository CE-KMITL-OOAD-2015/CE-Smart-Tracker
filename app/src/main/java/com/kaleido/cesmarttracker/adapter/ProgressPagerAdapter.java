package com.kaleido.cesmarttracker.adapter;

/**
 * Created by monkiyes on 10/21/2015 AD.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kaleido.cesmarttracker.fragment.Progress1;
import com.kaleido.cesmarttracker.fragment.Progress2;

import java.util.Locale;

public class ProgressPagerAdapter extends FragmentStatePagerAdapter {
    public ProgressPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                Progress1 p1 = new Progress1();
                return p1;
            case 1:
                Progress2 p2 = new Progress2();
                return p2;
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
                return "Categories";
            case 1:

                return "Members";
        }
        return super.getPageTitle(position);
    }

}
