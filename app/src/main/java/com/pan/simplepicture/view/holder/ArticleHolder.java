package com.pan.simplepicture.view.holder;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pan.simplepicture.R;
import com.pan.simplepicture.bean.Lz13;
import com.pan.simplepicture.view.activity.ArticleActivity;

import butterknife.Bind;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class ArticleHolder extends BaseHolder<Lz13> {
    public ArticleHolder(View view) {
        super(view);
    }

    @Bind(R.id.rl_auth)
    RelativeLayout rl_auth;

    @Bind(R.id.tv_auth)
    TextView tv_auth;

    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.des)
    TextView des;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ArticleActivity.class);
                intent.putExtra("article", mData);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
        title.setText(mData.title);
        des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.auth)) {
            rl_auth.setVisibility(View.GONE);
        } else {
            rl_auth.setVisibility(View.VISIBLE);
            tv_auth.setText(mData.auth);
        }
    }
}
