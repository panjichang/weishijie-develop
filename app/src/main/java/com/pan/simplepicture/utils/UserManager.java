package com.pan.simplepicture.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pan.simplepicture.bean.User;

/**
 * Created by sysadminl on 2016/1/10.
 */
public class UserManager {
    private static UserManager mUserManager = null;
    private User user;

    private Context mContext;

    private UserManager() {
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        String json = SharedPreferencesUtils.getString(mContext, "user", "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            user = gson.fromJson(json, User.class);
        }
    }

    public void logout() {
        user = null;
        SharedPreferencesUtils.saveString(
                mContext, "user", "");
    }

    public boolean isLogin() {
        return user != null;
    }

    public static UserManager getInstance() {
        if (mUserManager == null) {
            synchronized (UserManager.class) {
                mUserManager = new UserManager();
            }
        }
        return mUserManager;
    }

    public void saveUser(User user) {
        this.user = user;
        Gson gson = new Gson();
        SharedPreferencesUtils.saveString(mContext, "user", gson.toJson(user));
    }

    public User getUser() {
        return user;
    }
}


