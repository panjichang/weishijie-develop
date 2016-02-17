package com.pan.simplepicture.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.pan.simplepicture.model.impl.IGameModel;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class GameModel implements IGameModel {
    @Override
    public void loadGames(FindCallback callback) {
        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Game");
        avQuery.whereEqualTo("type",1);
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(callback);
    }
}