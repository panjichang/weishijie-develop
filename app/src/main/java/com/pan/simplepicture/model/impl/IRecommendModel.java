package com.pan.simplepicture.model.impl;

import com.avos.avoscloud.FindCallback;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IRecommendModel extends BaseModel{
    void loadApps(FindCallback callback);
}
