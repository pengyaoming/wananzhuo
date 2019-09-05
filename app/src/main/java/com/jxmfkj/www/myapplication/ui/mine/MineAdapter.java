package com.jxmfkj.www.myapplication.ui.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.MineEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class MineAdapter extends BaseQuickAdapter<MineEntity, BaseViewHolder> {
    public MineAdapter(int layoutResId, @Nullable List<MineEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineEntity item) {
        helper.setText(R.id.tvText, item.getName());
        helper.setImageResource(R.id.img, item.getImg());
    }
}
