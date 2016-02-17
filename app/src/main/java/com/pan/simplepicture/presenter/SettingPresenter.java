package com.pan.simplepicture.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.inter.Callback;
import com.pan.simplepicture.model.PicModel;
import com.pan.simplepicture.model.SettingModel;
import com.pan.simplepicture.model.impl.IPicModel;
import com.pan.simplepicture.model.impl.ISettingModel;
import com.pan.simplepicture.view.impl.IPictureView;
import com.pan.simplepicture.view.impl.ISettingView;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class SettingPresenter extends BasePresenter<ISettingView> {
    private ISettingModel mISettingModel;

    public SettingPresenter() {
        mISettingModel = new SettingModel();
    }

    public void sendFeedback(Map<String, String> params) {
        mISettingModel.sendFeedback(params, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (mView == null) return;
                if (e == null) {
                    mView.feedback(true);
                } else {
                    mView.feedback(false);
                }
            }
        });
    }

}
