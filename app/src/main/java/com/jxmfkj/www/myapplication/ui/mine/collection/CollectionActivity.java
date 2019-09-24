package com.jxmfkj.www.myapplication.ui.mine.collection;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.CollectionEntity;
import com.jxmfkj.www.myapplication.Entity.SearchEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseActivity;

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
        left_img.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        RetrofitUtil.getInstance(this).create(ApiServer.class)
                .getCollection(page+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<SearchEntity<List<CollectionEntity>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<SearchEntity<List<CollectionEntity>>> searchEntityBaseResponse) {
                            if (searchEntityBaseResponse.getCode() !=0){
                                Message(searchEntityBaseResponse.getMsg());
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


}
