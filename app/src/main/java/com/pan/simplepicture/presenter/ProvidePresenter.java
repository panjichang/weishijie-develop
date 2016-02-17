package com.pan.simplepicture.presenter;

import com.pan.simplepicture.bean.Material;
import com.pan.simplepicture.model.ProvideModel;
import com.pan.simplepicture.model.impl.IProvideModel;
import com.pan.simplepicture.view.impl.IProvideView;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class ProvidePresenter extends BasePresenter<IProvideView> {
    private IProvideModel mIProvideModel;

    public ProvidePresenter() {
        mIProvideModel = new ProvideModel();
    }

    public void provideMaterial() {
        if (mView.checkEmail())
            if (mView.checkTitle())
                if (mView.checkDes()) {
                    Material material = new Material();
                    material.des = mView.getmDes();
                    material.title = mView.getmTitle();
                    material.email = mView.getmEmail();
                    mIProvideModel.upProvide(material);
                }


    }
}
