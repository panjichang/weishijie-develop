package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sysadminl on 2016/1/12.
 */
public class Juzimi implements Parcelable {
    public String url;

    public String content;

    public String sender;

    public int x;

    public int y;

    public int width;

    public int height;

    public int maxWidth;

    public int maxHeight;

    public Juzimi() {
    }


    protected Juzimi(Parcel in) {
        url = in.readString();
        content = in.readString();
        sender = in.readString();
        x = in.readInt();
        y = in.readInt();
        width = in.readInt();
        height = in.readInt();
        maxWidth = in.readInt();
        maxHeight = in.readInt();
    }

    public static final Creator<Juzimi> CREATOR = new Creator<Juzimi>() {
        @Override
        public Juzimi createFromParcel(Parcel in) {
            return new Juzimi(in);
        }

        @Override
        public Juzimi[] newArray(int size) {
            return new Juzimi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(content);
        parcel.writeString(sender);
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(maxWidth);
        parcel.writeInt(maxHeight);
    }
}
