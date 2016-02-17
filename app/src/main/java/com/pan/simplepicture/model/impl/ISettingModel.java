package com.pan.simplepicture.model.impl;

import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.bean.Material;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ISettingModel extends BaseModel {
    void sendFeedback(Map<String,String> params,SaveCallback mCallback);
}
