package com.pan.simplepicture;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pan.simplepicture.utils.UserManager;
import com.umeng.socialize.PlatformConfig;

public class PanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AVOSCloud.initialize(this, "mlzmkqw1vlpe833ro2m4h5oo3p5isc0i27vtmso7m4fqd2vu", "xtycr8ds169x5pywzd70egw7ph3xbrxmsvo4bzkzk3dtalo1");
        initShare();
        UserManager.getInstance().init(this);
    }

    private void initShare() {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx03767b047a99b246", "2ad7e624e5c14dc6c2773a506cd90f24");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3248011080", "cdcbc3aa283e450f5aa2be5052cfeb4a");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1104167669", "2NmgvDSPVEiIHkPD");
    }
}
