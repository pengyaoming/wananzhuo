package com.jxmfkj.www.myapplication.ui.home.news;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.NewsEntity;
import com.jxmfkj.www.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {

    public NewsAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {

        TextView title = helper.getView(R.id.tvTitle);
        title.setText(item.getTitle());
        helper.setText(R.id.tvTime, item.getNiceDate());
        helper.setText(R.id.tvName, item.getChapterName());
        helper.setText(R.id.tvSource, item.getSuperChapterName());

    }



}
