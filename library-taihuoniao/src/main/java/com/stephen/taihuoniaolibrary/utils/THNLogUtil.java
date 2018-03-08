package com.stephen.taihuoniaolibrary.utils;

import android.util.Log;


/**
 * @author lilin
 *         created at 2016/3/24 14:58
 */
public class THNLogUtil {
    private static final String TAG = "THNLogUtil";
    private static final boolean DEV_MODE = true;
    private static final int LOG_MAXLENGTH  = 2000;

    public static void i(String tag, String msg) {
        if (DEV_MODE) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEV_MODE) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEV_MODE) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEV_MODE) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEV_MODE) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(TAG, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(TAG, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
