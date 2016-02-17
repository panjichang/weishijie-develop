package com.pan.simplepicture.inter;

import com.pan.simplepicture.bean.BaoZou;
import com.pan.simplepicture.bean.Beaty;
import com.pan.simplepicture.bean.Column;
import com.pan.simplepicture.bean.Episodes;
import com.pan.simplepicture.bean.PlayAddress;
import com.pan.simplepicture.bean.PlayUrl;
import com.pan.simplepicture.bean.Videos;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sysadminl on 2015/12/18.
 */
public interface ApiService {
    /**
     * 加载AT资源
     *
     * @param api_key
     * @param timestamp
     * @param page
     * @param access_token
     * @return
     */
    @GET("animelist_v4")
    Call<Videos> repoATVideos(
            @Query("api_key") String api_key,
            @Query("timestamp") String timestamp,
            @Query("page") String page,
            @Query("access_token") String access_token);

    /**
     * 加载最美创意资源
     *
     * @param pageNo
     * @param pageSize
     * @param deviceModel
     * @param plamformVersion
     * @param deviceName
     * @param plamform
     * @param imieId
     * @return
     */
    @GET("resources/getResources")
    Call<Beaty> repoBeautifulVideos(
            @Query("pageNo") String pageNo,
            @Query("pageSize") String pageSize,
            @Query("deviceModel") String deviceModel,
            @Query("plamformVersion") String plamformVersion,
            @Query("deviceName") String deviceName,
            @Query("plamform") String plamform,
            @Query("imieId") String imieId);

    /**
     * @param deviceModel
     * @param plamformVersion
     * @param deviceName
     * @param plamform
     * @param imieId
     * @param link
     * @param rsId
     * @return
     */
    @GET("resources/getPlayAdressByIdAndLink")
    Call<PlayUrl> repoBeautyVideoUrl(
            @Query("deviceModel") String deviceModel,
            @Query("plamformVersion") String plamformVersion,
            @Query("deviceName") String deviceName,
            @Query("plamform") String plamform,
            @Query("imieId") String imieId,
            @Query("link") String link,
            @Query("rsId") String rsId);

    /**
     * 获取影院电影
     *
     * @param client_id
     * @param pageSize
     * @param deviceModel
     * @return
     */
    @GET("columns/1/series_list.json")
    Call<BaoZou> repoCinemaMovies(
            @Query("client_id") String client_id,
            @Query("pagesize") String pageSize,
            @Query("page") String deviceModel);

    /**
     * 获取全部电影
     *
     * @param type        1为推荐电影  2为排行榜  3为全部
     * @param pageSize
     * @param deviceModel
     * @return
     */
    @GET("categories/16/series.json")
    Call<Column> repoMovies(
            @Query("type") String type,
            @Query("pagesize") String pageSize,
            @Query("page") String deviceModel);

    @GET("series/{id}/episodes.json")
    Call<Episodes> repoBaozouVideoInfo(
            @Path("id") String id,
            @Query("version") String version);

    @GET("rage_ios.php")
    Call<PlayAddress> repoBaozouAddress(@Query("url") String url,
                                        @Query("format") String format);

    @GET("parse_m3u8.php")
    Call<String> repoRealAddress(@Query("url") String url);
}
