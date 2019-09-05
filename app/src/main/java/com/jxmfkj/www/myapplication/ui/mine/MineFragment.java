package com.jxmfkj.www.myapplication.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.LoginEntity;
import com.jxmfkj.www.myapplication.Entity.MineEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.ui.mine.collection.CollectionActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MineFragment extends Fragment {
    private int id;
    private LoginEntity entity;
    private LinearLayout liner;
    private ImageView user_img;
    private TextView tvName;
    private TextView tvId;
    private RecyclerView recyclerView;
    private MineAdapter adapter;
    private MineEntity cssa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        liner = v.findViewById(R.id.liner);
        user_img = v.findViewById(R.id.user_img);
        tvName = v.findViewById(R.id.tvName);
        recyclerView = v.findViewById(R.id.recyclerView);
        tvId = v.findViewById(R.id.tvId);
        return v;
    }

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "null");
        int id = sharedPreferences.getInt("id", 0);
        tvName.setText(name + "");
        tvId.setText(id + "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final List<MineEntity> list = new ArrayList<>();
        list.add(new MineEntity("收藏", R.drawable.ic_collections_black_24dp));
        list.add(new MineEntity("稍后阅读", R.drawable.ic_chrome_reader_mode_black_24dp));
        list.add(new MineEntity("开源项目", R.drawable.ic_open_with_black_24dp));
        list.add(new MineEntity("关于我们", R.drawable.ic_guanyu));
        list.add(new MineEntity("设置", R.drawable.ic_settings_black_24dp));
        adapter = new MineAdapter(R.layout.item_sw, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0 && list.get(position).getName() == "收藏") {
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                }
            }
        });

    }

}
