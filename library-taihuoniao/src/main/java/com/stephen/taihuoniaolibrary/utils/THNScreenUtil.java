package com.stephen.taihuoniaolibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.stephen.taihuoniaolibrary.common.THNApp;

/**
 * Created by Stephen on 2018/3/6 10:52
 * Email: 895745843@qq.com
 */

public class THNScreenUtil {
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) THNApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) THNApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getStatusBarHeight() {
        int resourceId = THNApp.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId <= 0) ? 0 : THNApp.getContext().getResources().getDimensionPixelSize(resourceId);
    }
}
