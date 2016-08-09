package com.example.administrator.myapplication.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 通知栏的颜色
 */
public class StatusColor {

    public static void setStatusColor(Activity activity, int res) {

        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, activity);
        }
        //setTranslucentStatus(true, activity);
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        //tintManager.setStatusBarAlpha(0.0f);
        tintManager.setStatusBarTintResource(res);//通知栏所需颜色
    }


    @TargetApi(19)
    private static void setTranslucentStatus(boolean b, Activity activity) {
        // TODO Auto-generated method stub
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

		
