package com.pan.simplepicture.view.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pan.simplepicture.R;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.utils.NetWorkUtil;
import com.pan.simplepicture.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by sysadminl on 2016/1/25.
 */
public class WebActivity extends BaseActivity {
    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private String url, title;

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (NetWorkUtil.isNetWorkConnected(this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_web;
    }
}
