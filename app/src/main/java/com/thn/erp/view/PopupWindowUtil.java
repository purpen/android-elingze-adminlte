package com.thn.erp.view;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.thn.erp.utils.Util;


/**
 * @author lilin
 *         created at 2016/4/26 18:39
 */
public class PopupWindowUtil {

    /**
     * jvm在类装载的时候是同步的，从而保证了线程安全
     * activity被作为静态成员或被静态成员对象所持有，导致内存泄漏
     */
    private static class SingletonHolder{
        public static PopupWindowUtil instance = new PopupWindowUtil();
    }

    private OnDismissListener listener;
    private PopupWindow popupWindow;
    public interface OnDismissListener {
        void onDismiss();
    }

    private PopupWindowUtil(){}


    public static PopupWindowUtil getInstance(){

        return SingletonHolder.instance;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.listener = listener;
    }

    public void show(final Activity activity, View view) {
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity,1f);
                if (listener != null) {
                    listener.onDismiss();
                    listener = null;
                }
            }
        });
        setWindowAlpha(activity,0.5f);
    }

    public void show(final Activity activity, View view, int gravity) {
        PopupWindow popupWindow = new PopupWindow(view, Util.getScreenWidth() * 4 / 5, Util.getScreenHeight() / 4);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, gravity, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity,1f);
                if (listener != null) {
                    listener.onDismiss();
                    listener = null;
                }
            }
        });
        setWindowAlpha(activity,0.5f);
    }

    private void setWindowAlpha(Activity activity,float f) {
        if(activity==null) return;
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


    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}
