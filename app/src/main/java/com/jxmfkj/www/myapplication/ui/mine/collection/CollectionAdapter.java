package com.jxmfkj.www.myapplication.ui.mine.collection;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.CollectionEntity;
import com.jxmfkj.www.myapplication.R;

public class CollectionAdapter extends BaseQuickAdapter<CollectionEntity, BaseViewHolder> {
    public CollectionAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionEntity item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getNiceDate());
        helper.setText(R.id.tvName, item.getAuthor());
        helper.setText(R.id.tvSource, item.getChapterName());
    }
}
