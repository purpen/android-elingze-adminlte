package com.thn.erp.overview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Stephen on 2017/11/9 17:47
 * Email: 895745843@qq.com
 */

public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration{
    private float margin;

    public RecycleViewItemDecoration(Context context, int marginDimen) {
        margin = context.getResources().getDimensionPixelSize(marginDimen);
    }

    public RecycleViewItemDecoration(Context context, float marginPx) {
        margin = marginPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        int borderWidth = (int) margin;
        outRect.left = borderWidth;
        outRect.top = borderWidth;
        outRect.right= borderWidth;
        outRect.bottom = borderWidth;
        if (position < 3) {
            outRect.top = 5 ;
        }
    }
}
