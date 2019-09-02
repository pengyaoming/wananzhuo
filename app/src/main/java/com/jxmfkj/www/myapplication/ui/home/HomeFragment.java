package com.jxmfkj.www.myapplication.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.SwEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.adapter.NewsTitlesAdapter;
import com.jxmfkj.www.myapplication.api.RetrofitUitl;
import com.jxmfkj.www.myapplication.ui.home.news.NewsFragment;
import com.jxmfkj.www.myapplication.ui.mine.MineFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private List<String> list = new ArrayList<>();
    private List<Integer> entity = new ArrayList<>();
    private HomeAdapter adapter;
    private NewsTitlesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null, false);
        magicIndicator = v.findViewById(R.id.MagicIndicator);
        viewPager = v.findViewById(R.id.viewPager);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initClick();
    }

    private void initClick() {

    }


    private void initView() {
        viewPager.setOffscreenPageLimit(3);
        //获取公众号数据
        RetrofitUitl.getInstance().Api()
                .getChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<SwEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<SwEntity>> listBaseResponse) {
                        if (listBaseResponse.getCode() != 0) {
                            Toast.makeText(getActivity(), "数据错误", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            for (int i = 0; i < listBaseResponse.getData().size(); i++) {
                                list.add(listBaseResponse.getData().get(i).getName());
                                entity.add(listBaseResponse.getData().get(i).getId());
                            }
                            mAdapter.getAll().clear();
                            mAdapter.setData(list);
                            initFragment();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        initTab();
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mAdapter = new NewsTitlesAdapter();
        mAdapter.setOnItemClickListener(new NewsTitlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                viewPager.setCurrentItem(index, true);
            }
        });
        commonNavigator.setAdapter(mAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initFragment() {
        List<Fragment> entitls = new ArrayList<>();
        adapter = new HomeAdapter(getFragmentManager(), entitls);
        for (int i = 0; i < entity.size(); i++) {
            Fragment fragment = null;
            if (!TextUtils.isEmpty(list.get(i))) {
                fragment = NewsFragment.getInstance(entity.get(i));
            }
            if (fragment != null) {
                entitls.add(fragment);
            }
        }
        adapter.setData(entitls);
        viewPager.setAdapter(adapter);
    }

}
