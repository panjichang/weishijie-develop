package com.pan.simplepicture.view.impl;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ICommentView extends IBaseView {

    void setAdapter(List<AVObject> list);

    void loadMore(List<AVObject> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
