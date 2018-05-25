package com.thn.erp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.myapp.MyEventBusIndex;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.squareup.leakcanary.LeakCanary;
import com.stephen.taihuoniaolibrary.common.THNApp;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.constant.THNZone;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppApplication extends MultiDexApplication {
    private static Application instance;


    private static UploadManager uploadManager;


    public static Application getContext() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        initPush();
        if (BuildConfig.LOG_DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }

        THNApp.init(this);
        EventBus.builder().addIndex(new MyEventBusIndex()).throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
        initQiNiu();
        ZXingLibrary.initDisplayOpinion(this);
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
        MultiDex.install(base);
    }

    private void initQiNiu() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(THNZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();

        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        uploadManager = new UploadManager(config);
    }
    public static UploadManager getUploadManager() {
        return uploadManager;
    }
}
