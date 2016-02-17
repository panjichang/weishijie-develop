package com.pan.simplepicture.bean;

import java.util.List;

/**
 * Created by sysadminl on 2016/1/5.
 */
public class Episode {
    public int id;
    public String title;
    public String created_at;
    public String updated_at;
    public boolean end;
    public int series_id;
    public List<Videos> videos;

}
