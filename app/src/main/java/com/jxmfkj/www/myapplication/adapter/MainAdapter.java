package com.jxmfkj.www.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private ArrayList<Integer> icons;
    private Context mContext;

    public MainAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<Integer> icons, ArrayList<String> titles, Context mContext) {
        super(fm);
        this.fragments = fragments;
        this.icons = icons;
        this.mContext = mContext;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
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
