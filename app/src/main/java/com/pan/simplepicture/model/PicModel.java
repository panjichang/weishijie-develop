package com.pan.simplepicture.model;

import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.impl.IPicModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PicModel implements IPicModel {


    @Override
    public void parseJuzimiHtml(final Map<String, String> params, final Callback<List<Juzimi>> mCallback) {
        Observable.create(new Observable.OnSubscribe<List<Juzimi>>() {
            @Override
            public void call(Subscriber<? super List<Juzimi>> subscriber) {
                try {
                    Document result = Jsoup.connect(params.get("url") + "?page=" + params.get("page")).get();
                    Elements elements = result.select("div[class^=views-row views-row]");
                    List<Juzimi> list = new ArrayList<Juzimi>();
                    for (Element e : elements) {
                        Juzimi mJuzimi = new Juzimi();
                        Elements chromeimg = e.getElementsByClass("chromeimg");
                        if (chromeimg == null || chromeimg.size() == 0) continue;
                        mJuzimi.url = chromeimg.get(0).attr("src");
                        Elements xlistjus = e.getElementsByClass("xlistju");
                        if (xlistjus != null && xlistjus.size() > 0) {
                            mJuzimi.content = xlistjus.get(0).text();
                        }
                        Elements xqusernpops = e.getElementsByClass("xqusernpop");
                        if (xqusernpops != null && xqusernpops.size() > 0) {
                            mJuzimi.sender = xqusernpops.get(0).text();
                        }
                        list.add(mJuzimi);
                    }
                    subscriber.onNext(list);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onNext(null);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Juzimi>>() {
            @Override
            public void call(List<Juzimi> list) {
                if (null == list) {
                    mCallback.onFaild();
                } else {
                    mCallback.onSccuss(list);
                }
            }
        });
    }
}