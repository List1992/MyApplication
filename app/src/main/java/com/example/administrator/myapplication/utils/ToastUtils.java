package com.example.administrator.myapplication.utils;


import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.App;

/**
 * 自定义吐司，避免重复创建toast
 */
public class ToastUtils {

    private static void show(String msg, View view, int graviry, int duration) {

        if (view != null) {
            Toast toast = new Toast(App.getContext());
            toast.setGravity(graviry, 0, 0);
            toast.setDuration(duration);
            toast.setView(view);
            toast.show();
        } else {

            Toast.makeText(App.getContext(), msg, duration).show();
        }

    }

    public static void showShort(View view) {
        show(null, view, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public static void showLong(View view) {
        show(null, view, Gravity.CENTER, Toast.LENGTH_LONG);
    }

    public static void showShort(String msg) {
        show(msg, null, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        show(msg, null, Gravity.CENTER, Toast.LENGTH_LONG);
    }
}
