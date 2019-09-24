package com.jxmfkj.www.myapplication.ui.home.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.SearchEntity;
import com.jxmfkj.www.myapplication.Entity.SearchListEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 搜索
 * @author peng
 */
public class SearchActivity extends RxAppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private EditText editText;
    private SearchAdapter adapter;
    private int page = 0;
    private int pageSize = 20;
    private boolean isErr = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.click_img);
        editText = findViewById(R.id.edtSearch);
        initView();
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<SearchListEntity> listEntities = new ArrayList<>();
                        adapter.setNewData(listEntities);
                        page = 0;
                        retrofit(editText.getText().toString() + "");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        recyclerView.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        isFish();
    }


    private void initData() {
        String edtText = editText.getText().toString();
        if (TextUtils.isEmpty(edtText)) {
            Toast.makeText(SearchActivity.this, "关键字为空", Toast.LENGTH_LONG).show();
            return;
        } else {
            List<SearchListEntity> listEntities = new ArrayList<>();
            adapter.setNewData(listEntities);
            retrofit(edtText);
        }

    }

    private void retrofit(String edtText) {
        RetrofitUtil.getInstance(this)
                .create(ApiServer.class)
                .getSearch(page + "", edtText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchEntity<List<SearchListEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<SearchEntity<List<SearchListEntity>>> s) {
                        adapter.addData(s.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void isFish() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int TOTAL_COUNTER = 21;
                        if (pageSize > TOTAL_COUNTER) {
                            adapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                page++;
                                retrofit(editText.getText().toString() + "");
                                adapter.loadMoreComplete();
                            } else {
                                isErr = true;
                                Toast.makeText(SearchActivity.this, "获取失败", Toast.LENGTH_LONG).show();
                                adapter.loadMoreFail();

                            }
                        }
                    }
                }, 500);
            }
        }, recyclerView);
    }
}
