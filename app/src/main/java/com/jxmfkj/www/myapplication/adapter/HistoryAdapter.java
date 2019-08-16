package com.jxmfkj.www.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.HistoryEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryEntity.DataBean.DatasBean, BaseViewHolder> {
    public HistoryAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryEntity.DataBean.DatasBean item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvTime,item.getNiceDate());
        helper.setText(R.id.tvName,item.getChapterName());
        helper.setText(R.id.tvSource,item.getSuperChapterName());
    }
}
