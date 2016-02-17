package com.pan.simplepicture;

import android.os.Build;

import com.pan.simplepicture.bean.User;


public abstract class ConstantValue {
    public static String str = "35" + Build.BOARD.length() % 10
            + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
            + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
            + Build.HOST.length() % 10 + Build.ID.length() % 10
            + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
            + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
            + Build.TYPE.length() % 10 + Build.USER.length() % 10;
    public static String AT_URL = "http://i.animetaste.net/api/";

    public static String BAZOU_GET_REAL_URL = "http://vpbaby.5233game.com/";
    public static String BAOZOU_URL = "http://api.baomihua.tv/";

    public static String FLVCD_URL = " http://m.flvcd.com/";
    public static String URL_BEATY = "http://115.28.54.40:8080/beautyideaInterface/api/v1/";
    public static final int INITIAL_DELAY_MILLIS = 800;
    public static final String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 下拉刷新文字
     */
    public static final String PULL_STRING = "VSTAR";

    /**
     * QQ授权 appid
     */
    public static final String QQ_APPID = "1104167669";
    /**
     * QQ授权 appkey
     */
    public static final String QQ_APPKEY = "2NmgvDSPVEiIHkPD";

    /**
     * 微信平台 appid
     */
    public static final String WEIXIN_APPID = "wx03767b047a99b246";

    /**
     * AVOS appid
     */
    public static final String AVOS_APPID = "mlzmkqw1vlpe833ro2m4h5oo3p5isc0i27vtmso7m4fqd2vu";
    /**
     * AVOS appid
     */
    public static final String AVOS_APPKEY = "xtycr8ds169x5pywzd70egw7ph3xbrxmsvo4bzkzk3dtalo1";
}
