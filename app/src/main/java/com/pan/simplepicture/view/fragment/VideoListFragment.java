package com.pan.simplepicture.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.BaseRecyclerAdapter;
import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.presenter.PicturePresenter;
import com.pan.simplepicture.view.holder.BaozouListHolder;
import com.pan.simplepicture.view.holder.VideoListHolder;

import java.util.List;

import butterknife.Bind;

public class VideoListFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;

    public void setAdapter(List<? extends AbsVideoRes> list) {
        if (mRecyclerView == null) return;
        if (mAdapter == null) {
            if (2 == list.get(0).getType() || 100 == list.get(0).getType()) {
                mAdapter = new BaseRecyclerAdapter(list, R.layout.list_baozou_item, BaozouListHolder.class);
            } else {
                mAdapter = new BaseRecyclerAdapter(list, R.layout.list_video_item, VideoListHolder.class);
            }
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public PicturePresenter getPresenter() {
        return null;
    }

    private StaggeredGridLayoutManager mLayoutManager;

    private List<? extends AbsVideoRes> mRes;


    public void setRes(List<? extends AbsVideoRes> mRes) {
        this.mRes = mRes;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mRes == null || mRes.size() == 0) return;
        if (2 == mRes.get(0).getType() || 100 == mRes.get(0).getType()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            if (mRes.get(0).getVideoTitle().length() > 3) {
                mLayoutManager = new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);
            } else {
                mLayoutManager = new StaggeredGridLayoutManager(5,
                        StaggeredGridLayoutManager.VERTICAL);
            }
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        setAdapter(mRes);
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_video_fragment;
    }
}
