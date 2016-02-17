package com.pan.simplepicture.model.impl;

import android.content.Context;
import android.content.Intent;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IMainModel extends BaseModel {
    public void login(Context mContext, SHARE_MEDIA platform, UMAuthListener mAuthListener);

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void getPlateformInfo(Context mContext, SHARE_MEDIA platform,UMAuthListener mAuthListener);
}
