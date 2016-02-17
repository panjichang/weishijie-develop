package com.pan.simplepicture.view.holder;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pan.simplepicture.R;
import com.pan.simplepicture.utils.FrecsoUtils;
import com.pan.simplepicture.view.activity.WebActivity;

import butterknife.Bind;


public class GameHolder extends BaseHolder<AVObject>{
    @Bind(R.id.tv_play_count)
    public TextView tv_play_count;
    @Bind(R.id.tv_game)
    public TextView tv_game;

    @Bind(R.id.iv_png)
    public SimpleDraweeView iv_png;

    public GameHolder(View view) {
        super(view);
    }

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (TextUtils.isEmpty(mData.getString("package")) || "null".equals(mData.getString("package"))) {
                    intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("title", mData.getString("game_Name"));
                    intent.putExtra("url", mData.getString("game_Url"));
                } else {
                    int priority = mData.getInt("priority");
                    if (1 == priority) {
                        Uri uri = Uri
                                .parse("market://details?id=" + mData.getString("package"));
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else if (2 == priority) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(mData.getString("game_Url")));
                    }
                }
                if (intent != null)
                    mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void setData(AVObject mData) {
        super.setData(mData);
        tv_game.setText(mData.getString("game_Name"));
        tv_play_count.setText(mData.getString("play_Count"));
        FrecsoUtils.loadImage(mData.getString("game_Pic"), iv_png);
    }

}
