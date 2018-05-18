package com.thn.basemodule.tools;

import android.content.Context;

/**
 * 数据转换工具类
 */
public class DimenUtil {

    /**
     * dp转px
     */
    public static int dp2px(Context context, double dpVal) {
        if(context==null)
            return 0;
        if(context.getResources()==null){
            return 0;
        }
        if(context.getResources().getDisplayMetrics()==null){
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }


    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        if(context==null)
            return 0;
        if(context.getResources()==null){
            return 0;
        }
        if(context.getResources().getDisplayMetrics()==null){
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
