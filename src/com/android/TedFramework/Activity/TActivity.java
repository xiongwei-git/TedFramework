package com.android.TedFramework.Activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Ted on 14-8-6.
 */
public class TActivity extends FragmentActivity{

    public int getPxFromDp(Context context, float paramFloat)
    {
        return (int)(0.5F + paramFloat * getDipUnit(context));
    }

    public  DisplayMetrics getDM(Context context)
    {
        Display localDisplay = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        localDisplay.getMetrics(localDisplayMetrics);
        return localDisplayMetrics;
    }

    public int getWidth(Context context)
    {
        return getDM(context).widthPixels;
    }

    public int getHeight(Context context)
    {
        return getDM(context).heightPixels;
    }


    public float getDipUnit(Context context)
    {
        return getDM(context).densityDpi / 160.0F;
    }
}
