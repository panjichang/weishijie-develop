package com.pan.simplepicture.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.bean.Material;
import com.pan.simplepicture.model.impl.IProvideModel;
import com.pan.simplepicture.model.impl.ISettingModel;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class SettingModel implements ISettingModel {


    @Override
    public void sendFeedback(Map<String, String> params, SaveCallback mCallback) {
        AVObject object = AVObject.create(params.get("class"));
        object.put("content", params.get("content"));
        object.saveInBackground(mCallback);
    }
}