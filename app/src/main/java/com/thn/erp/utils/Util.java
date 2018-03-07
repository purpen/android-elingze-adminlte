package com.thn.erp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.thn.erp.AppApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lilin on 2017/7/5.
 */

public class Util {
    /**
     * 检查手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^1((3[0-9])|(4[57])|(5[0-35-9])|(7[6780])|(8[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = AppApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    AppApplication.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return AppApplication.getContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "banmen";
    }

    public static File saveBitmapToFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar" + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fops = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,70, fops);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
        return file;
    }


    /**
     * 获取颜色数组
     */
    public static int[] getColorArray(int resourceId){
        Resources resources = AppApplication.getContext().getResources();
        TypedArray typedArray = resources.obtainTypedArray(resourceId);
        int len = typedArray.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++){
            resIds[i] = resources.getColor(typedArray.getResourceId(i, 0));
        }
        typedArray.recycle();
        return resIds;
    }

}
