/**
 * Copyright 2016 aTool.org
 */
package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.utils.StringUtils;

/**
 * Auto-generated: 2016-01-02 1:48:3
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Series extends AbsVideoRes implements Parcelable {
    public int type = 2;
    public int id;
    public boolean recommend;
    public String description;
    public int subtitles_count;
    public String created_at;
    public String updated_at;
    public boolean star_online;
    public int online_count;
    public String plays_count;
    public Icon icon;
    public String title;
    public Latest latest;
    public int episode_mode;


    protected Series(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        description = in.readString();
        subtitles_count = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        online_count = in.readInt();
        plays_count = in.readString();
        icon = in.readParcelable(Icon.class.getClassLoader());
        title = in.readString();
        latest = in.readParcelable(Latest.class.getClassLoader());
        episode_mode = in.readInt();
        duration = in.readString();
        thumbnail = in.readString();
        smallThumbnail = in.readString();
        url = in.readString();
    }

    public static final Creator<Series> CREATOR = new Creator<Series>() {
        @Override
        public Series createFromParcel(Parcel in) {
            return new Series(in);
        }

        @Override
        public Series[] newArray(int size) {
            return new Series[size];
        }
    };

    @Override
    public String getVideoTitle() {
        return title;
    }

    @Override
    public String getVideoDes() {
        return "";
    }

    public String duration;

    @Override
    public void setVideoInfo() {
        duration = StringUtils.timeFormatter(latest.getVideo().duration);
        thumbnail = latest.thumbnail.thumbnail.url;
        smallThumbnail = latest.thumbnail.thumbnail.medium.url;
        url = latest.getVideo().source_url;
    }

    @Override
    public String getVideoDuration() {
        try {
            return StringUtils.stringFormatter(Integer.parseInt(plays_count));
        } catch (Exception e) {
            e.printStackTrace();
            return "100";
        }
        //  return TextUtils.isEmpty(duration) ? StringUtils.timeFormatter(latest.getVideo().duration) : duration;
    }

    public String thumbnail;

    @Override
    public String getVideoThumbnail() {
        return TextUtils.isEmpty(thumbnail) ? latest.thumbnail.thumbnail.url : thumbnail;
    }

    public String smallThumbnail;

    @Override
    public String getSmallVideoThumbnail() {
        return TextUtils.isEmpty(smallThumbnail) ? latest.thumbnail.thumbnail.medium.url : smallThumbnail;
    }

    @Override
    public String getVideoId() {
        return String.valueOf(id);
    }

    @Override
    public int getType() {
        return type;
    }

    public String url;

    @Override
    public String getUrl() {
        return TextUtils.isEmpty(url) ? latest.getVideo().source_url : url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeInt(subtitles_count);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeInt(online_count);
        parcel.writeString(plays_count);
        parcel.writeParcelable(icon, i);
        parcel.writeString(title);
        parcel.writeParcelable(latest, i);
        parcel.writeInt(episode_mode);
        parcel.writeString(duration);
        parcel.writeString(thumbnail);
        parcel.writeString(smallThumbnail);
        parcel.writeString(url);
    }
}