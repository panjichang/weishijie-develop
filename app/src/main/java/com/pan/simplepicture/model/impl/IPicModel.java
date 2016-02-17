package com.pan.simplepicture.model.impl;

import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.inter.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IPicModel extends BaseModel {

    void parseJuzimiHtml(Map<String, String> params,Callback<List<Juzimi>> mCallback);
}
