package com.pan.simplepicture.inter;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by sysadminl on 2016/1/20.
 */
public interface OnShareListener {
    public void onShareData(ShareAction action);

    public void onShareSuccess(SHARE_MEDIA platform);

    public void onShareFailed(SHARE_MEDIA platform);

    public void onCancle();

    public void onShareCancle(SHARE_MEDIA platform);
}