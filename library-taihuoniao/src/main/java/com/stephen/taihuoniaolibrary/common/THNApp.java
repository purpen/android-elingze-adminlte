package com.stephen.taihuoniaolibrary.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by Stephen on 2018/3/5 16:46
 * Email: 895745843@qq.com
 */

public class THNApp {
    private static Application mContext;

    private THNApp() {
    }

    public static void init(Application application) {
        mContext = application;
    }

    public static Context getContext(){
        return mContext;
    }

    public int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }
}