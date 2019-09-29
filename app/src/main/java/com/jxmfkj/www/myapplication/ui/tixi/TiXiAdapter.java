package com.jxmfkj.www.myapplication.ui.tixi;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxmfkj.www.myapplication.Entity.SystemEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.ui.tixi.article.ArtcleActivity;


public class TiXiAdapter extends BaseQuickAdapter<SystemEntity.ChildrenBean, BaseViewHolder> {
    private Context context;

    public TiXiAdapter(Context context) {
        super(R.layout.item_tixi);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SystemEntity.ChildrenBean item) {
        helper.setText(R.id.tvTitle, item.getName());
        TextView textView = helper.getView(R.id.tvTitle);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtcleActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getName());
                context.startActivity(intent);
            }
        });
    }
}
