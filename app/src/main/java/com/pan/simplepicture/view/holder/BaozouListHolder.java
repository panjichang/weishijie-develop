package com.pan.simplepicture.view.holder;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pan.simplepicture.R;
import com.pan.simplepicture.bean.VideoSources;
import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.utils.FrecsoUtils;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class BaozouListHolder extends BaseHolder<AbsVideoRes> {
    public BaozouListHolder(View view) {
        super(view);
    }

    @Bind(R.id.tv_baozou_title)
    public TextView tv_baozou_title;

    @Bind(R.id.tv_baozou_play_count)
    public TextView tv_baozou_play_count;

    @Bind(R.id.tv_baozou_time)
    public TextView tv_baozou_time;

    @Bind(R.id.baozou_pic)
    public SimpleDraweeView baozou_pic;

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
        tv_baozou_title.setText(mData.getVideoTitle());
        tv_baozou_time.setText(mData.getVideoDuration());
        if (mData instanceof VideoSources)
            tv_baozou_play_count.setText((((VideoSources) mData).plays_count + "次播放"));
        FrecsoUtils.loadImage(mData.getVideoThumbnail(), baozou_pic);
    }
}
