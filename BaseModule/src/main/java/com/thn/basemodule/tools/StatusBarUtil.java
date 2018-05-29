package com.thn.basemodule.tools;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by taihuoniao on 2016/4/14 19:19
 * Email: 895745843@qq.com
 */
public class StatusBarUtil {
    /**
     *  状态栏变为黑色
     */

    public static void chenjin(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintColor(activity.getResources().getColor(colorId));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * 显示透明状态栏
     */
    public static void showStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 隐藏状态栏
     */
    public static void hide(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    /**
     * 显示状态栏
     */
    public static void show(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int resourceId = BaseModuleContext.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId <= 0) ? 0 : BaseModuleContext.getContext().getResources().getDimensionPixelSize(resourceId);
    }
}
