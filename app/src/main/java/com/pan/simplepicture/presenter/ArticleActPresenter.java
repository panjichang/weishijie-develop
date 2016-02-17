package com.pan.simplepicture.presenter;

import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.ArticleActModel;
import com.pan.simplepicture.model.impl.IArticleActModel;
import com.pan.simplepicture.view.impl.IArticleActView;

import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleActPresenter extends BasePresenter<IArticleActView> {
    private IArticleActModel mIArticleModel;

    public ArticleActPresenter() {
        mIArticleModel = new ArticleActModel();
    }

    public void getArticleContent(final Map<String, String> params) {
        mIArticleModel.parserLZ13Content(params, new Callback<String>() {
            @Override
            public void onSccuss(String data) {
                if (mView == null) return;
                mView.setContent(data);
            }

            @Override
            public void onFaild() {
            }
        });
    }
}
