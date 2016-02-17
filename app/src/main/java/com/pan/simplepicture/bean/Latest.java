/**
 * Copyright 2016 aTool.org
 */
package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Auto-generated: 2016-01-02 1:48:3
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Latest implements Parcelable{

    public int id;
    public int subtitles_count;
    public int duration;
    public String created_at;
    public String updated_at;
    public int series_id;
    public Thumbnail thumbnail;
    public int online_users;
    public int plays_count;
    public List<VideoSources> video_sources;
    public String name;
    public String description;
    public int items_count;
    public int number;
    public String title;

    protected Latest(Parcel in) {
        id = in.readInt();
        subtitles_count = in.readInt();
        duration = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        series_id = in.readInt();
        online_users = in.readInt();
        plays_count = in.readInt();
        name = in.readString();
        description = in.readString();
        items_count = in.readInt();
        number = in.readInt();
        title = in.readString();
    }

    public static final Creator<Latest> CREATOR = new Creator<Latest>() {
        @Override
        public Latest createFromParcel(Parcel in) {
            return new Latest(in);
        }

        @Override
        public Latest[] newArray(int size) {
            return new Latest[size];
        }
    };

    public VideoSources getVideo() {
        return video_sources.get(0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(subtitles_count);
        parcel.writeInt(duration);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeInt(series_id);
        parcel.writeInt(online_users);
        parcel.writeInt(plays_count);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(items_count);
        parcel.writeInt(number);
        parcel.writeString(title);
    }
}