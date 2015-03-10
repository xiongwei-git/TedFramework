package com.android.TedFramework.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Ted on 14-8-6.
 */
public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void showCenter(Context context, String info) {
        Toast toast = new Toast(context);
        toast.setText(info);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
