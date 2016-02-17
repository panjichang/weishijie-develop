package com.pan.simplepicture.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by sysadminl on 2015/12/1.
 */
public class ViewUtils {
    public static View getViewById(View view, int resId) {
        SparseArray<View> mViews = (SparseArray<View>) view.getTag();
        if (mViews == null) {
            mViews = new SparseArray<View>();
            view.setTag(mViews);
        }
        View child = mViews.get(resId);
        if (child == null) {
            child = view.findViewById(resId);
            mViews.put(resId, child);
        }
        return child;
    }
}
