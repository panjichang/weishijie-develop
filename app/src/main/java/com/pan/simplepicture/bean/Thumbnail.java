/**
  * Copyright 2016 aTool.org 
  */
package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2016-01-02 1:48:3
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Thumbnail implements Parcelable{

    public Thumbnails thumbnail;

    protected Thumbnail(Parcel in) {
    }

    public static final Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel in) {
            return new Thumbnail(in);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}