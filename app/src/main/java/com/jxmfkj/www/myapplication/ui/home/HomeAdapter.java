package com.jxmfkj.www.myapplication.ui.home;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;


public class HomeAdapter extends FragmentStatePagerAdapter {
    protected List<Fragment> fragments;


    public HomeAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    public void setData(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void add(Fragment fragment) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public List<Fragment> getAll() {
        return fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}