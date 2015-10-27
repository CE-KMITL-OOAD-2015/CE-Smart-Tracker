package com.kaleido.cesmarttracker;

/**
 * Created by monkiyes on 10/21/2015 AD.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
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
            case 2:
                Progress3 p3 = new Progress3();
                return p3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Categories";
            case 1:

                return "Members";
            case 2:

                return "Setting";
        }
        return null;
    }



}
