package com.jxmfkj.www.myapplication.ui.home.search;

import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SearchListEntity;
import com.jxmfkj.www.myapplication.R;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<SearchListEntity, BaseViewHolder> {
    public SearchAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchListEntity item) {
        helper.setText(R.id.tvTitle, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tvTime, item.getNiceDate());
        helper.setText(R.id.tvName, item.getAuthor());
        helper.setText(R.id.tvSource, item.getSuperChapterName());

    }
}
