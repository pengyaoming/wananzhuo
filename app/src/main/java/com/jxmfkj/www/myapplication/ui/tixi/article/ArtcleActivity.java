package com.jxmfkj.www.myapplication.ui.tixi.article;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.Entity.SearchEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.jxmfkj.www.myapplication.ui.WebViewActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtcleActivity extends RxAppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArtcleAdapter mAdapter;
    private TextView tvTitle;
    private ImageView lift_img;
    private int page = 0;
    private int pageSize = 0;
    private int id = 0;
    private String name;
    private boolean isErr = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artcle);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        initView();
        initData(page);
    }


    /*
    布局初始化
     */
    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(name);
        lift_img = findViewById(R.id.left_img);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArtcleAdapter();
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<NewsEntity> list = new ArrayList<>();
                        mAdapter.setNewData(list);
                        initData(0);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
        isFinish();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ArtcleActivity.this, WebViewActivity.class)
                        .putExtra("url", mAdapter.getItem(position).getLink())
                        .putExtra("name", mAdapter.getItem(position).getTitle()));
            }
        });
        lift_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void isFinish() {
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int TOTAL_COUNTER = 0;
                        if (pageSize != 0) {
                            TOTAL_COUNTER = pageSize + 1;
                        }
                        if (pageSize >= TOTAL_COUNTER) {
                            mAdapter.loadMoreEnd();
                        } else {
                            if (!isErr) {
                                page++;
                                initData(page);
                                mAdapter.loadMoreComplete();
                            } else {
                                isErr = true;
                                Toast.makeText(ArtcleActivity.this, "获取跟多数据失败", Toast.LENGTH_SHORT).show();
                                mAdapter.loadMoreFail();
                            }
                        }
                    }
                }, 500);
            }
        }, recyclerView);
    }

    /*
    接口访问数据
     */
    private void initData(int page) {
        RetrofitUtil.getInstance(this).create(ApiServer.class)
                .getArtcle(page + "", id + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<SearchEntity<List<NewsEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<SearchEntity<List<NewsEntity>>> listBaseEntity) {
                        if (listBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(ArtcleActivity.this, listBaseEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                        mAdapter.addData(listBaseEntity.getData().getDatas());
                        pageSize = listBaseEntity.getData().getDatas().size();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
