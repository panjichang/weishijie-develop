package com.pan.simplepicture.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pan.simplepicture.inter.IBase;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.view.impl.IBaseView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by sysadminl on 2015/12/10.
 */
public abstract class BaseFragment<T extends BasePresenter<IBaseView>> extends Fragment implements IBase {

    protected BasePresenter mPresenter;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.attach((IBaseView) this);
        }
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mContext = mRootView.getContext();
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.detachView();
            mPresenter = null;
        }
        mContext = null;
        super.onDestroy();
    }
}
