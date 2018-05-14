package com.thn.erp.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.stephen.taihuoniaolibrary.utils.THNLogUtil;

public class CustomPopupWindow {
    private PopupWindow mPopupWindow;
    private View contentView;
    private Activity activity;
    public CustomPopupWindow(Builder builder) {
        this.contentView = builder.contentView;
        this.activity = builder.activity;
        mPopupWindow = new PopupWindow(contentView,builder.layoutParams.width,builder.layoutParams.height);
        mPopupWindow.setOutsideTouchable(builder.outSideCancel);
        mPopupWindow.setFocusable(builder.focus);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(builder.animationStyle);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
    }


    /**
     * popup 消失
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            setWindowAlpha(1);
            mPopupWindow.dismiss();
        }
    }

    /**
     * 根据id获取view
     *
     * @param viewId
     * @return
     */
    public View getItemView(int viewId) {
        if (mPopupWindow != null) {
            return this.contentView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 显示popupWindow
     * @param targetView
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public CustomPopupWindow showAtLocation(View targetView, int gravity, int offx, int offy) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(targetView, gravity, offx, offy);
        }
        return this;
    }

    /**
     * 设置窗口透明度
     * @param alpha
     */
    public CustomPopupWindow setWindowAlpha(float alpha) {
        if (activity==null){
            THNLogUtil.e("function setWindowAlpha activity==null");
            return this;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        window.setAttributes(lp);
        return this;
    }


    public static class Builder {
        private ViewGroup.LayoutParams layoutParams;
        private View contentView;
        private boolean focus;
        private boolean outSideCancel;
        private int animationStyle;
        private Activity activity;

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }


        public Builder setFocusable(boolean focus) {
            this.focus = focus;
            return this;
        }

        public Builder setOutSideCancel(boolean outSideCancel) {
            this.outSideCancel = outSideCancel;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setLayoutParams(ViewGroup.LayoutParams params){
            this.layoutParams = params;
            return this;
        }

        public CustomPopupWindow build() {
            return new CustomPopupWindow(this);
        }
    }
}
