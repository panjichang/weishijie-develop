package com.pan.simplepicture.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.BaseRecyclerAdapter;
import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.inter.LoadingState;
import com.pan.simplepicture.inter.OnRetryListener;
import com.pan.simplepicture.presenter.CommentPresenter;
import com.pan.simplepicture.presenter.PicturePresenter;
import com.pan.simplepicture.utils.NetWorkUtil;
import com.pan.simplepicture.view.holder.PictureHolder;
import com.pan.simplepicture.view.impl.IPictureView;
import com.pan.simplepicture.widget.LoadingView;

import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;

public class PictureFragment extends BaseFragment implements IPictureView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;

    @Override
    public void setAdapter(List<Juzimi> list) {
        if (mRecyclerView == null) return;
        pageNo = list.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter(list, R.layout.fragment_picture_item, PictureHolder.class);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (list.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (list.size() == 0) || (!((Juzimi) mAdapter.getItem(0)).url.equals(list.get(0).url)))
                mAdapter.setmDatas(list);
        }
    }

    @Override
    public void loadMore(List<Juzimi> list) {
        if (mRecyclerView != null && mAdapter != null && list != null) {
            if (list.size() < pageSize)
                canLoadMore = false;
            if (list.size() <= 0) {
                return;
            }
            mAdapter.addAll(list);
            pageNo += list.size();
        }
    }

    @Override
    public void onRefreshComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onLoadMoreComplete() {
        isLoadingMore = false;
    }

    @Override
    public PicturePresenter getPresenter() {
        return new PicturePresenter();
    }

    private StaggeredGridLayoutManager mLayoutManager;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.fl_loading)
    LoadingView fl_loading;

    @Override
    public void showSuccess() {
        fl_loading.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(mContext);
    }

    @Override
    public void showFaild() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_NO_NET);
    }


    private int mType;

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null || !(mPresenter instanceof PicturePresenter)) {
            return;
        }
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.disk_file_filter_pic_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
            }
        }).build();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore)
                    PictureFragment.this.onScrolled(recyclerView, dx, dy);
            }
        });
        params = new TreeMap<String, String>();
        switch (mType) {
            case 0:
                params.put("url", "http://www.juzimi.com/meitumeiju");
                break;
            case 1:
                params.put("url", "http://www.juzimi.com/meitumeiju/shouxiemeiju");
                break;
            case 2:
                params.put("url", "http://www.juzimi.com/meitumeiju/jingdianduibai");
                break;
        }
        params.put("page", String.valueOf(page));
        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    private TreeMap<String, String> params;
    private int page = 0;
    private int pageNo = 0;
    private final int pageSize = 21;

    @Override
    public void onRefresh() {
        page = 0;
        params.put("page", String.valueOf(page));
        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int[] visibleItem = mLayoutManager.findLastVisibleItemPositions(null);
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        int lastitem = Math.max(visibleItem[0], visibleItem[1]);

        if (!isLoadingMore && lastitem >= totalItemCount - 4 && dy > 0) {
            isLoadingMore = true;
            loadPage();//这里多线程也要手动控制isLoadingMore
        }

    }

    private void loadPage() {
        params.put("page", String.valueOf(++page));
        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
    }

    private boolean isLoadingMore;
}
