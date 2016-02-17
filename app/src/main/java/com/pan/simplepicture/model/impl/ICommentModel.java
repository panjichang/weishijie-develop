package com.pan.simplepicture.model.impl;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ICommentModel extends BaseModel {
    void loadComment(Map<String, String> params, FindCallback<AVObject> mCallback);
}
