package com.pan.simplepicture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sysadminl on 2016/1/2.
 */
public class Icons implements Parcelable{
    public PicUrl big;
    public PicUrl medium;
    public PicUrl small;
    public PicUrl x;
    public PicUrl y;

    protected Icons(Parcel in) {
        big = in.readParcelable(PicUrl.class.getClassLoader());
        medium = in.readParcelable(PicUrl.class.getClassLoader());
        small = in.readParcelable(PicUrl.class.getClassLoader());
        x = in.readParcelable(PicUrl.class.getClassLoader());
        y = in.readParcelable(PicUrl.class.getClassLoader());
    }

    public static final Creator<Icons> CREATOR = new Creator<Icons>() {
        @Override
        public Icons createFromParcel(Parcel in) {
            return new Icons(in);
        }

        @Override
        public Icons[] newArray(int size) {
            return new Icons[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(big, i);
        parcel.writeParcelable(medium, i);
        parcel.writeParcelable(small, i);
        parcel.writeParcelable(x, i);
        parcel.writeParcelable(y, i);
    }
}
