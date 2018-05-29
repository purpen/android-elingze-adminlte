package com.thn.erp.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.thn.basemodule.tools.LogUtil;

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
            LogUtil.e("function setWindowAlpha activity==null");
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


        public Builder(Activity activity,View contentView){
            this.activity =activity;
            this.contentView = contentView;
            this.layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        public Builder(Activity activity,View contentView,ViewGroup.LayoutParams layoutParams){
            this.activity =activity;
            this.contentView = contentView;
            this.layoutParams = layoutParams;
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


        public CustomPopupWindow build() {
            return new CustomPopupWindow(this);
        }
    }
}
