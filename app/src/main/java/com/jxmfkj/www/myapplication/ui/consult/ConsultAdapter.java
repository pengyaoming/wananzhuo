package com.jxmfkj.www.myapplication.ui.consult;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.ConsultEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class ConsultAdapter extends BaseQuickAdapter<ConsultEntity, BaseViewHolder> {
    public ConsultAdapter() {
        super(R.layout.item_consult);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ConsultEntity item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvSource, "来源:" + item.getSuperChapterName());
        helper.setText(R.id.tvTime, "时间:" + item.getNiceDate());
        helper.setText(R.id.tvState, "作者:" + item.getAuthor());
    }
}
