package com.pan.simplepicture.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.pan.simplepicture.ConstantValue;
import com.pan.simplepicture.bean.BaoZou;
import com.pan.simplepicture.bean.Beaty;
import com.pan.simplepicture.bean.Column;
import com.pan.simplepicture.bean.Videos;
import com.pan.simplepicture.inter.ApiService;
import com.pan.simplepicture.model.impl.IVideoModel;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class VideoModel implements IVideoModel {
    @Override
    public void loadATVideos(Map<String, String> params, Callback<Videos> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.AT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Videos> call = service.repoATVideos(params.get("api_key"), params.get("timestamp"), params.get("page"), params.get("access_token"));
        call.enqueue(callback);
    }

    @Override
    public void loadBeautifulVideos(Map<String, String> params, Callback<Beaty> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.URL_BEATY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Beaty> call = service.repoBeautifulVideos(params.get("pageNo"), params.get("pageSize"), params.get("deviceModel"), params.get("plamformVersion"), params.get("deviceName"), params.get("plamform"), params.get("imieId"));
        call.enqueue(callback);
    }

    @Override
    public void loadYouKuVideos(Map<String, String> params, FindCallback<AVObject> callback) {
        AVQuery<AVObject> query = AVQuery.getQuery("Video");
        query.whereContains("type", params.get("type"));
        query.setLimit(Integer.parseInt(params.get("pageSize")));
        int skip = Integer.parseInt(params.get("skip"));
        if (0 < skip) {
            query.setSkip(skip);
        }
        query.orderByDescending(params.get("orderBy"));
        query.findInBackground(callback);
    }

    @Override
    public void loadCinemaMovies(Map<String, String> params, Callback<BaoZou> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAOZOU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<BaoZou> call = service.repoCinemaMovies(params.get("client_id"), params.get("pagesize"), params.get("page"));
        call.enqueue(callback);
    }

    @Override
    public void loadMovies(Map<String, String> params, Callback<Column> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAOZOU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Column> call = service.repoMovies(params.get("type"), params.get("pagesize"), params.get("page"));
        call.enqueue(callback);
    }

}