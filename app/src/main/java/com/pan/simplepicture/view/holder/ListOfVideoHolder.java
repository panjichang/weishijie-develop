package com.pan.simplepicture.view.holder;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pan.simplepicture.R;
import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.inter.ParallaxViewController;
import com.pan.simplepicture.utils.FrecsoUtils;
import com.pan.simplepicture.view.activity.PlayActivity;

import butterknife.Bind;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class ListOfVideoHolder extends BaseHolder<AbsVideoRes> {
    public ListOfVideoHolder(View view) {
        super(view);
    }

    @Bind(R.id.image)
    public SimpleDraweeView image;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.image_serie)
    public SimpleDraweeView image_serie;

    @Override
    public void init() {
        super.init();
        Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");
        tv_title.setTypeface(mTypeface);
        Object mObject = mView.getTag(R.id.tag_first);
        if (mObject != null && mObject instanceof ParallaxViewController) {
            ((ParallaxViewController) mObject).imageParallax(image);
        }
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayActivity.class);
                intent.putExtra("video", mData);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void setData(AbsVideoRes mData) {
        super.setData(mData);
        tv_title.setText(mData.getVideoTitle());
        FrecsoUtils.loadImage(mData.getVideoThumbnail(), image);
        FrecsoUtils.loadImage(mData.getSmallVideoThumbnail(), image_serie);
    }
}
