package com.yizhui.oschina.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yizhui.oschina.R;

/**
 * Created by Yizhui on 2016/5/21.
 */
public class BaseApplication extends Application {

    private static Context sContext;
    private static Resources sResources;

    private static String sLastToastMessage = "";
    private static long sLastToastTime;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
        sResources = sContext.getResources();
    }

    public static synchronized Context context() {
        return sContext;
    }

    public static Resources resources() {
        return sResources;
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToast(String message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(resources().getString(message), duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon, int gravity) {
        showToast(resources().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon, int gravity, Object... args) {
        showToast(resources().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            if (message.equalsIgnoreCase(sLastToastMessage) ||
                    Math.abs(sLastToastTime - System.currentTimeMillis()) < 2000) {
                return;
            }

            View view = LayoutInflater.from(context()).inflate(R.layout.view_toast, null);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            tvMessage.setText(message);
            if (icon != 0) {
                ivIcon.setImageResource(icon);
                ivIcon.setVisibility(View.VISIBLE);
            }
            Toast toast = new Toast(context());
            toast.setView(view);

            if (gravity == Gravity.CENTER)
                toast.setGravity(gravity, 0, 0);
            else
                toast.setGravity(gravity, 0, 35); // margin-bottom:35

            toast.setDuration(duration);
            toast.show();

            sLastToastMessage = message;
            sLastToastTime = System.currentTimeMillis();
        }
    }
}
