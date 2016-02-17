package com.pan.simplepicture.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by sysadminl on 2016/1/12.
 */
public class ScreenUtils {
    private static ScreenUtils screen;

    private ScreenUtils() {
    }

    private static int width;
    private static int height;

    public static ScreenUtils getInstance(Context mContext) {
        if (screen == null) {
            synchronized (ScreenUtils.class) {
                screen = new ScreenUtils();
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                width = dm.widthPixels;
                height = dm.heightPixels;
            }
        }
        return screen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
