//package com.jxmfkj.www.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.Toast;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.jxmfkj.www.myapplication.Entity.SwEntity;
//import com.jxmfkj.www.myapplication.adapter.SwAdapter;
//import com.jxmfkj.www.myapplication.api.ApiServer;
//import com.jxmfkj.www.myapplication.ui.HistoryActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class SwActivity extends AppCompatActivity {
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private RecyclerView recyclerView;
//    private SwAdapter adapter;
//    List<SwEntity> list = new ArrayList<>();
//    private int id = 0;
//    int page;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sw);
//        initView();
//        initClick();
//
//    }
//
//    private void initClick() {
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                Intent intent = new Intent(SwActivity.this, HistoryActivity.class);
//                intent.putExtra("id", SwActivity.this.adapter.getItem(position).getId());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initView() {
//        swipeRefreshLayout = findViewById(R.id.swipe);
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        adapter = new SwAdapter();
//        initData();
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Handler mHandler = new Handler();
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.setNewData(new ArrayList<SwEntity.DataBean>());
//                        initData();
//                        swipeRefreshLayout.setRefreshing(false);
//                        Toast.makeText(SwActivity.this, "刷新成功", Toast.LENGTH_LONG).show();
//                    }
//                }, 1000);
//            }
//        });
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//    }
//
//    private void initData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        final ApiServer request = retrofit.create(ApiServer.class);
//
//        Call<SwEntity> call = request.getSwCall();
//        call.enqueue(new Callback<SwEntity>() {
//            @Override
//            public void onResponse(Call<SwEntity> call, Response<SwEntity> response) {
//                adapter.addData(response.body().getData());
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<SwEntity> call, Throwable t) {
//            }
//        });
//    }
//}
