package com.thn.basemodule.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by Stephen on 2018/3/6 10:52
 * Email: 895745843@qq.com
 */

public class ScreenUtil {

    /**
     * 获取屏幕像素密度
     * @return
     */
    public static float getDensity() {
        return getDisplayMetrics().density;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) BaseModuleContext.getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    /**
     * 获取屏幕宽度像素值
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseModuleContext.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    /**
     * 获取屏幕高度像素值
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) BaseModuleContext.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


}
