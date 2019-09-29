package com.jxmfkj.www.myapplication.ui.mine.collection;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.CollectionEntity;
import com.jxmfkj.www.myapplication.Entity.SearchEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseActivity;
import com.jxmfkj.www.myapplication.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 收藏
 */
public class CollectionActivity extends BaseActivity {
    private CollectionAdapter adapter;
    private int page = 0;
    private int pageSize = 0;
    private boolean isErr = true;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.left_img)
    ImageView left_img;

    @OnClick(R.id.left_img)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_img:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {
        tvTitle.setText("收藏");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        List<CollectionEntity> list = new ArrayList<>();
                        adapter.setNewData(list);
                        initAdapter(page);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
        left_img.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter();
        isFish();
    }

    private void isFish() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int TATAL_COUNTER = 21;
                        if (pageSize >= TATAL_COUNTER) {
                            adapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                page++;
                                initAdapter(page);
                                adapter.loadMoreComplete();

                            } else {
                                isErr = true;
                                Message("获取跟多数据失败");
                                adapter.loadMoreFail();
                            }
                        }
                    }
                }, 500);
            }
        }, recyclerView);
    }

    @Override
    public void initData() {
        recyclerView.setAdapter(adapter);
        initAdapter(page);

    }

    private void initAdapter(int page) {
        RetrofitUtil.getInstance(this).create(ApiServer.class)
                .getCollection(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<SearchEntity<List<CollectionEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<SearchEntity<List<CollectionEntity>>> searchEntityBaseEntity) {
                        if (searchEntityBaseEntity.getErrorCode() != 0) {
                            Message(searchEntityBaseEntity.getErrorMsg());
                            return;
                        }
                        adapter.addData(searchEntityBaseEntity.getData().getDatas());
                        pageSize = searchEntityBaseEntity.getData().getSize();
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
