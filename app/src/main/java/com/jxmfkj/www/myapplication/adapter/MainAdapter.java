package com.jxmfkj.www.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.R;


import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentStatePagerAdapter {
    private List<String> titles;
    private List<Fragment> fragments;
    private List<Integer> icons;
    private Context mContext;

    public MainAdapter(FragmentManager fm,  List<Integer> icons, List<String> titles, Context mContext) {
        super(fm);

        this.icons = icons;
        this.mContext = mContext;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return MainAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setData(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public View getTabVie(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_main, null);
        TextView tv = v.findViewById(R.id.tv_tab);
        tv.setText(titles.get(position));
        ImageView image = v.findViewById(R.id.icon_image);
        image.setImageResource(icons.get(position));
        return v;
    }


}
