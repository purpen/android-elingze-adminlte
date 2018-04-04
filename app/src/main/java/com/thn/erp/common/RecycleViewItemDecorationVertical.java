package com.thn.erp.common;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Stephen on 2017/11/9 17:47
 * Email: 895745843@qq.com
 */

public class RecycleViewItemDecorationVertical extends RecyclerView.ItemDecoration{
    private float margin;

    public RecycleViewItemDecorationVertical(Context context, int marginDimen) {
        margin = context.getResources().getDimensionPixelSize(marginDimen);
    }

    public RecycleViewItemDecorationVertical(float marginPx) {
        margin = marginPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildLayoutPosition(view);
        int borderHeight = (int) (margin/2);
        outRect.top = borderHeight;
        outRect.bottom = borderHeight;
    }
}
