package com.pan.simplepicture.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * Created by sysadminl on 2015/12/21.
 */
public class FrecsoUtils {

    public static void loadImage(String url, SimpleDraweeView imageView, ControllerListener listener) {
        if (TextUtils.isEmpty(url)) {
            url = "http://";
        }
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                . // other setters
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .setControllerListener(listener)
//                . // other setters
                .build();
        imageView.setController(controller);
    }

    public static void loadImage(String url, SimpleDraweeView imageView) {
        if (TextUtils.isEmpty(url)) {
            url = "http://";
        }
        Uri uri = Uri.parse(url);
        imageView.setImageURI(uri);
    }


    /**
     * 获取适用于Fresco本地图片资源Url
     */
    public static String getFrescoLocalResUrl(Context mContext, int resId) {
        return "res://" + mContext.getPackageName() + "/" + resId;
    }

    /**
     * 获取适用于Fresco本地文件图片资源
     */
    public static String getFrescoLocalFile(File file) {
        return "file://" + file.getPath();
    }


    public static void clear() {
        ImagePipeline mImagePipeline = Fresco.getImagePipeline();
        mImagePipeline.clearMemoryCaches();
        mImagePipeline.clearDiskCaches();
        mImagePipeline.clearCaches();
    }
}
