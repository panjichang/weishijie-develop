package com.pan.simplepicture.view.holder;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.pan.simplepicture.R;
import com.pan.simplepicture.bean.Juzimi;
import com.pan.simplepicture.utils.ContextUtils;
import com.pan.simplepicture.utils.FrecsoUtils;
import com.pan.simplepicture.utils.ScreenUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class PictureHolder extends BaseHolder<Juzimi> {
    public PictureHolder(View view) {
        super(view);
    }

    @Bind(R.id.sd_juzimi)
    public SimpleDraweeView sd_juzimi;
    @Bind(R.id.tv_content)
    public TextView tv_content;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });
    }

    public void showImage(SimpleDraweeView iv_pic, SimpleDraweeView moveView, final ViewGroup container, final View fl) {
        if (iv_pic == null || moveView == null) return;
        if (iv_pic.getTag() != null && moveView.getTag() != null && (boolean) iv_pic.getTag() && (boolean) moveView.getTag()) {
            //X轴平移
            ObjectAnimator translationX = ObjectAnimator.ofFloat(moveView, "translationX", 0, (ScreenUtils.getInstance(mContext).getWidth() / 2 - (mData.x + mData.width / 2)));
            //Y轴平移
            ObjectAnimator translationY = ObjectAnimator.ofFloat(moveView, "translationY", 0, (ScreenUtils.getInstance(mContext).getHeight() / 2 - (mData.y + mData.height / 2)));
            //X轴缩放
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(moveView, "scaleX", 1.0f, ScreenUtils.getInstance(mContext).getWidth() * 1.0f / mData.width);
            //Y轴缩放
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(moveView, "scaleY", 1.0f, ScreenUtils.getInstance(mContext).getWidth() * 0.8f / mData.height);

            AnimatorSet set = new AnimatorSet();
            set.setDuration(300);
            set.setInterpolator(new LinearInterpolator());
            set.playTogether(translationX, translationY, scaleX, scaleY);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    container.setVisibility(View.INVISIBLE);
                    container.removeAllViews();
                    fl.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        }
    }

    private void onMeasure() {
        int[] loacation = new int[2];
        sd_juzimi.getLocationOnScreen(loacation);
        mData.x = loacation[0];
        mData.y = loacation[1];
        mData.width = sd_juzimi.getMeasuredWidth();
        mData.height = sd_juzimi.getMeasuredHeight();
    }

    private void hidePop() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    public void showPop() {
        onMeasure();
        View view = ContextUtils.inflate(mContext, R.layout.pop_picture);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePop();
            }
        });
        final SimpleDraweeView iv_pic = ButterKnife.findById(view, R.id.iv_pic);
        final FrameLayout fl = ButterKnife.findById(view, R.id.fl);
        final TextView tv_content = ButterKnife.findById(view, R.id.tv_content);
        final TextView tv_sender = ButterKnife.findById(view, R.id.tv_sender);
        if (TextUtils.isEmpty(mData.content)) {
            tv_content.setVisibility(View.INVISIBLE);
            tv_sender.setVisibility(View.INVISIBLE);
        } else {
            tv_content.setText(mData.content);
            tv_sender.setText("——" + mData.sender);
        }
        final SimpleDraweeView moveView = new SimpleDraweeView(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mData.width, mData.height);
        moveView.setLayoutParams(layoutParams);
        layoutParams.setMargins(mData.x, mData.y, ScreenUtils.getInstance(mContext).getWidth() - (mData.x + mData.width), ScreenUtils.getInstance(mContext).getHeight() - (mData.y + mData.height));
        final RelativeLayout rl_container = ButterKnife.findById(view, R.id.rl_container);
        rl_container.addView(moveView);
        FrecsoUtils.loadImage(mData.url, iv_pic, new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                iv_pic.setTag(true);
                mData.maxWidth = imageInfo.getWidth();
                mData.maxHeight = imageInfo.getHeight();
                showImage(iv_pic, moveView, rl_container, fl);
            }
        });
        FrecsoUtils.loadImage(mData.url, moveView, new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                moveView.setTag(true);
                showImage(iv_pic, moveView, rl_container, fl);
            }
        });
        pop = new PopupWindow(view, ScreenUtils.getInstance(mContext).getWidth(), ScreenUtils.getInstance(mContext).getHeight());
        pop.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.black)));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY,
                0, 0);
    }

    private PopupWindow pop;

    @Override
    public void setData(Juzimi mData) {
        super.setData(mData);
        if (TextUtils.isEmpty(mData.content)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setText(mData.content);
        }
        FrecsoUtils.loadImage(mData.url, sd_juzimi);
    }
}
