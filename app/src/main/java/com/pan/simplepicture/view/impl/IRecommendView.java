package com.pan.simplepicture.view.impl;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IRecommendView extends IBaseView {
    void setAdapter(List<AVObject> list);

    void showSuccess();

    void showEmpty();

    void showFaild();

    void showNoNet();

    boolean checkNet();

}
