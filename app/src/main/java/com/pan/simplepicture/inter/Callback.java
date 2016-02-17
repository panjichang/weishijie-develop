package com.pan.simplepicture.inter;

/**
 * Created by sysadminl on 2016/1/12.
 */
public interface Callback<T> {
    public void onSccuss(T data);

    public void onFaild();
}
