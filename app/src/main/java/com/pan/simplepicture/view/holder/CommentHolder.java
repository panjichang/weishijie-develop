package com.pan.simplepicture.view.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pan.simplepicture.R;
import com.pan.simplepicture.utils.FrecsoUtils;
import com.pan.simplepicture.utils.StringUtils;

import butterknife.Bind;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class CommentHolder extends BaseHolder<AVObject> {
    public CommentHolder(View view) {
        super(view);
    }

    @Bind(R.id.time)
    public TextView time;
    @Bind(R.id.user)
    public TextView user;
    @Bind(R.id.iv_icon)
    SimpleDraweeView iv_icon;
    @Bind(R.id.content)
    TextView content;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(AVObject mData) {
        super.setData(mData);
        time.setText(StringUtils.dayFormatter(mData.getString("published")));
        content.setText(mData.getString("content"));
        user.setText(TextUtils.isEmpty(mData.getString("screen_name")) ? "名字被狗吃了" : mData.getString("screen_name"));
        FrecsoUtils.loadImage(mData.getString("profile_image_url"), iv_icon);
    }
}
