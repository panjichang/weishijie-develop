package com.pan.simplepicture.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.bean.Episode;
import com.pan.simplepicture.bean.Episodes;
import com.pan.simplepicture.bean.PlayAddress;
import com.pan.simplepicture.bean.PlayUrl;
import com.pan.simplepicture.bean.VideoSources;
import com.pan.simplepicture.bean.Videos;
import com.pan.simplepicture.bean.YouKu;
import com.pan.simplepicture.model.PlayModel;
import com.pan.simplepicture.model.impl.IPlayModel;
import com.pan.simplepicture.utils.StringUtils;
import com.pan.simplepicture.view.impl.IPlayView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PlayPresenter extends BasePresenter<IPlayView> {
    private IPlayModel mIPlayModel;

    public PlayPresenter() {
        mIPlayModel = new PlayModel();
    }

    public void getVideoUrl(final Context mContext, final Map<String, String> params) {
        mIPlayModel.loadVideoUrl(params, new Callback<PlayUrl>() {
            @Override
            public void onResponse(Response<PlayUrl> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                if (response == null || response.body() == null || response.body().player == null)
                    return;
                mView.setVideoSource(response.body().player);
                play(mContext);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void play(Context mContext) {
        if (mView != null) {
            mView.sendPlayMessage(mView.getFlag() == true ? true : mIPlayModel.isAutoPlay(mContext));
        }
    }

    public void getBaozouVideoInfo(final Context mContext, final Map<String, String> params) {
        mIPlayModel.loadBaozouUrl(params, new Callback<Episodes>() {
            @Override
            public void onResponse(Response<Episodes> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                if (response == null || response.body() == null || response.body().episodes == null || response.body().episodes.size() == 0)
                    return;
                try {
                    //           mView.setVideoSource(response.body().episodes.get(0).videos.get(0).video_sources.get(0).source_url);
                    //           play(mContext);
                    List<VideoSources> list = new ArrayList<VideoSources>();
                    for (Episode episode : response.body().episodes) {
                        for (Videos videos : episode.videos) {
                            for (VideoSources video_sources :
                                    videos.video_sources) {
                                video_sources.type = Integer.parseInt(params.get("type"));
                                video_sources.name = videos.title;
                                video_sources.plays_count = videos.plays_count;
                                video_sources.des = videos.description;
                                video_sources.smallPic = videos.thumbnail.thumbnail.medium.url;
                                video_sources.bigPic = videos.thumbnail.thumbnail.big.url;
                                list.add(video_sources);
                            }
                        }
                    }
                    mView.setupViewPager(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getYoukuVideoInfo(Map<String, String> params) {
        mIPlayModel.parseYoukuHtml(params, new com.pan.simplepicture.inter.Callback<YouKu>() {

            @Override
            public void onSccuss(YouKu data) {
                if (mView == null)
                    return;
                mView.setInfo(data);
            }

            @Override
            public void onFaild() {

            }
        });
    }

    public void getBaozouRealUrl(final Context mContext, final Map<String, String> params) {
        mIPlayModel.loadBaozouAddress(params, new Callback<PlayAddress>() {
            @Override
            public void onResponse(Response<PlayAddress> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                try {
                    mView.setVideoSource(response.body().V.get(0).U);
                } catch (Exception e) {
                    mView.setVideoSource(params.get("url"));
                }
                play(mContext);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public void sendComment(final Map<String, String> params) {
        mIPlayModel.saveComment(params, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null && mView != null) {
                    mView.showCommentSuccess();
                }
            }
        });
    }

    public void getRealAddress(final Context mContext, final Map<String, String> params) {
        mIPlayModel.loadRealAddress(params, new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (mView == null)
                    return;
                if (response == null || TextUtils.isEmpty(response.body()))
                    return;
                mView.setVideoSource(StringUtils.getRealUrl(response.body()));
                play(mContext);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
