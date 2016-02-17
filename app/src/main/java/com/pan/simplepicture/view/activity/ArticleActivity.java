package com.pan.simplepicture.view.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.pan.simplepicture.R;
import com.pan.simplepicture.bean.Lz13;
import com.pan.simplepicture.presenter.ArticleActPresenter;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.view.impl.IArticleActView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleActivity extends BaseActivity implements IArticleActView {
    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    private Lz13 mArticle;

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        mArticle = getIntent().getParcelableExtra("article");
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(mArticle.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return new ArticleActPresenter();
    }

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null) return;
        Map<String, String> params = new HashMap<>();
        params.put("url", mArticle.href);
        ((ArticleActPresenter) mPresenter).getArticleContent(params);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void setContent(String content) {
        if (tv_content == null) return;
        tv_content.setText(Html.fromHtml(content));
    }
}
