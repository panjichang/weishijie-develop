package com.pan.simplepicture.model;

import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.impl.IArticleActModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleActModel implements IArticleActModel {
    @Override
    public void parserLZ13Content(final Map<String, String> params, final Callback<String> mCallback) {
        Observable.create(new Observable.OnSubscribe<String>() {
                              @Override
                              public void call(Subscriber<? super String> subscriber) {
                                  try {
                                      Document result = Jsoup.connect(params.get("url")).get();
                                      Element postContent = result.getElementsByClass("PostContent").get(0);
                                      Elements p = postContent.getElementsByTag("p");
                                      StringBuffer sb = new StringBuffer();
                                      for (Element e : p) {
                                          sb.append("<p>").append(e.text()).append("</p>");
                                      }
                                      subscriber.onNext(sb.toString());
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

                subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        if (null == string) {
                            mCallback.onFaild();
                        } else {
                            mCallback.onSccuss(string);
                        }
                    }
                });
    }
}
