package com.pan.simplepicture.view.impl;

import com.pan.simplepicture.bean.Anime;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ICollectionView extends IBaseView {
    void setAdapter(List<Anime> list);
}
