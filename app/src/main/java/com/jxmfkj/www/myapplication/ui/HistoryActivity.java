package com.jxmfkj.www.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.HistoryEntity;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.adapter.HistoryAdapter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 公众号历史记录
 */
public class HistoryActivity extends RxAppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private int page = 0;
    private int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initData();
        entite();
    }


    private void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(new ArrayList<HistoryEntity.DataBean.DatasBean>());
                        entite();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(HistoryActivity.this, "刷新成功", Toast.LENGTH_LONG).show();
                    }
                }, 1000);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(HistoryActivity.this.adapter.getItem(position).getLink())));
            }
        });
    }

    private void entite() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiServer request = retrofit.create(ApiServer.class);
        Call<HistoryEntity> call = request.getHistCall(id + "", page + "");
        call.enqueue(new Callback<HistoryEntity>() {
            @Override
            public void onResponse(Call<HistoryEntity> call, Response<HistoryEntity> response) {

                adapter.addData(response.body().getData().getDatas());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<HistoryEntity> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "请求失败", Toast.LENGTH_LONG).show();

            }
        });
    }

}
