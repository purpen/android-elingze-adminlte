package com.thn.erp.view.ImageCrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 *
 * @author zhy
 */
public class ClipImageLayout extends RelativeLayout {

    private ClipZoomImageView mZoomImageView;


    public void setImage(Bitmap bitmap) {
        /**
         * 这里测试，直接写死了图片，真正使用过程中，可以提取为自定义属性
         */

        mZoomImageView.setImageBitmap(bitmap);
//        mZoomImageView.setImageDrawable(getResources().getDrawable(
//                R.drawable.a));
    }

    public ClipImageLayout(Context context) {
        this(context, null);
    }

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        ClipImageBorderView mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);


        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);

        // 计算padding的px
//        mHorizontalPadding = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
//                        .getDisplayMetrics());
//        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
//        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }


    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

}
