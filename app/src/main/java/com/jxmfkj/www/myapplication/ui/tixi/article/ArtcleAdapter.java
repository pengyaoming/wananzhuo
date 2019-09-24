package com.jxmfkj.www.myapplication.ui.tixi.article;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.R;

public class ArtcleAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {
    public ArtcleAdapter() {
        super(R.layout.item_consult);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvSource, item.getChapterName());
        helper.setText(R.id.tvTime, item.getNiceDate());
        helper.setText(R.id.tvState, item.getSuperChapterName());
    }
}
