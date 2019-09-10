package com.jxmfkj.www.myapplication.ui.tixi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class SystemAdapter extends BaseQuickAdapter<SystemEntity, BaseViewHolder> {

    public SystemAdapter() {
        super(R.layout.item_system);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemEntity item) {
        TiXiAdapter adapter;
        RecyclerView recyclerView;
        helper.setText(R.id.tvTitle, item.getName());
        recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        adapter = new TiXiAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addData(item.getChildren());
    }
}
