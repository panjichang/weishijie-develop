package com.pan.simplepicture.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class OverScrollWarpLayout extends LinearLayout {

    /**
     * OvershootInterpolator�ĵ���ϵ��
     */
    private static final float OVERSHOOT_TENSION = 0.75f;

    /**
     * ƽ��������
     */
    private Scroller mScroller;

    public OverScrollWarpLayout(Context context, AttributeSet attr) {
        super(context, attr);
        this.setOrientation(LinearLayout.VERTICAL);
        // ��ʼ��ƽ��������
        mScroller = new Scroller(getContext(), new OvershootInterpolator(OVERSHOOT_TENSION));
    }

    public OverScrollWarpLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        // ��ʼ��ƽ��������
        mScroller = new Scroller(getContext(), new OvershootInterpolator(OVERSHOOT_TENSION));
    }

    // ���ô˷���������Ŀ��λ��
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    // ���ô˷������ù��������ƫ��
    public void smoothScrollBy(int dx, int dy) {

        // ����mScroller�Ĺ���ƫ����
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        // ����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��
        invalidate();
    }

    @Override
    public void computeScroll() {

        // ���ж�mScroller�����Ƿ����
        if (mScroller.computeScrollOffset()) {

            // �������View��scrollTo()���ʵ�ʵĹ���
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            // ������ø÷���������һ���ܿ�������Ч��
            postInvalidate();
        }
        super.computeScroll();
    }

    public final void smoothScrollToNormal() {
        smoothScrollTo(0, 0);
    }

    public final int getScrollerCurrY() {
        return mScroller.getCurrY();
    }
}