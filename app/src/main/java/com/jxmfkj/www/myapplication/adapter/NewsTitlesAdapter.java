package com.jxmfkj.www.myapplication.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.R;

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class NewsTitlesAdapter extends CommonNavigatorAdapter {

    private List<String> entities;

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    private NewsTitlesAdapter.OnItemClickListener mOnItemClickListener;

    public NewsTitlesAdapter() {
        entities = new ArrayList<>();
    }

    public void setData(List<String> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    public List<String> getAll() {
        return entities;
    }


    @Override
    public int getCount() {
        return entities.size();
    }

    public NewsTitlesAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(NewsTitlesAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        final int mSelectedColor = ContextCompat.getColor(context, R.color.white);
        final int mNormalColor = ContextCompat.getColor(context, R.color.white);
        final CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        commonPagerTitleView.setContentView(R.layout.news_tab_layout);
        final TextView title = commonPagerTitleView.findViewById(R.id.title);
        title.setText(entities.get(index));
        title.setTextSize(16);
        commonPagerTitleView.setContentPositionDataProvider(new CommonPagerTitleView.ContentPositionDataProvider() {
            public int getContentLeft() {
                Rect bound = new Rect();
                title.getPaint().getTextBounds(title.getText().toString(), 0, title.getText().length(), bound);
                int contentWidth = bound.width();
                return commonPagerTitleView.getLeft() + commonPagerTitleView.getWidth() / 2 - contentWidth / 2;
            }

            public int getContentTop() {
                Paint.FontMetrics metrics = title.getPaint().getFontMetrics();
                float contentHeight = metrics.bottom - metrics.top;
                return (int) ((float) (commonPagerTitleView.getHeight() / 2) - contentHeight / 2.0F);
            }

            public int getContentRight() {
                Rect bound = new Rect();
                title.getPaint().getTextBounds(title.getText().toString(), 0, title.getText().length(), bound);
                int contentWidth = bound.width();
                return commonPagerTitleView.getLeft() + commonPagerTitleView.getWidth() / 2 + contentWidth / 2;
            }

            public int getContentBottom() {
                Paint.FontMetrics metrics = title.getPaint().getFontMetrics();
                float contentHeight = metrics.bottom - metrics.top;
                return (int) ((float) (commonPagerTitleView.getHeight() / 2) + contentHeight / 2.0F);
            }
        });

        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {


            @Override
            public void onSelected(int index, int totalCount) {
//                title.setTextColor(mSelectedColor);
            }

            @Override
            public void onDeselected(int index, int totalCount) {
//                title.setTextColor(mNormalColor);
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
                title.setTextColor(color);
            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
                title.setTextColor(color);
            }
        });

        commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(index);
                }
            }
        });


//        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
//        int padding = UIUtil.dip2px(context, 12.0D);
//        simplePagerTitleView.setPadding(padding, 0, padding, 0);
//        simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.black));
//        simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//        simplePagerTitleView.setText(entities.get(index).channelName);
//        simplePagerTitleView.setTextSize(17);
//        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(index);
//                }
//            }
//        });
        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
        linePagerIndicator.setYOffset(UIUtil.dip2px(context, 4));
        linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 1.8));
        linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 2));
        linePagerIndicator.setColors(ContextCompat.getColor(context, R.color.white));
        return linePagerIndicator;
    }
}
