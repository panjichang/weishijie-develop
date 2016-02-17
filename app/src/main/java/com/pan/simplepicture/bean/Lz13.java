package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class Lz13 implements Parcelable {
    public String title;
    public String href;
    public String text;
    public String auth;
    public Lz13(){}
    protected Lz13(Parcel in) {
        title = in.readString();
        href = in.readString();
        text = in.readString();
        auth = in.readString();
    }

    public static final Creator<Lz13> CREATOR = new Creator<Lz13>() {
        @Override
        public Lz13 createFromParcel(Parcel in) {
            return new Lz13(in);
        }

        @Override
        public Lz13[] newArray(int size) {
            return new Lz13[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(href);
        parcel.writeString(text);
        parcel.writeString(auth);
    }
}
