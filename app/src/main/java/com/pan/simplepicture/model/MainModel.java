package com.pan.simplepicture.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.pan.simplepicture.model.impl.IMainModel;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class MainModel implements IMainModel {
    private  UMShareAPI mShareAPI;
    @Override
    public void login(Context mContext, SHARE_MEDIA platform, UMAuthListener mAuthListener) {
        if(mShareAPI==null) {
            mShareAPI = UMShareAPI.get(mContext);
        }
       // mShareAPI.getPlatformInfo((Activity)mContext, platform, mAuthListener);
        mShareAPI.doOauthVerify((Activity)mContext, platform, mAuthListener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(mShareAPI!=null){
            mShareAPI.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getPlateformInfo(Context mContext, SHARE_MEDIA platform,UMAuthListener mAuthListener){
        if(mShareAPI!=null){
            mShareAPI.getPlatformInfo((Activity)mContext, platform, mAuthListener);
        }
    }
}