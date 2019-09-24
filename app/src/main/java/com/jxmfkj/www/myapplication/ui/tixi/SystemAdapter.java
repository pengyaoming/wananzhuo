package com.jxmfkj.www.myapplication.ui.tixi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.ui.tixi.article.ArtcleActivity;
import com.jxmfkj.www.myapplication.weight.FlowLayoutManager;

public class SystemAdapter extends BaseQuickAdapter<SystemEntity, BaseViewHolder> {
    private Context context;

    public SystemAdapter(Context context) {
        super(R.layout.item_system);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, SystemEntity item) {
        final TiXiAdapter mAdapter;
        RecyclerView recyclerView;
        helper.setText(R.id.tvTitle, item.getName());
        recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        mAdapter = new TiXiAdapter(context);
        recyclerView.setAdapter(mAdapter);
        mAdapter.addData(item.getChildren());

    }
}
