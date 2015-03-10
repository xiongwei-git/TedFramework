package com.android.tedframework.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Ted on 2014/9/25.
 */
public class DateUtil {
    public static final String TIMEZONE_CN = "Asia/Shanghai";
    public final static String SIMPLEFORMATTYPE1 = "yyyyMMddHHmmss";
    public final static String SIMPLEFORMATTYPE2 = "yyyy-MM-dd HH:mm:ss";
    public final static String SIMPLEFORMATTYPE3 = "yyyy-M-d HH:mm:ss";
    public final static String SIMPLEFORMATTYPE4 = "yyyy-MM-dd HH:mm";
    public final static String SIMPLEFORMATTYPE5 = "yyyy-M-d HH:mm";
    public final static String SIMPLEFORMATTYPE6 = "yyyyMMdd";
    public final static String SIMPLEFORMATTYPE7 = "yyyy-MM-dd";
    public final static String SIMPLEFORMATTYPE8 = "yyyy-M-d";
    public final static String SIMPLEFORMATTYPE9 = "yyyy年MM月dd日";
    public final static String SIMPLEFORMATTYPE10 = "yyyy年M月d日";
    public final static String SIMPLEFORMATTYPE11 = "M月d日";
    public final static String SIMPLEFORMATTYPE12 = "HH:mm:ss";
    public final static String SIMPLEFORMATTYPE13 = "HH:mm";
    public final static String SIMPLEFORMATTYPE14 = "yyyy/MM/dd";
    public final static String SIMPLEFORMATTYPE15 = "MM月dd日";
    public final static String SIMPLEFORMATTYPE16 = "yyyy/MM/dd HH:mm:ss";
    public final static String SIMPLEFORMATTYPE17 = "d/M/yyyy";
    public final static String SIMPLEFORMATTYPE18 = "yyyy-MM";

    public static Calendar getCalFromTimeMillis(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static String getCalStrBySDF(Calendar calendar, String type) {
        String str = "";
        if (!StringUtil.emptyOrNull(type) && calendar != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(type);
            dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CN));
            str = (dateFormat).format(calendar.getTime());
        }
        return str;
    }


}
