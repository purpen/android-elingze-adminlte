package com.thn.erp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;
import com.thn.erp.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


//import com.squareup.leakcanary.LeakCanary;


/**
 * Created by taihuoniao on 2016/3/14.
 * ¥
 * 上线之前检查WriteJSONToSD
 * 在客户端scene是场景，qingjing是情景，而在服务器端sight是场景，scene是情景
 * 检查在DataConstants.NETWORK_FAILURE情况下dialog是否隐藏
 * 检查所有的Toast，删除没用的提示
 */
public class AppApplication extends Application {
    private static Application instance;
    public static int which_activity;//判断是从哪个界面跳转到登录界面,0是默认从主页面跳

//    private PushAgent mPushAgent;
    private SharedPreferences tempSharedPreference;//检查本地存储是否设置推送的开关

    // activityList
    private List<BaseActivity> mBaseActivityList;


    public static Application getContext() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        initPush();
        if (mBaseActivityList == null) {
            mBaseActivityList = new ArrayList<>();
        }
        if (BuildConfig.LOG_DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    public boolean isSD() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = getExternalCacheDir();
            if (file != null && file.canWrite()) {
                return true;
            }
        }
        return false;
    }

    //获取缓存文件
    public File getCacheDisk() {
        if (isSD()) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null) {
                if (cacheDir.canWrite()) {
                    return cacheDir;
                }
            }
        }
        return getCacheDir();
    }

//    public void initPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.mipmap.icon_logo;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
//        JPushInterface.setPushNotificationBuilder(1, builder);
//        tempSharedPreference = getSharedPreferences(DataConstants.PUSH_STATUS, Context.MODE_PRIVATE);
//    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }

    public void addActivity(BaseActivity activity){
        mBaseActivityList.add(activity);
    }

    public void removeActivity(BaseActivity activity){
        boolean contains = mBaseActivityList.contains(activity);
        if (contains) {
            mBaseActivityList.remove(activity);
            activity.finish();
        }
    }

    public void clearAllActivity() {
        for(int i = 0; i < mBaseActivityList.size(); i++) {
            BaseActivity remove = mBaseActivityList.remove(i);
            remove.finish();
        }
    }
}
