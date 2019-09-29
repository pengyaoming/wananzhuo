package com.jxmfkj.www.myapplication.ui.tixi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 体系
 */
public class TiXiFragment extends RxFragment {
    private TextView tv_title;
    private RecyclerView recyclerView;
    private SystemAdapter adapter;
    private RelativeLayout linearLayout;
    private TiXiAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tixi, container, false);
        tv_title = view.findViewById(R.id.tvTitle);
        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayout = view.findViewById(R.id.liner);
        return view;
    }

    public static TiXiFragment getInstance() {
        TiXiFragment fragment = new TiXiFragment();
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initOnClick();
    }


    private void initView() {
        tv_title.setText("体系");
        tv_title.setTextColor(getResources().getColor(R.color.white));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SystemAdapter(getContext());
        recyclerView.setAdapter(adapter);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.IcIcon));
    }

    private void initData() {
        RetrofitUtil.getInstance(getContext())
                .create(ApiServer.class)
                .getSystem().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<SystemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<SystemEntity>> listBaseEntity) {
                        if (listBaseEntity.getErrorCode() != 0) {
                            Toast.makeText(getActivity(), listBaseEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            adapter.addData(listBaseEntity.getData());
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

    /*
    点击跳转
     */
    private void initOnClick() {
    }
}
