package com.jxmfkj.www.myapplication.adapter;


import android.support.annotation.NonNull;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SwEntity;
import com.jxmfkj.www.myapplication.R;

public class SwAdapter extends BaseQuickAdapter<SwEntity.DataBean, BaseViewHolder> {
    public SwAdapter() {
        super(R.layout.item_sw);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SwEntity.DataBean item) {
            helper.setText(R.id.tvText, item.getName());
    }




}
