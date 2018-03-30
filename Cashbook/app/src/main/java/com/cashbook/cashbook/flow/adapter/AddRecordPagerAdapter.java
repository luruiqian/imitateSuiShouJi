package com.cashbook.cashbook.flow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luruiqian on 2018/3/30.
 */

public class AddRecordPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabIndicators = new ArrayList<>();

    public AddRecordPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabIndicators) {
        super(fm);
        this.fragments = fragments;
        this.tabIndicators = tabIndicators;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return (fragments != null && fragments.size() > 0) ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position);
    }
}
