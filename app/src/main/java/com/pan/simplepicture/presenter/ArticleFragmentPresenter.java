package com.pan.simplepicture.presenter;

import com.pan.simplepicture.bean.Lz13;
import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.ArticleFragmentModel;
import com.pan.simplepicture.model.impl.IArticleFragmentModel;
import com.pan.simplepicture.view.impl.IArticleFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleFragmentPresenter extends BasePresenter<IArticleFragmentView> {
    private IArticleFragmentModel mIArticleModel;

    public ArticleFragmentPresenter() {
        mIArticleModel = new ArticleFragmentModel();
    }

    public void getArticles(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIArticleModel.parserLZ13(params, new Callback<List<Lz13>>() {
            @Override
            public void onSccuss(List<Lz13> data) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if (data.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(data);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(data);
                }

            }

            @Override
            public void onFaild() {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))){
                    mView.showFaild();
                }
            }
        });
    }
}
