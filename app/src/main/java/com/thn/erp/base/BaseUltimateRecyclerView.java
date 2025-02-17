package com.thn.erp.base;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/26 19:49
 * Email: 895745843@qq.com
 */

public class BaseUltimateRecyclerView extends UltimateRecyclerView {
    private RecyclerView.ItemDecoration itemDecoration;

    public BaseUltimateRecyclerView(Context context) {
        super(context);
    }

    public BaseUltimateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseUltimateRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setLoadMoreView(LayoutInflater.from(mContext).inflate(R.layout.custom_bottom_progressbar, null));
        setRecylerViewBackgroundColor(Color.WHITE);
        setEmptyView( R.layout.empty_view,
                UltimateRecyclerView.EMPTY_CLEAR_ALL,
                UltimateRecyclerView.STARTWITH_ONLINE_ITEMS);
//        reenableLoadmore();
        itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
        addItemDecoration(itemDecoration);
    }

    /**
     * 去掉分割线
     */
    public void removeItemDividerDecoration() {
        if (itemDecoration != null) {
            mRecyclerView.removeItemDecoration(itemDecoration);
            itemDecoration = null;
        }
    }
}
