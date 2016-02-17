/**
  * Copyright 2015 aTool.org 
  */
package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2015-12-18 12:34:41
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Videosource implements Parcelable{
    public String uhd;
    public String hd;
    public String sd;
    public Videosource(){}
    protected Videosource(Parcel in) {
        uhd = in.readString();
        hd = in.readString();
        sd = in.readString();
    }

    public static final Creator<Videosource> CREATOR = new Creator<Videosource>() {
        @Override
        public Videosource createFromParcel(Parcel in) {
            return new Videosource(in);
        }

        @Override
        public Videosource[] newArray(int size) {
            return new Videosource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uhd);
        parcel.writeString(hd);
        parcel.writeString(sd);
    }
}