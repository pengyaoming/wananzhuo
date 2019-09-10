package com.jxmfkj.www.myapplication.ui.tixi;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.R;


public class TiXiAdapter extends BaseQuickAdapter<SystemEntity.ChildrenBean, BaseViewHolder> {
    public TiXiAdapter() {
        super(R.layout.item_tixi);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemEntity.ChildrenBean item) {
        helper.setText(R.id.tvTitle, item.getName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
