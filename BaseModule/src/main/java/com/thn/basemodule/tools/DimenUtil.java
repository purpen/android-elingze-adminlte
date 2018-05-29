package com.thn.basemodule.tools;

import android.content.Context;

/**
 * 数据转换工具类
 */
public class DimenUtil {

    /**
     * dp转px
     */
    public static int dp2px(double dpVal) {
        final float scale = BaseModuleContext.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }


    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = BaseModuleContext.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转dp
     */
    public static float px2dp(float pxVal) {
        final float scale = BaseModuleContext.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / BaseModuleContext.getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}
