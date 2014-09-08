package com.android.TedFramework.util;

/**
 * Created by w_xiong on 2014/9/5.
 */
public class CheckDoubleClick {
    private static long lastClickTime=0;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
