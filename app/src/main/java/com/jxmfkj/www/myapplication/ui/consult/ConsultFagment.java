package com.jxmfkj.www.myapplication.ui.consult;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.jxmfkj.www.myapplication.Entity.BannerEntity;
import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.ConsultEntity;
import com.jxmfkj.www.myapplication.Entity.CssEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUitl;
import com.jxmfkj.www.myapplication.ui.WebViewActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConsultFagment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ConsultEntity> uris;
    private ConsultAdapter mAdapter;
    private int page = 0;
    private int pageSize = 20;
    private boolean isErr = false;
    private int TOTAL_COUNTER = 21;
    private boolean isFish = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_consult, null, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        return v;
    }

    public static ConsultFagment getInstance() {
        ConsultFagment fragment = new ConsultFagment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initBanner();
        initData();
        onClick();
    }

    private void onClick() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", mAdapter.getItem(position).getLink());
                intent.putExtra("name", mAdapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
    }


    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ConsultAdapter();
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setNewData(uris);
                        loadData(false);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        //上拉加载
        isFeish();
    }

    private void initBanner() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, (ViewGroup) recyclerView.getParent(), false);
        final Banner banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        RetrofitUitl.getInstance().Api()
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<BannerEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<List<BannerEntity>> bannerEntityBaseResponse) {
                        if (bannerEntityBaseResponse.getCode() != 0) {
                            Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            List<String> images = new ArrayList<>();
                            List<String> titles = new ArrayList<>();
                            for (int i = 0; i < bannerEntityBaseResponse.getData().size(); i++) {
                                images.add(bannerEntityBaseResponse.getData().get(i).getImagePath());
                                titles.add(bannerEntityBaseResponse.getData().get(i).getTitle());
                            }
                            banner.setImages(images).setBannerTitles(titles).setDelayTime(3000).isAutoPlay(true).start();
                        }
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mAdapter.addHeaderView(view);
    }

    private void initData() {
        loadData(false);
        recyclerView.setAdapter(mAdapter);

    }

    private void loadData(boolean isFish) {
        if (!isFish) {
            page = 0;
        }
        RetrofitUitl.getInstance().Api()
                .getConsult(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<CssEntity<List<ConsultEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CssEntity<List<ConsultEntity>>> listBaseResponse) {
                        mAdapter.addData(listBaseResponse.getData().getDatas());
                        pageSize = listBaseResponse.getData().getDatas().size();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void isFeish() {
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pageSize >= TOTAL_COUNTER) {
                            mAdapter.loadMoreEnd();
                        } else {
                            if (!isErr) {
                                page++;
                                loadData(true);
                                mAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败 PullToRefreshUseActivity
                                isErr = true;
                                Toast.makeText(getActivity(), "获取更多数据失败", Toast.LENGTH_LONG).show();
                                mAdapter.loadMoreFail();
                            }
                        }
                    }
                }, 500);
            }
        }, recyclerView);
    }

}
