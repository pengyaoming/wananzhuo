package com.jxmfkj.www.myapplication.ui.home.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.Entity.NewsWEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.jxmfkj.www.myapplication.ui.WebViewActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 公众号列表
 *
 * @author peng
 */
public class NewsFragment extends RxFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int id;
    private int page = 0;
    private NewsAdapter adapter;
    private int pageSize = 20;
    private boolean isErr = false;
    private boolean collection = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, null, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsAdapter();
        initRetrofit(false);
        initView();
        onClick();
    }

    private void onClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", NewsFragment.this.adapter.getItem(position).getLink());
                intent.putExtra("name", NewsFragment.this.adapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    private void initRetrofit(boolean is) {
        if (!is) {
            page = 0;

        }
        RetrofitUtil.getInstance(getContext())
                .create(ApiServer.class)
                .getArticle(getArguments().getInt("id", id) + "", page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<NewsWEntity<List<NewsEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseEntity<NewsWEntity<List<NewsEntity>>> newsWEntityBaseEntity) {
                        if (newsWEntityBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(getActivity(), newsWEntityBaseEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            adapter.addData(newsWEntityBaseEntity.getData().getDatas());
                            pageSize = newsWEntityBaseEntity.getData().getDatas().size();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static NewsFragment getInstance(int id) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initView() {
        adapter = new NewsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK, Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        final List<NewsEntity> list = new ArrayList<>();
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(list);
                        initRetrofit(false);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 500);
            }
        });
        isFish();
    }

    private void isFish() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int TOTAL_COUNTER = 21;
                        if (pageSize >= TOTAL_COUNTER) {
                            adapter.loadMoreEnd();
                        } else {
                            if (!isErr) {
                                page++;
                                initRetrofit(true);
                                adapter.loadMoreComplete();
                            } else {
                                isErr = true;
                                Toast.makeText(getActivity(), "获取更多数据失败", Toast.LENGTH_LONG).show();
                                adapter.loadMoreFail();
                            }
                        }
                    }
                }, 500);
            }
        }, recyclerView);
    }
}
