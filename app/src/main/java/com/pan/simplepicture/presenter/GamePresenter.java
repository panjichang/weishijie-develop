package com.pan.simplepicture.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.pan.simplepicture.model.GameModel;
import com.pan.simplepicture.model.impl.IGameModel;
import com.pan.simplepicture.view.impl.IGameView;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class GamePresenter extends BasePresenter<IGameView> {
    private IGameModel mIGameModel;

    public GamePresenter() {
        mIGameModel = new GameModel();
    }

    public void getGames() {
        if(!mView.checkNet()){
            mView.showNoNet();
            return;
        }
        mIGameModel.loadGames(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (mView == null) return;
                if (e == null) {
                    if (list.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(list);
                        mView.showSuccess();
                    }
                } else {
                    mView.showFaild();
                }
            }
        });
    }
}
