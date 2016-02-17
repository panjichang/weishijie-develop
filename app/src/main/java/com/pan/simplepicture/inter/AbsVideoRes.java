package com.pan.simplepicture.inter;

import android.os.Parcelable;

/**
 * Created by sysadminl on 2015/12/21.
 */
public abstract class AbsVideoRes implements Parcelable {
    public abstract String getVideoTitle();

    public abstract String getVideoDes();

    public abstract String getVideoDuration();

    public abstract String getVideoThumbnail();

    public abstract String getSmallVideoThumbnail();

    public abstract String getVideoId();

    public abstract int getType();

    public abstract String getUrl();

    public void setVideoInfo(){}
}
