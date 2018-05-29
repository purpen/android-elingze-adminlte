package com.thn.erp.zxing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.thn.erp.AppApplication;
import com.thn.basemodule.tools.LogUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TDevice {

    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;

    private static int _pageSize = -1;
    public static float displayDensity = 0.0F;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    public TDevice() {
    }
    
    
    public static int getDefaultLoadFactor() {
        if (_loadFactor == null) {
            Integer integer = 0xf & AppApplication.getContext()
                    .getResources().getConfiguration().screenLayout;
            _loadFactor = integer;
            _loadFactor = Math.max(integer, 1);
        }
        return _loadFactor;
    }



//    public static String getUdid() {
//        String udid = BaseApplication.getPreferences().getString("udid", "");
//        if (udid.length() == 0) {
//            SharedPreferences.Editor editor = BaseApplication.getPreferences()
//                    .edit();
//            udid = String.format("%s", UUID.randomUUID());
//            editor.putString("udid", udid);
//            editor.commit();
//        }
//        return udid;
//    }

//    public static boolean hasBigScreen() {
//        boolean flag = true;
//        if (_hasBigScreen == null) {
//            boolean flag1;
//            if ((0xf & MainApplication.getContext().getResources()
//                    .getConfiguration().screenLayout) >= 3)
//                flag1 = flag;
//            else
//                flag1 = false;
//            Boolean boolean1 = Boolean.valueOf(flag1);
//            _hasBigScreen = boolean1;
//            if (!boolean1.booleanValue()) {
//                if (getDensity() <= 1.5F)
//                    flag = false;
//                _hasBigScreen = Boolean.valueOf(flag);
//            }
//        }
//        return _hasBigScreen.booleanValue();
//    }

    public static boolean hasCamera() {
        if (_hasCamera == null) {
            PackageManager pckMgr = AppApplication.getContext()
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            flag2 = flag || flag1;
            _hasCamera = flag2;
        }
        return _hasCamera;
    }

    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag;
        flag = PRE_HC || GTE_ICS && ViewConfiguration.get(context).hasPermanentMenuKey();
        return flag;
    }

//    public static boolean hasInternet() {
//        boolean flag;
//        if (((ConnectivityManager) MainApplication.getContext().getSystemService(
//                "connectivity")).getActiveNetworkInfo() != null)
//            flag = true;
//        else
//            flag = false;
//        return flag;
//    }

    public static boolean gotoGoogleMarket(Activity activity, String pck) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.android.vending");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pck));
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static boolean isPackageExist(String pckName) {
//        try {
//            PackageInfo pckInfo = MainApplication.getContext().getPackageManager()
//                    .getPackageInfo(pckName, 0);
//            if (pckInfo != null)
//                return true;
//        } catch (NameNotFoundException e) {
//            TLog.error(e.getMessage());
//        }
//        return false;
//    }

    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(view.getWidth(), 0, 0, 0);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) AppApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    public static boolean isLandscape() {
        boolean flag;
        flag = AppApplication.getContext().getResources().getConfiguration().orientation == 2;
        return flag;
    }

    public static boolean isPortrait() {
        boolean flag = true;
        if (AppApplication.getContext().getResources().getConfiguration().orientation != 1)
            flag = false;
        return flag;
    }

    public static boolean isTablet() {
        if (_isTablet == null) {
            boolean flag;
            flag = (0xf & AppApplication.getContext().getResources()
                    .getConfiguration().screenLayout) >= 3;
            _isTablet = flag;
        }
        return _isTablet;
    }


    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(0, 0, 0, 0);
    }

    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) AppApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    public static void toogleSoftKeyboard(View view) {
        ((InputMethodManager) AppApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String getCurCountryLan() {
        return AppApplication.getContext().getResources().getConfiguration().locale
                .getLanguage()
                + "-"
                + AppApplication.getContext().getResources().getConfiguration().locale
                .getCountry();
    }

    public static boolean isZhCN() {
        String lang = AppApplication.getContext().getResources()
                .getConfiguration().locale.getCountry();
        return lang.equalsIgnoreCase("CN");
    }

    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }

    public static String percent2(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }

    public static void gotoMarket(Context context, String pck) {
        if (!isHaveMarket(context)) {
            Toast.makeText(context, "你手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pck));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    public static void openAppInMarket(Context context) {
        if (context != null) {
            String pckName = context.getPackageName();
            try {
                gotoMarket(context, pckName);
            } catch (Exception ex) {
                try {
                    String otherMarketUri = "http://market.android.com/details?id="
                            + pckName;
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(otherMarketUri));
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static PackageInfo getPackageInfo(String pckName) {
        try {
            return AppApplication.getContext().getPackageManager()
                    .getPackageInfo(pckName, 0);
        } catch (NameNotFoundException e) {
        }
        return null;
    }

    public static int getVersionCode() {
        int versionCode;
        try {
            versionCode = AppApplication.getContext()
                    .getPackageManager()
                    .getPackageInfo(AppApplication.getContext().getPackageName(),
                            0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static int getVersionCode(String packageName) {
        int versionCode;
        try {
            versionCode = AppApplication.getContext().getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static String getVersionName() {
        String name;
        try {
            name = AppApplication.getContext()
                    .getPackageManager()
                    .getPackageInfo(AppApplication.getContext().getPackageName(),
                            0).versionName;
        } catch (NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) AppApplication.getContext()
                .getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openCamera(Context context) {
        Intent intent = new Intent(); // 调用照相机
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
//        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }

//    public static String getIMEI() {
//        TelephonyManager tel = (TelephonyManager) AppApplication.getContext()
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        return tel.getDeviceId();
//    }

    public static String getPhoneType() {
        return Build.MODEL;
    }

    public static void openApp(Context context, String packageName) {
        Intent mainIntent = AppApplication.getContext().getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        } else {
            LogUtil.e("openApp","Action:" + mainIntent.getAction());
        }
        context.startActivity(mainIntent);
    }

    public static boolean openAppActivity(Context context, String packageName,
                                          String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isWifiOpen() {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) AppApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }

//    public static void uninstallApk(Context context, String packageName) {
//        if (isPackageExist(packageName)) {
//            Uri packageURI = Uri.parse("package:" + packageName);
//            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
//                    packageURI);
//            context.startActivity(uninstallIntent);
//        }
//    }

    @SuppressWarnings("deprecation")
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) AppApplication.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
        Toast.makeText(AppApplication.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject,
                                 String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getStatuBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = AppApplication.getContext().getResources()
                    .getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context,
                                             final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager)AppApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
}
