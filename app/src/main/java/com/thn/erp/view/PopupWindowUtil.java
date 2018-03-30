package com.thn.erp.view;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.stephen.taihuoniaolibrary.common.THNApp;
import com.thn.erp.utils.Util;


/**
 * @author lilin
 *         created at 2016/4/26 18:39
 */
public class PopupWindowUtil {
    private static OnDismissListener windowListener;
    private static PopupWindow popupWindow;
    private static Activity activity;
    public interface OnDismissListener {
        void onDismiss();
    }

    public static void setListener(OnDismissListener listener) {
        windowListener = listener;
    }

    public static void show(Activity activity, View view) {
        PopupWindowUtil.activity = activity;
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(dismissListener);
        setWindowAlpha(activity,0.5f);
    }

    public static void show(Activity activity, View view, int gravity) {
        PopupWindowUtil.activity = activity;
        PopupWindow popupWindow = new PopupWindow(view, Util.getScreenWidth() * 4 / 5, Util.getScreenHeight() / 4);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, gravity, 0, 0);
        popupWindow.setOnDismissListener(dismissListener);
        setWindowAlpha(activity,0.5f);
    }

    private static void setWindowAlpha(Activity activity, float f) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = f;
        if (f == 1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        window.setAttributes(lp);
    }

    private static PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setWindowAlpha(activity,1f);
            if (windowListener != null) {
                windowListener.onDismiss();
                PopupWindowUtil.windowListener = null;
            }
        }
    };


    public static void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            activity = null;
        }
    }
}
