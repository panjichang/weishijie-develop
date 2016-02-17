/**
 * Copyright 2016 aTool.org
 */
package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.utils.StringUtils;

/**
 * Auto-generated: 2016-01-02 1:48:3
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class VideoSources extends AbsVideoRes implements Parcelable{
    public int type;
    public int id;
    public String provider;
    public String source_url;
    public int size;
    public String name;
    public int duration;
    public String created_at;
    public String updated_at;
    public String quality;
    public String direct_url;
    public String bigPic;
    public String smallPic;
    public String des;
    public int plays_count;


    protected VideoSources(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        provider = in.readString();
        source_url = in.readString();
        size = in.readInt();
        name = in.readString();
        duration = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        quality = in.readString();
        direct_url = in.readString();
        bigPic = in.readString();
        smallPic = in.readString();
        des = in.readString();
        plays_count = in.readInt();
    }

    public static final Creator<VideoSources> CREATOR = new Creator<VideoSources>() {
        @Override
        public VideoSources createFromParcel(Parcel in) {
            return new VideoSources(in);
        }

        @Override
        public VideoSources[] newArray(int size) {
            return new VideoSources[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeInt(id);
        parcel.writeString(provider);
        parcel.writeString(source_url);
        parcel.writeInt(size);
        parcel.writeString(name);
        parcel.writeInt(duration);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeString(quality);
        parcel.writeString(direct_url);
        parcel.writeString(bigPic);
        parcel.writeString(smallPic);
        parcel.writeString(des);
        parcel.writeInt(plays_count);
    }


    @Override
    public String getVideoTitle() {
        return name;
    }

    @Override
    public String getVideoDes() {
        return des;
    }

    @Override
    public String getVideoDuration() {
        return StringUtils.timeFormatter(duration);
    }

    @Override
    public String getVideoThumbnail() {
        return bigPic;
    }

    @Override
    public String getSmallVideoThumbnail() {
        return smallPic;
    }

    @Override
    public String getVideoId() {
        return String.valueOf(id);
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getUrl() {
        return source_url;
    }
}