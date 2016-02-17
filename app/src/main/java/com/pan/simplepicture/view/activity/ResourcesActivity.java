package com.pan.simplepicture.view.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pan.simplepicture.R;
import com.pan.simplepicture.presenter.BasePresenter;

/**
 * 视频资源
 *
 * @author pan
 */
public class ResourcesActivity extends BaseActivity {


    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_list;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
