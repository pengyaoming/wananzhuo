package com.jxmfkj.www.myapplication.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.MineEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.ui.login.LoginActivity;
import com.jxmfkj.www.myapplication.ui.mine.collection.CollectionActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 我的
 * @author peng
 */
public class MineFragment extends RxFragment {
    private LinearLayout liner;
    private ImageView user_img;
    private TextView tvName;
    private TextView tvId;
    private RecyclerView recyclerView;
    private MineAdapter adapter;

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
        initClick();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initClick() {
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvName.getText()) && TextUtils.isEmpty(tvId.getText())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 200);
                }
            }
        });
    }
    private void initView() {
        SharedPreferences sp = getContext().getSharedPreferences("cookie_prefs", MODE_PRIVATE);
        String username = sp.getString("name", "");
        String id = sp.getString("id", "");
        if (!TextUtils.isEmpty(id)) {
            tvName.setText(username);
            tvId.setText(id);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 15) {
            if (requestCode == 200) {
                int id = data.getIntExtra("linkId", 0);
                String name = data.getStringExtra("name");
                tvName.setText(name + "");
                tvId.setText(id + "");
            }
        }
    }
}
