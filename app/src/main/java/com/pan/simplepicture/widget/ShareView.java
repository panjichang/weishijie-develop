package com.pan.simplepicture.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.ShareAdapter;
import com.pan.simplepicture.bean.Share;
import com.pan.simplepicture.inter.OnShareListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class ShareView extends LinearLayout {
    private Context mContext;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShareView(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
        // TODO Auto-generated constructor stub
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        // TODO Auto-generated constructor stub
        init();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        // TODO Auto-generated constructor stub
        init();
    }

    public ShareView(Context context) {
        super(context);
        this.mContext = context;
        // TODO Auto-generated constructor stub
        init();
    }

    @Bind(R.id.gv_share)
    GridView gv_share;

    UMImage image;

    @OnItemClick(R.id.gv_share)
    public void onItemClick(int position) {
        if (mOnShareListener != null)
            mOnShareListener.onCancle();
            mOnShareListener.onShareData(new ShareAction((Activity) mContext)
                    .setPlatform(mAdapter.getItem(position).share_media)
                    .withMedia(image)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onResult(SHARE_MEDIA platform) {
                            mOnShareListener.onShareSuccess(platform);
                        }

                        @Override
                        public void onError(SHARE_MEDIA platform, Throwable t) {
                            mOnShareListener.onShareFailed(platform);
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA platform) {
                            mOnShareListener.onShareCancle(platform);
                        }
                    }));
    }

    @OnClick(R.id.btn_cancle)
    public void onDismiss() {
        if (mOnShareListener != null)
            mOnShareListener.onCancle();
    }

    private SHARE_MEDIA[] share_medias = {
            SHARE_MEDIA.QQ,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.QZONE,
            SHARE_MEDIA.SINA
    };

    private int[] imgs = {R.drawable.qq_selector, R.drawable.wechatmoment_selector, R.drawable.wechat_selector,
            R.drawable.qzone_selector, R.drawable.sina_selector};

    private String[] platform_names = {"QQ", "朋友圈", "微信", "QQ空间", "新浪微博"};

    private void init() {
        View view = View.inflate(mContext, R.layout.share_dialog, this);
        ButterKnife.bind(this, view);
        List<Share> mList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            Share share = new Share();
            share.share_media = share_medias[i];
            share.resPic = imgs[i];
            share.resStr = platform_names[i];
            mList.add(share);
        }
        mAdapter = new ShareAdapter(mContext, mList);
        gv_share.setAdapter(mAdapter);
        image = new UMImage(mContext,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
    }

    private ShareAdapter mAdapter;

    public void setOnShareListener(OnShareListener mOnShareListener) {
        this.mOnShareListener = mOnShareListener;
    }

    private OnShareListener mOnShareListener;

}
