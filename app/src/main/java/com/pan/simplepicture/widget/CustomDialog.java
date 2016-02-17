package com.pan.simplepicture.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pan.simplepicture.R;


public class CustomDialog extends Dialog {

    private static int default_width = 160; // 默认宽度

    private static int default_height = 120;// 默认高度

    private Context mContext;

    public CustomDialog(Context context, View layout, int style) {

        super(context, style);

        mContext = context;
    }

    public CustomDialog(Context context, View layout, int width, int height,
                        int gravity, int style) {
        super(context, style);
        setContentView(layout);
        mContext = context;
        // initSocialSDK();
        // initPlatformMap();
        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height;
        params.width = width;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, View layout, int width, int height,
                        int gravity) {
        super(context, R.style.dialog);
        setContentView(layout);
        mContext = context;
        // initSocialSDK();
        // initPlatformMap();
        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height;
        params.width = width;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    ;

    private void startShare() {
        // TODO Auto-generated method stub
        /*
		 * if (mOnShareDataListener != null) {
		 * mOnShareDataListener.onShareData(mController); }
		 */
        showCustomUI(false);
    }

	/*
	 * Map<String, SHARE_MEDIA> mPlatformsMap = new HashMap<String,
	 * SHARE_MEDIA>(); private final UMSocialService mController =
	 * UMServiceFactory .getUMSocialService("com.umeng.share");
	 */
	/*
	*//**
     * 初始化SDK，添加一些平台
     */
	/*
	 * private void initSocialSDK() { // 添加QQ平台 UMQQSsoHandler qqHandler = new
	 * UMQQSsoHandler((Activity) mContext, ConstantValue.QQ_APPID,
	 * ConstantValue.QQ_APPKEY); qqHandler.addToSocialSDK();
	 * 
	 * // 添加QQ空间平台 QZoneSsoHandler qzoneHandler = new QZoneSsoHandler((Activity)
	 * mContext, ConstantValue.QQ_APPID, ConstantValue.QQ_APPKEY);
	 * qzoneHandler.addToSocialSDK();
	 * 
	 * // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID String appID =
	 * ConstantValue.WEIXIN_APPID; // 添加微信平台 UMWXHandler wxHandler = new
	 * UMWXHandler(mContext, appID); wxHandler.addToSocialSDK();
	 * 
	 * // 支持微信朋友圈 UMWXHandler wxCircleHandler = new UMWXHandler(mContext,
	 * appID); wxCircleHandler.setToCircle(true);
	 * wxCircleHandler.addToSocialSDK();
	 * 
	 * // 设置新浪SSO handler mController.getConfig().setSsoHandler(new
	 * SinaSsoHandler());
	 * 
	 * // 设置腾讯微博SSO handler mController.getConfig().setSsoHandler(new
	 * TencentWBSsoHandler());
	 * 
	 * }
	 * 
	 * private int[] imgs = { R.drawable.wechat_selector,
	 * R.drawable.wechatmoment_selector, R.drawable.qq_selector,
	 * R.drawable.qzone_selector, R.drawable.sina_selector,
	 * R.drawable.tencent_selector };
	 *//**
     * 初始化平台map
     */
	/*
	 * private void initPlatformMap() { mPlatformsMap.put("微信好友",
	 * SHARE_MEDIA.WEIXIN); mPlatformsMap.put("朋友圈", SHARE_MEDIA.WEIXIN_CIRCLE);
	 * mPlatformsMap.put("QQ好友", SHARE_MEDIA.QQ); mPlatformsMap.put("QQ空间",
	 * SHARE_MEDIA.QZONE); mPlatformsMap.put("新浪微博", SHARE_MEDIA.SINA);
	 * mPlatformsMap.put("腾讯微博", SHARE_MEDIA.TENCENT); }
	 */
    /**
     * 分享监听器
     */
	/*
	 * SnsPostListener mShareListener = new SnsPostListener() {
	 * 
	 * @Override public void onStart() { Toast.makeText(mContext, "开始分享.",
	 * Toast.LENGTH_SHORT).show(); }
	 * 
	 * @Override public void onComplete(SHARE_MEDIA platform, int stCode,
	 * SocializeEntity entity) { if (stCode == 200) { Toast.makeText(mContext,
	 * "分享成功", Toast.LENGTH_SHORT).show(); } else { String eMsg = ""; if (stCode
	 * == -101) { eMsg = "没有授权"; }
	 * 
	 * } } };
	 */
    String[] strings;

    private void showCustomUI(final boolean isDirectShare) {
    }

	/*
	 * public interface OnShareDataListener { public void
	 * onShareData(UMSocialService mController); }
	 */
    // private OnShareDataListener mOnShareDataListener;

	/*
	 * public void setOnShareDataListener(OnShareDataListener
	 * mOnShareDataListener) { this.mOnShareDataListener = mOnShareDataListener;
	 * }
	 */

}
