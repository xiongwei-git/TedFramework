package com.android.TedFramework.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Ted on 14-7-21.
 */
public class DeviceUtil {
    private static int nStatusbarHeight;
    private static int nNavigatebarHeight;
    private static int nNavigationBarWidth;
    private static int nScreentWidth;
    private static int nScreentHeight;

    /**
     * 获取设备的型号
     * @return
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;

        if (model == null) {
            return "";
        } else {
            return model;
        }
    }

    /**
     * 获取设备 SDK版本名
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * 获取设备 SDK版本号
     * @return
     */
    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断是否存在SD卡
     * @return
     */
    public static boolean isSdCardExist()
    {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取设备的IMEI号
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getDeviceId();
    }

    /**
     * 获取设备的IMSI号
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getSubscriberId();
    }

    /**
     * 获取设备号 wifi mac + imei + cpu serial
     * @param context
     * @return
     */
    public static String getMobileUUID(Context context) {
        String uuid = "";
        // 先获取mac
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		/** 获取mac地址 */
        if (wifiMgr != null) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            if (info != null && info.getMacAddress() != null) {
                uuid = info.getMacAddress().replace(":", "");
            }
        }
        // 再加上imei
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = teleMgr.getDeviceId();
        uuid += imei;
        // 最后再加上cpu
        String str = "", strCPU = "", cpuAddress = "";
        try {
            String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
            ProcessBuilder cmd = new ProcessBuilder(args);
            java.lang.Process pp = cmd.start();
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("Serial") > -1) {
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        uuid += cpuAddress;
        // 如果三个加在一起超过64位的话就截取
        if (uuid != null && uuid.length() > 64) {
            uuid = uuid.substring(0, 64);
        }
        return uuid;
    }

    /**
     * 获取屏幕的宽高
     *
     * @param dm
     *            设备显示对象描述
     * @return int数组, int[0] - width, int[1] - height
     */
    public static int[] getScreenSize(DisplayMetrics dm) {
        int[] result = new int[2];
        result[0] = dm.widthPixels;
        result[1] = dm.heightPixels;
        return result;
    }

    /**
     * 判断当前设备的数据服务是否有效
     *
     * @return true - 有效，false - 无效
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }

        NetworkInfo nwInfo = connectMgr.getActiveNetworkInfo();
        if (nwInfo == null || !nwInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public static void initScreenParams(Resources resources) {
        if (resources == null) {
            return;
        }
        try {
            Class<?> clazz = null;
            Object obj = null;
            Field field = null;
            int x;
            clazz = Class.forName("com.android.internal.R$dimen");
            obj = clazz.newInstance();
            try {
                field = clazz.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                nStatusbarHeight = resources.getDimensionPixelSize(x);
            } catch (NoSuchFieldException e) {

            }

            try {
                field = clazz.getField("navigation_bar_width");
                x = Integer.parseInt(field.get(obj).toString());
                nNavigationBarWidth = resources.getDimensionPixelSize(x);
            } catch (NoSuchFieldException e) {

            }

            try {
                field = clazz.getField("navigation_bar_height_landscape");
                x = Integer.parseInt(field.get(obj).toString());
                nNavigatebarHeight = resources.getDimensionPixelSize(x);
            } catch (NoSuchFieldException e) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        nScreentWidth = resources.getDisplayMetrics().widthPixels;
        nScreentHeight = resources.getDisplayMetrics().heightPixels;
    }

    public static int getStatusbarHeight() {
        return nStatusbarHeight;
    }
    public static int getNavigationbarHeight() {
        return nNavigatebarHeight;
    }

    public static int getNavigationBarWidth() {
        return nNavigationBarWidth;

    }
    public static int getAcailableScreenHeight() {
        return nScreentHeight - nStatusbarHeight;
    }
    public static int getPixelFromDip(Context context, float dip) {
        return getPixelFromDip(context.getResources().getDisplayMetrics(), dip);
    }

    public static double calculateScreenSize(DisplayMetrics outMetrics) {
        double x = Math.pow(outMetrics.widthPixels / outMetrics.xdpi, 2);
        double y = Math.pow(outMetrics.heightPixels / outMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    public static double calculateScreenSize(Context context) {
        DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
        return calculateScreenSize(outMetrics);
    }

    /**
     *
     * @return
     */
    public static int[] getScreenSize() {
        int[] result = new int[2];
        result[0] = nScreentWidth;
        result[1] = nScreentHeight;
        return result;
    }

    public static boolean isAppInstalled(Context context, String pkgName) {
        if (context == null) {
            return false;
        }

        try {
            context.getPackageManager().getPackageInfo(pkgName, PackageManager.PERMISSION_GRANTED);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * (检查终端是否支持指定的action)
     */
    public static boolean isIntentAvailable(Context context, String action) {
        return isIntentAvailable(context, new Intent(action));
    }

    /**
     * (检查终端是否支持指定的intent)
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }

        PackageManager pkgManager = context.getPackageManager();
        if (pkgManager == null) {
            return false;
        }
        List<ResolveInfo> list = pkgManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * Dip转换为实际屏幕的像素值
     *
     * @param dm
     *            设备显示对象描述
     * @param dip
     *            dip值
     * @return 匹配当前屏幕的像素值
     */
    public static int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }
}
