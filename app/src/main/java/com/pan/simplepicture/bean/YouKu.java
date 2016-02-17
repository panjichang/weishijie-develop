package com.pan.simplepicture.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by sysadminl on 2016/1/13.
 */
public class YouKu {
    public String title;

    public String update;

    public String area;

    public String play_Count;

    public String star;

    public String des;

    public String mainPeople;

    public List<Resources> data;

    public void addPeople(String people){
        if(TextUtils.isEmpty(mainPeople)){
            mainPeople=people;
        }else{
            mainPeople= mainPeople+"/"+people;
        }
    }

}
