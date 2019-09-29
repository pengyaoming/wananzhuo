package com.jxmfkj.www.myapplication.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.MessageEvent;
import com.jxmfkj.www.myapplication.Entity.MineEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.base.BaseFragment;
import com.jxmfkj.www.myapplication.ui.login.LoginActivity;
import com.jxmfkj.www.myapplication.ui.mine.collection.CollectionActivity;
import com.jxmfkj.www.myapplication.ui.mine.install.InstallActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * 我的
 *
 * @author peng
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.tvId)
    TextView tvId;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.linLogin)
    LinearLayout linLogin;
    @BindView(R.id.linId)
    LinearLayout linId;
    @BindView(R.id.linName)
    LinearLayout linName;
    private MineAdapter adapter;
    private final List<MineEntity> list = new ArrayList<>();
    private boolean isErr = false;


    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null, false);
    }

    @Override
    protected void initData() {
        linId.setVisibility(View.GONE);
        linName.setVisibility(View.GONE);
        linLogin.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.add(new MineEntity("收藏", R.drawable.ic_collections_black_24dp));
        list.add(new MineEntity("稍后阅读", R.drawable.ic_chrome_reader_mode_black_24dp));
        list.add(new MineEntity("开源项目", R.drawable.ic_open_with_black_24dp));
        list.add(new MineEntity("关于我们", R.drawable.ic_guanyu));
        list.add(new MineEntity("设置", R.drawable.ic_settings_black_24dp));
        adapter = new MineAdapter(R.layout.item_sw, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        SharedPreferences sp = getContext().getSharedPreferences("sp", MODE_PRIVATE);
        String username = sp.getString("name", "");
        int id = sp.getInt("id", 0);
        if (!TextUtils.isEmpty(username)) {
            tvName.setText(username + "");
            tvId.setText(id + "");
            isErr = true;
            linId.setVisibility(View.VISIBLE);
            linName.setVisibility(View.VISIBLE);
            linLogin.setVisibility(View.GONE);
        }
        onClick();
    }

    @OnClick({R.id.user_img, R.id.linLogin, R.id.linId, R.id.linName})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.user_img:
                startActivityForResult(new Intent(getContext(), LoginActivity.class), 200);
                break;
            case R.id.linLogin:
                startActivityForResult(new Intent(getContext(), LoginActivity.class), 200);
                break;
            case R.id.linId:
                break;
            case R.id.linName:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 15) {
            if (requestCode == 200) {
                linId.setVisibility(View.VISIBLE);
                linName.setVisibility(View.VISIBLE);
                linLogin.setVisibility(View.GONE);
                int id = data.getIntExtra("linkId", 0);
                String name = data.getStringExtra("name");
                tvName.setText(name + "");
                tvId.setText(id + "");
                isErr = true;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG", "onstop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG", "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getContext().getSharedPreferences("sp", MODE_PRIVATE);
        if (!TextUtils.isEmpty(sp.getString("name", ""))) {
            tvName.setText(sp.getString("name", "") + "");
            tvId.setText(sp.getInt("id", 0) + "");
            isErr = true;
        }

    }

    private void onClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list == null) {
                    return;
                }

                if (list.get(position).getName() == "收藏") {
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                } else if (list.get(position).getName() == "稍后阅读") {

                } else if (list.get(position).getName() == "开源项目") {

                } else if (list.get(position).getName() == "关于我们") {

                } else if (list.get(position).getName() == "设置") {
                    startActivity(new Intent(getActivity(), InstallActivity.class)
                            .putExtra("isErr", isErr)
                    );
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        isErr = messageEvent.isErr();
        if (!isErr) {
            SharedPreferences sp = getContext().getSharedPreferences("sp", MODE_PRIVATE);
            sp.edit().clear().commit();
            tvName.setText(sp.getString("name", "") + "");
            tvId.setText(sp.getString("id", "") + "");
        }
    }


}
