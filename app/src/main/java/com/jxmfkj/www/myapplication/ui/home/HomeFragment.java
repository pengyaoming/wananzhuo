package com.jxmfkj.www.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.SwEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.adapter.NewsTitlesAdapter;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.jxmfkj.www.myapplication.ui.home.news.NewsFragment;
import com.jxmfkj.www.myapplication.ui.home.search.SearchActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 公众号
 * @author peng
 */
public class HomeFragment extends RxFragment {
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private List<String> list = new ArrayList<>();
    private List<Integer> entity = new ArrayList<>();
    private HomeAdapter adapter;
    private NewsTitlesAdapter mAdapter;
    private Fragment fragment;
    private ImageView search_img;


    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null, false);
        magicIndicator = v.findViewById(R.id.MagicIndicator);
        viewPager = v.findViewById(R.id.viewPager);
        search_img = v.findViewById(R.id.search_img);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        onClick();
    }

    private void onClick() {
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

    }

    private void LozyLoad() {
        RetrofitUtil.getInstance(getContext())
                .create(ApiServer.class)
                .getChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<SwEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseEntity<List<SwEntity>> listBaseEntity) {
                        if (listBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(getActivity(), listBaseEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            list.clear();
                            entity.clear();
                            for (int i = 0; i < listBaseEntity.getData().size(); i++) {
                                list.add(listBaseEntity.getData().get(i).getName());
                                entity.add(listBaseEntity.getData().get(i).getId());
                            }
                            mAdapter.setData(list);
                            isFragment();
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

    private void initView() {
        LozyLoad();
        viewPager.setOffscreenPageLimit(3);
        initAdapter(getChildFragmentManager());
        initTab(getContext());
    }


    private void initTab(Context context) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
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

    private void initAdapter(FragmentManager childFragmentManager) {
        List<Fragment> fragments = new ArrayList<>();
        adapter = new HomeAdapter(childFragmentManager, fragments);
        viewPager.setAdapter(adapter);
    }

    private void isFragment() {
        List<String> entitls = mAdapter.getAll();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < entitls.size(); i++) {
            fragment = NewsFragment.getInstance(entity.get(i));
            if (fragment != null) {
                fragments.add(fragment);
            }
        }
        adapter.setData(fragments);

    }


}
