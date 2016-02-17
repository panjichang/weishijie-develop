package com.pan.simplepicture.presenter;

import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.PicModel;
import com.pan.simplepicture.model.impl.IPicModel;
import com.pan.simplepicture.view.impl.IPictureView;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PicturePresenter extends BasePresenter<IPictureView> {
    private IPicModel mIPicModel;

    public PicturePresenter() {
        mIPicModel = new PicModel();
    }


    public void getJuzimiPictrues(final Map<String, String> params) {
        if(!mView.checkNet()){
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIPicModel.parseJuzimiHtml(params, new Callback<List<Juzimi>>() {
            @Override
            public void onSccuss(List<Juzimi> data) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if(data.size()==0){
                        mView.showEmpty();
                    }else {
                        mView.setAdapter(data);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(data);
                }
            }

            @Override
            public void onFaild() {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                  mView.showFaild();
                }
            }
        });
    }
}
