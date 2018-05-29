package com.thn.basemodule.tools;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = BaseModuleContext.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseModuleContext.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseModuleContext.getContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "";
    }

}
