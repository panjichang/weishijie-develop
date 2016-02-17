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
public class Icon implements Parcelable{

    public Icons icon;

    protected Icon(Parcel in) {
    }

    public static final Creator<Icon> CREATOR = new Creator<Icon>() {
        @Override
        public Icon createFromParcel(Parcel in) {
            return new Icon(in);
        }

        @Override
        public Icon[] newArray(int size) {
            return new Icon[size];
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