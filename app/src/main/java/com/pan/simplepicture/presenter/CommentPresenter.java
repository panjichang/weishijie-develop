package com.pan.simplepicture.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.model.CommentModel;
import com.pan.simplepicture.model.impl.ICommentModel;
import com.pan.simplepicture.view.impl.ICommentView;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class CommentPresenter extends BasePresenter<ICommentView> {
    private ICommentModel mICommentModel;

    public CommentPresenter() {
        mICommentModel = new CommentModel();
    }

    public void getComments(final Map<String, String> params) {
        if(!mView.checkNet()){
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mICommentModel.loadComment(params, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if (e == null && list != null) {
                    if ("0".equals(params.get("pageNo"))) {
                        mView.setAdapter(list);
                        if (list.size() == 0) {
                            mView.showEmpty();
                        } else {
                            mView.showSuccess();
                        }
                    } else {
                        mView.loadMore(list);
                    }
                }else{
                    if ("0".equals(params.get("pageNo"))) {
                        mView.showFaild();
                    }
                }
            }
        });
    }
}
