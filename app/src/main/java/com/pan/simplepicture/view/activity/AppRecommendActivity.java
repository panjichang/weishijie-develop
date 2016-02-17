package com.pan.simplepicture.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.BaseRecyclerAdapter;
import com.pan.simplepicture.inter.LoadingState;
import com.pan.simplepicture.inter.OnRetryListener;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.presenter.GamePresenter;
import com.pan.simplepicture.presenter.RecommendPresenter;
import com.pan.simplepicture.utils.NetWorkUtil;
import com.pan.simplepicture.view.holder.GameHolder;
import com.pan.simplepicture.view.impl.IGameView;
import com.pan.simplepicture.view.impl.IRecommendView;
import com.pan.simplepicture.widget.LoadingView;

import java.util.List;

import butterknife.Bind;

public class AppRecommendActivity extends BaseActivity implements IRecommendView {

    //save our header or result
    private RecyclerView mRecyclerView;

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_small_game_toolbar;
    }

    @Override
    public void setActionBar() {

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Bind(R.id.fl_loading)
    LoadingView fl_loading;

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null || !(mPresenter instanceof RecommendPresenter)) return;
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.disk_file_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                ((RecommendPresenter) mPresenter).getApps();
            }
        }).build();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        ((RecommendPresenter) mPresenter).getApps();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return new RecommendPresenter();
    }

    @Override
    public void setAdapter(List<AVObject> list) {
        if (mRecyclerView != null)
            mRecyclerView.setAdapter(new BaseRecyclerAdapter(list, R.layout.list_item_card_game, GameHolder.class));
    }

    @Override
    public void showSuccess() {
        fl_loading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(this);
    }

    @Override
    public void showFaild() {
        mRecyclerView.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        mRecyclerView.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_NO_NET);
    }
}
