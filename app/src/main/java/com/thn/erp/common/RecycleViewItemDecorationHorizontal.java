package com.thn.erp.common;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Stephen on 2017/11/9 17:47
 * Email: 895745843@qq.com
 */

public class RecycleViewItemDecorationHorizontal extends RecyclerView.ItemDecoration{
    private float margin;

    public RecycleViewItemDecorationHorizontal(Context context, int marginDimen) {
        margin = context.getResources().getDimensionPixelSize(marginDimen);
    }

    public RecycleViewItemDecorationHorizontal(float marginPx) {
        margin = marginPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildLayoutPosition(view);
        int borderWidth = (int) (margin/2);
        outRect.left = borderWidth;
        outRect.right= borderWidth;
    }
}
