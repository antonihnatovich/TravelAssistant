package com.example.yoant.travelassistant.adapters.view_pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yoant.travelassistant.ui.fragments.RepresentationFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int COUNT = 6;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RepresentationFragment().newInstance("all");
            case 1:
                return new RepresentationFragment().newInstance("africa");
            case 2:
                return new RepresentationFragment().newInstance("americas");
            case 3:
                return new RepresentationFragment().newInstance("asia");
            case 4:
                return new RepresentationFragment().newInstance("europe");
            default:
                return new RepresentationFragment().newInstance("oceania");
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Africa";
            case 2:
                return "Americas";
            case 3:
                return "Asia";
            case 4:
                return "Europe";
            default:
                return "Oceania";
        }
    }
}
