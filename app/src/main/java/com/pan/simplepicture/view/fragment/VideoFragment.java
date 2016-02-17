package com.pan.simplepicture.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pan.simplepicture.ConstantValue;
import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.BaseRecyclerAdapter;
import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.inter.LoadingState;
import com.pan.simplepicture.inter.OnRetryListener;
import com.pan.simplepicture.inter.ParallaxViewController;
import com.pan.simplepicture.presenter.PicturePresenter;
import com.pan.simplepicture.presenter.VideoPresenter;
import com.pan.simplepicture.utils.MD5Utils;
import com.pan.simplepicture.utils.NetWorkUtil;
import com.pan.simplepicture.view.holder.ListOfVideoHolder;
import com.pan.simplepicture.view.holder.VideoHolder;
import com.pan.simplepicture.view.impl.IVideoView;
import com.pan.simplepicture.widget.LoadingView;

import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;

public class VideoFragment extends BaseFragment implements IVideoView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;

    @Override
    public void setAdapter(List<? extends AbsVideoRes> list) {
        if (mRecyclerView == null) return;
        pageNo = list.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            if (params.containsKey("res_Type") && params.get("res_Type").equals("1")) {
                mAdapter = new BaseRecyclerAdapter(list, R.layout.cell_item, ListOfVideoHolder.class).setTag(R.id.tag_first, mController);
            } else {
                mAdapter = new BaseRecyclerAdapter(list, R.layout.res_item, VideoHolder.class).setTag(R.id.tag_first, mController).setTag(R.id.tag_second, mType);
            }
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (list.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (list.size() == 0) || (!((AbsVideoRes) mAdapter.getItem(0)).getVideoId().equals(list.get(0).getVideoId())))
                mAdapter.setmDatas(list);
        }
    }

    @Override
    public void loadMore(List<? extends AbsVideoRes> list) {
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

    private int mType = 0;

    public void setType(int mType) {
        this.mType = mType;
    }

    @Override
    public VideoPresenter getPresenter() {
        return new VideoPresenter();
    }

    private ParallaxViewController mController;
    private LinearLayoutManager mLinearLayoutManager;

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


    @Override
    public void bindView(Bundle savedInstanceState) {
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.movie_store_empty_view).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                onRefresh();
            }
        }).build();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mController = new ParallaxViewController() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore)
                    VideoFragment.this.onScrolled(recyclerView, dx, dy);
            }
        };
        mController.registerImageParallax(mRecyclerView);
        if (mPresenter == null || !(mPresenter instanceof VideoPresenter)) {
            return;
        }
        params = new TreeMap<String, String>();
        switch (mType) {
            //全球动画精选
            case 0:
                long time = System.currentTimeMillis() / 1000L;
                params.put("api_key", "android");
                params.put("timestamp", String.valueOf(time));
                params.put("page", String.valueOf(page));
                String str = MD5Utils.getAccessToken(params);
                params.put("access_token", str);
                ((VideoPresenter) mPresenter).getATVideos(params);
                break;
            //最美创意
            case 1:
                params.put("deviceModel", Build.MODEL.replace(" ", "+"));
                params.put("plamformVersion", Build.VERSION.RELEASE);
                params.put("deviceName", Build.MANUFACTURER);
                params.put("plamform", "Android");
                params.put("pageNo", String.valueOf(pageNo));
                params.put("pageSize", String.valueOf(pageSize));
                params.put("imieId", MD5Utils.MD5(ConstantValue.str + ConstantValue.str));
                ((VideoPresenter) mPresenter).getBeautifulVideos(params);
                break;
            //游戏CG
            case 2:
                params.put("res_Type", "0");
                params.put("type", "&2&");
                params.put("orderBy", "createdAt");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            //电影预告花絮
            case 3:
                params.put("res_Type", "0");
                params.put("type", "&3&");
                params.put("orderBy", "createdAt");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            case 8:
                params.put("res_Type", "0");
                params.put("type", "&8&");
                params.put("orderBy", "createdAt");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            case 9:
                params.put("res_Type", "0");
                params.put("type", "&9&");
                params.put("orderBy", "createdAt");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            //mv
            case 4:
                params.put("res_Type", "0");
                params.put("type", "&4&");
                params.put("orderBy", "createdAt");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            //院线电影
            case 5:
                params.put("client_id", "20230302");
                params.put("pagesize", String.valueOf(pageSize));
                params.put("page", String.valueOf(page));
                ((VideoPresenter) mPresenter).getCinemaMovies(params);
                break;
            //电影
            case 6:
                params.put("type", "3");
                params.put("pagesize", String.valueOf(pageSize));
                params.put("page", String.valueOf(page));
                ((VideoPresenter) mPresenter).getMovies(params);
                break;
            //热门电影
            case 7:
                params.put("type", "2");
                params.put("pagesize", String.valueOf(pageSize));
                params.put("page", String.valueOf(page));
                ((VideoPresenter) mPresenter).getMovies(params);
                break;
            //轻松学堂
            case 100:
                //音乐舞蹈
            case 101:
                //访谈演讲
            case 102:
                //娱乐八卦
            case 103:
                //恶搞配音
            case 104:
                //搞笑
            case 105:
                //吐槽
            case 106:
                //体育
            case 107:
                //汽车
            case 108:
                //脱口秀
            case 109:
                //生活
            case 110:
                //动漫
            case 111:
                //微电影
            case 112:
                //记录片
            case 113:
                params.put("type", "&" + mType + "&");
                params.put("res_Type", "1");
                params.put("orderBy", "play_Count");
                params.put("pageSize", String.valueOf(pageSize));
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    private TreeMap<String, String> params;
    private int page = 1;
    private int pageNo = 0;
    private final int pageSize = 10;

    @Override
    public void onRefresh() {
        switch (mType) {
            case 0:
                page = 1;
                params.clear();
                long time = System.currentTimeMillis() / 1000L;
                params.put("api_key", "android");
                params.put("timestamp", String.valueOf(time));
                params.put("page", String.valueOf(page));
                String str = MD5Utils.getAccessToken(params);
                params.put("access_token", str);
                ((VideoPresenter) mPresenter).getATVideos(params);
                break;
            case 1:
                pageNo = 0;
                params.put("pageNo", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getBeautifulVideos(params);
                break;
            //游戏CG
            case 2:
                //电影预告花絮
            case 3:
                //mv
            case 8:
            case 9:
            case 4:
                pageNo = 0;
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            //院线电影
            case 5:
                page = 1;
                params.put("page", String.valueOf(page));
                ((VideoPresenter) mPresenter).getCinemaMovies(params);
                break;
            //电影
            case 6:
                //热门电影
            case 7:
                page = 1;
                params.put("page", String.valueOf(page));
                ((VideoPresenter) mPresenter).getMovies(params);
                break;
            //轻松学堂
            case 100:
                //音乐舞蹈
            case 101:
                //访谈演讲
            case 102:
                //娱乐八卦
            case 103:
                //恶搞配音
            case 104:
                //搞笑
            case 105:
                //吐槽
            case 106:
                //体育
            case 107:
                //汽车
            case 108:
                //脱口秀
            case 109:
                //生活
            case 110:
                //动漫
            case 111:
                //微电影
            case 112:
                //记录片
            case 113:
                pageNo = 0;
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
        }
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        if (!isLoadingMore && lastVisibleItem >= totalItemCount - 2 && dy > 0) {
            isLoadingMore = true;
            loadPage();//这里多线程也要手动控制isLoadingMore
        }
    }

    private void loadPage() {
        switch (mType) {
            case 0:
                params.clear();
                long time = System.currentTimeMillis() / 1000L;
                params.put("api_key", "android");
                params.put("timestamp", String.valueOf(time));
                params.put("page", String.valueOf(++page));
                String str = MD5Utils.getAccessToken(params);
                params.put("access_token", str);
                ((VideoPresenter) mPresenter).getATVideos(params);
                break;
            case 1:
                params.put("pageNo", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getBeautifulVideos(params);
                break;
            //游戏
            case 2:
                //电影预告花絮
            case 3:
            case 9:
            case 8:
                //mv
            case 4:
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
            //院线电影
            case 5:
                params.put("page", String.valueOf(++page));
                ((VideoPresenter) mPresenter).getCinemaMovies(params);
                break;
            //电影
            case 6:
                //热门电影
            case 7:
                params.put("page", String.valueOf(++page));
                ((VideoPresenter) mPresenter).getMovies(params);
                break;
            //轻松学堂
            case 100:
                //音乐舞蹈
            case 101:
                //访谈演讲
            case 102:
                //娱乐八卦
            case 103:
                //恶搞配音
            case 104:
                //搞笑
            case 105:
                //吐槽
            case 106:
                //体育
            case 107:
                //汽车
            case 108:
                //脱口秀
            case 109:
                //生活
            case 110:
                //动漫
            case 111:
                //微电影
            case 112:
                //记录片
            case 113:
                params.put("skip", String.valueOf(pageNo));
                ((VideoPresenter) mPresenter).getYouKuVideos(params);
                break;
        }
    }

    private boolean isLoadingMore;
}
