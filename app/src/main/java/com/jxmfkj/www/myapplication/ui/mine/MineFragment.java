package com.jxmfkj.www.myapplication.ui.mine;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxmfkj.www.myapplication.R;

public class MineFragment extends Fragment {
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, null, false);
        return v;
    }

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }


}
