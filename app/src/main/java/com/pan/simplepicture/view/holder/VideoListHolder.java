package com.pan.simplepicture.view.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pan.simplepicture.R;
import com.pan.simplepicture.inter.AbsVideoRes;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class VideoListHolder extends BaseHolder<AbsVideoRes> {
    public VideoListHolder(View view) {
        super(view);
    }

    @Bind(R.id.tv_title)
    public TextView tv_title;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(mData);
            }
        });
    }

    @Override
    public void setData(AbsVideoRes mData) {
        super.setData(mData);
        if (mData.getVideoTitle().length() < 3) {
            tv_title.setGravity(Gravity.CENTER);
        }
        tv_title.setText(mData.getVideoTitle());
    }
}
