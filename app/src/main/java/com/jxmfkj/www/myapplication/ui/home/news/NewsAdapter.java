package com.jxmfkj.www.myapplication.ui.home.news;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {
    public NewsAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getNiceDate());
        helper.setText(R.id.tvName, item.getChapterName());
        helper.setText(R.id.tvSource, item.getSuperChapterName());
    }
}
