package com.pan.simplepicture.presenter;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.pan.simplepicture.bean.BaoZou;
import com.pan.simplepicture.bean.Beaty;
import com.pan.simplepicture.bean.Column;
import com.pan.simplepicture.bean.Resources;
import com.pan.simplepicture.bean.Videos;
import com.pan.simplepicture.model.VideoModel;
import com.pan.simplepicture.model.impl.IVideoModel;
import com.pan.simplepicture.view.impl.IVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class VideoPresenter extends BasePresenter<IVideoView> {
    private IVideoModel mIVideoModel;

    public VideoPresenter() {
        mIVideoModel = new VideoModel();
    }

    public void getATVideos(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIVideoModel.loadATVideos(params, new Callback<Videos>() {
            @Override
            public void onResponse(Response<Videos> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (response == null || response.body() == null || response.body().data == null || response.body().data.list == null || response.body().data.list.anime == null) {
                    if ("1".equals(params.get("page"))) {
                        mView.showFaild();
                    }
                    return;
                }
                if ("1".equals(params.get("page"))) {
                    if (response.body().data.list.anime.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(response.body().data.list.anime);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(response.body().data.list.anime);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("1".equals(params.get("page"))) {
                    mView.showFaild();
                }
                return;
            }
        });
    }

    public void getBeautifulVideos(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIVideoModel.loadBeautifulVideos(params, new Callback<Beaty>() {
            @Override
            public void onResponse(Response<Beaty> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (response == null || response.body() == null || response.body().resources == null) {
                    if ("0".equals(params.get("pageNo"))) {
                        mView.showFaild();
                    }
                    return;
                }
                if ("0".equals(params.get("pageNo"))) {
                    if (0 == response.body().resources.size()) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(response.body().resources);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(response.body().resources);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("pageNo"))) {
                    mView.showFaild();
                }
            }
        });
    }

    public void getYouKuVideos(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIVideoModel.loadYouKuVideos(params, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (e == null && list != null) {
                    List<Resources> resList = new ArrayList<>();
                    for (AVObject object : list) {
                        Resources resources = new Resources();
                        resources.rsId = object.getString("log_Sid");
                        if ("1".equals(object.getString("res_Type"))) {
                            resources.type = 3;
                        } else if ("0".equals(object.getString("res_Type")) && TextUtils.isEmpty(resources.rsId)) {
                            resources.type = 1;
                            resources.rsId = "XMTQ0MjgwNDUzNg%3D%3D";
                        }
                        resources.link = object.getString("website");
                        if (!resources.link.startsWith("http://www.soku.com/v")) {
                            if (resources.link.contains("?")) {
                                resources.link = resources.link.split("[?]")[0];
                            }
                            if (resources.link.contains("q_")) {
                                resources.link = "http://www.soku.com/v/?keyword=" + resources.link.split("q_")[1];
                            }
                        }
                        resources.title = object.getString("name");
                        resources.thumbnailV2 = object.getString("big_Pic");
                        resources.thumbnail = object.getString("small_Pic");
                        resources.duration = object.getString("duration");
                        resources.description = object.getString("des");
                        resList.add(resources);
                    }
                    if ("0".equals(params.get("skip"))) {
                        if (resList.size() == 0) {
                            mView.showEmpty();
                        } else {
                            mView.setAdapter(resList);
                            mView.showSuccess();
                        }
                    } else {
                        mView.loadMore(resList);
                    }
                } else {
                    if ("0".equals(params.get("skip"))) {
                        mView.showFaild();
                    }
                }
            }
        });
    }

    public void getCinemaMovies(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIVideoModel.loadCinemaMovies(params, new Callback<BaoZou>() {
            @Override
            public void onResponse(Response<BaoZou> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (response == null || response.body() == null || response.body().column == null || response.body().column.series == null) {
                    if ("1".equals(params.get("page"))) {
                        mView.showFaild();
                    }
                    return;
                }
                if ("1".equals(params.get("page"))) {
                    if (response.body().column.series.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(response.body().column.series);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(response.body().column.series);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("1".equals(params.get("page"))) {
                    mView.showFaild();
                }
            }
        });
    }

    public void getMovies(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIVideoModel.loadMovies(params, new Callback<Column>() {
            @Override
            public void onResponse(Response<Column> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (response == null || response.body() == null || response.body().series == null) {
                    if ("1".equals(params.get("page"))) {
                        mView.showFaild();
                    }
                    return;
                }
                if ("1".equals(params.get("page"))) {
                    if (0 == response.body().series.size()) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(response.body().series);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(response.body().series);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("1".equals(params.get("page"))) {
                    mView.showFaild();
                }
            }
        });
    }

}
