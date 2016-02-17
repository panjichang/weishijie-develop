package com.pan.simplepicture.model;

import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.ConstantValue;
import com.pan.simplepicture.bean.Episodes;
import com.pan.simplepicture.bean.PlayAddress;
import com.pan.simplepicture.bean.PlayUrl;
import com.pan.simplepicture.bean.Resources;
import com.pan.simplepicture.bean.YouKu;
import com.pan.simplepicture.inter.ApiService;
import com.pan.simplepicture.model.impl.IPlayModel;
import com.pan.simplepicture.utils.SharedPreferencesUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PlayModel implements IPlayModel {

    @Override
    public void loadVideoUrl(Map<String, String> params, Callback<PlayUrl> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.URL_BEATY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<PlayUrl> call = service.repoBeautyVideoUrl(params.get("deviceModel"), params.get("plamformVersion"), params.get("deviceName"), params.get("plamform"), params.get("imieId"), params.get("link"), params.get("rsId"));
        call.enqueue(callback);
    }

    /**
     * 储存评论
     */
    public void saveComment(Map<String, String> params, SaveCallback callBack) {
        AVObject avComment = AVObject.create(params.get("class"));
        avComment.put("content", params.get("content"));
        avComment.put("rsId", params.get("rsId"));
        avComment.put("published", params.get("published"));
        avComment.put("location", params.get("location"));
        avComment.put("gender", params.get("gender"));
        avComment.put("screen_name", params.get("screen_name"));
        avComment.put("profile_image_url", params.get("profile_image_url"));
        avComment.saveInBackground(callBack);
    }

    @Override
    public void loadRealAddress(Map<String, String> params, Callback<String> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.FLVCD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<String> call = service.repoRealAddress(params.get("url"));
        call.enqueue(callback);
    }

    @Override
    public boolean isAutoPlay(Context mContext) {
        return SharedPreferencesUtils.getBoolean(mContext, "auto_play", false);
    }

    @Override
    public void loadBaozouUrl(Map<String, String> params, Callback<Episodes> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAOZOU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Episodes> call = service.repoBaozouVideoInfo(params.get("id"), params.get("version"));
        call.enqueue(callback);
    }

    @Override
    public void loadBaozouAddress(Map<String, String> params, Callback<PlayAddress> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAZOU_GET_REAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<PlayAddress> call = service.repoBaozouAddress(params.get("url"), params.get("format"));
        call.enqueue(callback);
    }


    @Override
    public void parseYoukuHtml(final Map<String, String> params, final com.pan.simplepicture.inter.Callback<YouKu> mCallback) {
        Observable.create(new Observable.OnSubscribe<YouKu>() {
                              @Override
                              public void call(Subscriber<? super YouKu> subscriber) {
                                  try {
                                      Document result = Jsoup.connect(params.get("url")).get();
                                      Elements es = result.getElementsByTag("a");
                                      List<Resources> list = new ArrayList<Resources>();
                                      YouKu youku = new YouKu();
                                      youku.data = list;
                                      for (Element e : es) {
                                          if (e.hasAttr("href") && e.hasAttr("_log_sid")
                                                  && e.hasAttr("_log_directpos")) {
                                              if (!params.get("_log_sid").equals(e.attr("_log_sid"))) {
                                                  continue;
                                              }
                                              if (!e.attr("href").startsWith("http://v.youku.com"))
                                                  continue;
                                              if (!"31".equals(e.attr("_log_directpos")))
                                                  continue;
                                              Resources res = new Resources();
                                              res.link = e.attr("href");
                                              res.title = e.attr("title");
                                              if (TextUtils.isEmpty(res.title)) {
                                                  res.title = e.parent().text();
                                              }
                                              res.rsId = "XMTQ0MjgwNDUzNg%3D%3D";
                                              list.add(res);
                                              continue;
                                          }
                                          if (e.hasAttr("href") && e.hasAttr("_log_sid")
                                                  && !e.hasAttr("_log_directpos")) {
                                              if (!params.get("_log_sid").equals(e.attr("_log_sid"))) {
                                                  continue;
                                              }
                                              if ("s_area".equals(e.parent().attr("class"))) {
                                                  youku.area = e.text();
                                                  try {
                                                      youku.update = e.parent().parent().getElementsByClass("updateNotice").get(0).text();
                                                  } catch (Exception ee) {
                                                      ee.printStackTrace();
                                                  }
                                              }
                                              if ("s_figure".equals(e.parent().attr("class"))) {
                                                  youku.addPeople(e.text());
                                              }
                                              if ("num".equals(e.parent().attr("class"))) {
                                                  youku.play_Count = e.text();
                                              }
                                          }
                                      }
                                      subscriber.onNext(youku);
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                      subscriber.onNext(null);
                                  }
                              }
                          }
        ).

                subscribeOn(Schedulers.io()
                ).
                observeOn(AndroidSchedulers.mainThread()

                ).

                subscribe(new Action1<YouKu>() {
                    @Override
                    public void call(YouKu youku) {
                        if (null == youku) {
                            mCallback.onFaild();
                        } else {
                            mCallback.onSccuss(youku);
                        }
                    }
                });
    }


}