package com.jxmfkj.www.myapplication.ui.home.news;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.Entity.NewsWEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.RetrofitUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int id;
    private int page = 0;
    private NewsAdapter adapter;
    private int pageSize = 20;
    private boolean isErr = false;


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
    }

    private void initRetrofit(boolean is) {
        if (!is) {
            page = 0;
        }
        RetrofitUitl.getInstance().Api()
                .getArticle(getArguments().getInt("id", id) + "", page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<NewsWEntity<List<NewsEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<NewsWEntity<List<NewsEntity>>> newsWEntityBaseResponse) {
                        if (newsWEntityBaseResponse.getCode() != 0) {
                            Toast.makeText(getActivity(), "数据错误", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            adapter.addData(newsWEntityBaseResponse.getData().getDatas());
                            pageSize = newsWEntityBaseResponse.getData().getDatas().size();
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
