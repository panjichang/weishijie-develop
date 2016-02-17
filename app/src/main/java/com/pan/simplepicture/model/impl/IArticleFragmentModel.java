package com.pan.simplepicture.model.impl;

import com.pan.simplepicture.bean.Lz13;
import com.pan.simplepicture.inter.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public interface IArticleFragmentModel extends BaseModel{

    void parserLZ13(Map<String, String> params,Callback<List<Lz13>> mCallback);

}
