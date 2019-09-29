package com.jxmfkj.www.myapplication.ui.consult;

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
import com.jxmfkj.www.myapplication.Entity.BannerEntity;
import com.jxmfkj.www.myapplication.Entity.ConsultEntity;
import com.jxmfkj.www.myapplication.Entity.CssEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.jxmfkj.www.myapplication.base.BaseFragment;
import com.jxmfkj.www.myapplication.ui.WebViewActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 首页
 *
 * @author peng
 */
public class ConsultFagment extends BaseFragment {
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


    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_consult, null, false);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ConsultAdapter();
        recyclerView.setAdapter(mAdapter);
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
        loadData(false);
        initBanner();
        //上拉加载
        isFeish();
        onClick();

    }

    //点击事件
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
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_collection:
                        int id = mAdapter.getItem(position).getId();
                        Collection(id);
                }
            }
        });
    }

    //点击收藏
    private void Collection(int id) {
        RetrofitUtil.getInstance(getContext()).create(ApiServer.class)
                .getCilckCollection(id + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        if (baseEntity.getErrorCode() != 0) {
                            showMessage(baseEntity.getErrorMsg());
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


    //获取banner
    private void initBanner() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, (ViewGroup) recyclerView.getParent(), false);
        final Banner banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        RetrofitUtil.getInstance(getContext()).create(ApiServer.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<BannerEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseEntity<List<BannerEntity>> bannerEntityBaseEntity) {
                        if (bannerEntityBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            List<String> images = new ArrayList<>();
                            List<String> titles = new ArrayList<>();
                            for (int i = 0; i < bannerEntityBaseEntity.getData().size(); i++) {
                                images.add(bannerEntityBaseEntity.getData().get(i).getImagePath());
                                titles.add(bannerEntityBaseEntity.getData().get(i).getTitle());
                            }
                            banner.setImages(images).setBannerTitles(titles).setDelayTime(3000).isAutoPlay(true).start();
                        }

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

    //获取首页数据
    private void loadData(boolean isFish) {
        if (!isFish) {
            page = 0;
            Top();
        }
        RetrofitUtil.getInstance(getContext())
                .create(ApiServer.class)
                .getConsult(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<CssEntity<List<ConsultEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<CssEntity<List<ConsultEntity>>> listBaseEntity) {
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

    //上啦加载
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

    /**
     * 获取顶部数据
     */
    public void Top() {
        RetrofitUtil.getInstance(getContext()).create(ApiServer.class)
                .getTop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<ConsultEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<ConsultEntity>> listBaseEntity) {
                        if (listBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(getActivity(), listBaseEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAdapter.addData(listBaseEntity.getData());
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
