package com.pan.simplepicture.widget.scrollview;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


/**
 * �������µ��Թ�����ScrollView<br><br>
 * <strong>����:</strong> ��ȡScrollView������ͼ����ӵ��Զ����{@link OverScrollWarpLayout}������ͼ��
 * ��������ͼ��ӵ�ScrollView��Ϊ����ͼ,���еĵ��Թ�������{@link OverScrollWarpLayout}�����
 *
 * @author King
 */
public class OverScrollView extends ScrollView {

	/**
	 * ����ϵ��, ��ͼ������������ָ��������ı�ֵ
	 */
	private static final float ELASTICITY_COEFFICIENT = 0.25f;

	/**
	 * �޵��Թ���״̬
	 */
	private static final int NO_OVERSCROLL_STATE = 0;
	
	/**
	 * �Ϸ����Թ���״̬
	 */
	private static final int TOP_OVERSCROLL_STATE = 1;

	/**
	 * �·����Թ���״̬
	 */
	private static final int BOTTOM_OVERSCROLL_STATE = 2;
	
	/**
	 * �������߶�,����˸߶Ȳ��ٹ���
	 */
	private static final int OVERSCROLL_MAX_HEIGHT = 1200;
	
	/**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;
	
    /**
     * �����¼��ĸ߶�Ĭ�Ϸ�ֵ
     */
    private static final int TRIGGER_HEIGHT = 120;
    
	/**
	 * ���Թ���״̬
	 */
	private int overScrollSate;
	
	/**
	 * ���Ա�־λ:�Ƿ���Ե��Թ���
	 */
	private boolean mIsUseOverScroll = true;
	
	/**
	 * �Ƿ��ǿ��Թ���
	 */
	private boolean isRecord;
	
	/**
	 * �Զ���ĵ��Թ�����ͼ
	 */
	private OverScrollWarpLayout mContentLayout;
	
	/**
	 * OverScroll������
	 */
	private OverScrollListener mOverScrollListener;
	
	/**
	 * OverScrollϸ�¼�����
	 */
	private OverScrollTinyListener mOverScrollTinyListener;
	
	/**
	 * Scrollϸ�¼�����
	 */
	private OnScrollListener mScrollListener;
	
	/**
	 * ����һ�ε���ָ����λ��
	 */
	private float mLastMotionY;
	
	/**
	 * ���Թ����ܾ���,����Ϊ��������Ϊ��
	 */
	private int overScrollDistance;
	
	/**
	 * ������Ļ�ϵ���ָ��id
	 */
	private int mActivePointerId = INVALID_POINTER;
	
	/**
	 * �Ƿ���
	 */
	private boolean isOnTouch;
	
	/**
	 * �Ƿ���й���
	 */
	private boolean isInertance;
	
	/**
	 * �Ƿ�ʹ�ù���
	 */
	private boolean mIsUseInertance = true;
	
	/**
	 * �Ƿ���ÿ��ٹ���
	 */
	private boolean mIsBanQuickScroll;
	
	/**
	 * ���Ծ���(�뻬�������й�)
	 */
	private int inertanceY;
	
	/**
	 * �����¼��ĸ߶ȷ�ֵ����СֵΪ30
	 */
	private int mOverScrollTrigger = TRIGGER_HEIGHT;
	
	public OverScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollView();
	}

	public OverScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OverScrollView(Context context) {
		this(context, null);
	}

	private void initScrollView(){
		//���ù�������Ӱ( API Level 9 )
		if(Build.VERSION.SDK_INT >= 9){
			setOverScrollMode(View.OVER_SCROLL_NEVER);
		}else{
			ViewCompat.setOverScrollMode(this, ViewCompat.OVER_SCROLL_NEVER);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// �����ã������κδ���
		if(!mIsUseOverScroll){
			return super.onInterceptTouchEvent(ev);
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (isOverScrolled()) {
					isRecord = true;
					// Remember where the motion event started
					mLastMotionY = (int) ev.getY();
					
					mActivePointerId = ev.getPointerId(0);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(isRecord && Math.abs(ev.getY() - mLastMotionY) > 20){
					return true;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				if(isRecord){
					isRecord = false;
				}
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		isOnTouch = true;
		if(ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL){
			if(mOverScrollTinyListener != null){
				mOverScrollTinyListener.scrollLoosen();
			}
			isOnTouch = false;
		}

		// �����ã������κδ���
		if(!mIsUseOverScroll){
			return super.onTouchEvent(ev);
		}
		
		if(!isOverScrolled()){
			mLastMotionY = (int) ev.getY();
			return super.onTouchEvent(ev);
		}
		
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mActivePointerId = ev.getPointerId(0);
			mLastMotionY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_POINTER_DOWN: 
            final int index = ev.getActionIndex();
            mLastMotionY = (int) ev.getY(index);
            mActivePointerId = ev.getPointerId(index);
            break;
        case MotionEvent.ACTION_POINTER_UP:
        	onSecondaryPointerUp(ev);
        	if(mActivePointerId != INVALID_POINTER){
        		mLastMotionY = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
        	}
            break;
		case MotionEvent.ACTION_MOVE:
			if (isRecord) {
				final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    break;
                }
				
                
				final float y = ev.getY(activePointerIndex);
				// ��������
				int deltaY = (int) (mLastMotionY - y);
				// ��¼�µĴ���λ��
				mLastMotionY = y;
				
				if(Math.abs(overScrollDistance) >= OVERSCROLL_MAX_HEIGHT && overScrollDistance * deltaY > 0){
					deltaY = 0;
				}
				
				//��������ScrollView��������߽磬ֱ�ӵ����������
				if(overScrollDistance *(overScrollDistance + deltaY) < 0){
					mContentLayout.smoothScrollToNormal();
					overScrollDistance = 0;
					break;
				}
				
				// �����ScrollView����״̬��ֱ�ӵ���ScrollView�������
				if((!isOnBottom() && overScrollDistance > 0) || (!isOnTop() && overScrollDistance < 0)){
					mContentLayout.smoothScrollToNormal();
					overScrollDistance = 0;
					break;
				}
				
				if(overScrollDistance * deltaY > 0){
					deltaY = (int) (deltaY * ELASTICITY_COEFFICIENT);
				}
				
				if(overScrollDistance == 0){
					deltaY = (int) (deltaY * ELASTICITY_COEFFICIENT * 0.5f);
				}
				
				if(overScrollDistance == 0 && deltaY == 0){
					break;
				}
				
				//������չ������룬���Ϊ20
				if(Math.abs(deltaY) > 20){
					deltaY = deltaY > 0 ? 20 : -20;
				}
				
				// ��¼�����ܾ���
				overScrollDistance += deltaY;
				
				if(isOnTop() && overScrollDistance > 0 && !isOnBottom()){
					overScrollDistance = 0;
					break;
				}
				
				if(isOnBottom() && overScrollDistance < 0 && !isOnTop()){
					overScrollDistance = 0;
					break;
				}
				
				// ������ͼ
				mContentLayout.smoothScrollBy(0, deltaY);
				
				if(mOverScrollTinyListener != null){
					mOverScrollTinyListener.scrollDistance(deltaY, overScrollDistance);
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mContentLayout.smoothScrollToNormal();
			overScrollTrigger();
			// ���û����ܾ���
			overScrollDistance = 0;
			// ���ñ��
			isRecord = false;
			// ������ָ����id
			mActivePointerId = INVALID_POINTER;
			break;
			
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * ��������: ��ֹ����pointerIndex out of range�쳣<br>
	 *
	 * @param ev
	 */
	private void onSecondaryPointerUp(MotionEvent ev) {
		final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		final int pointerId = ev.getPointerId(pointerIndex);
		if (pointerId == mActivePointerId) {
			// This was our active pointer going up. Choose a new
			// active pointer and adjust accordingly.
			// TODO: Make this decision more intelligent.
			final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			mLastMotionY = (int) ev.getY(newPointerIndex);
			mActivePointerId = ev.getPointerId(newPointerIndex);
		}
		
	}

	public boolean isOverScrolled() {
		return isOnTop() || isOnBottom();
	}
	
	private boolean isOnTop(){
		return getScrollY() == 0;
	}
	
	private boolean isOnBottom(){
		return getScrollY() + getHeight() == mContentLayout.getHeight();
	}
	
	/**
	 * ��������:��ʼ��������ͼ <br>
	 * <br>
	 * <strong>����:</strong> ��ȡScrollView������ͼ����ӵ��Զ����{@link OverScrollWarpLayout}������ͼ��
	 * ��������ͼ��ӵ�ScrollView��Ϊ����ͼ
	 */
	private void initOverScrollLayout() {
		//��������Ϊtrue,�����������ͼʱ�߶Ȳ�����䵽���ScrollView�ĸ߶�
		setFillViewport(true);
		if(mContentLayout == null){
			// ��ȡScrollView������ͼ
			View child = getChildAt(0);
			// ��ʼ�����Թ�����ͼ
			mContentLayout = new OverScrollWarpLayout(getContext());
			// �Ƴ�ScrollView������ͼ
			this.removeAllViews();
			// ��ԭ��ScrollView����ͼ���뵽���Թ�����ͼ��
			mContentLayout.addView(child);
			// ��ӵ��Թ�����ͼ����ΪScrollView����ͼ
			this.addView(mContentLayout,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		}
//		mIsUseOverScroll = true;
	}
	
	/**
	 * ��������:�����Ƿ���Ե��Թ��� <br>
	 *
	 * @param isOverScroll
	 */
	public void setOverScroll(boolean isOverScroll){
		mIsUseOverScroll = isOverScroll;
	}
	
	/**
	 * ��������: �����Ƿ�ʹ�ù���<br>
	 *
	 * @param isInertance
	 */
	public void setUseInertance(boolean isInertance){
		mIsUseInertance = isInertance;
	}
	
	@Override
	protected void onAttachedToWindow() {
		initOverScrollLayout();
		super.onAttachedToWindow();
	}
	
	/**
	 * ��������: ��ȡ����״̬<br>
	 *
	 * @return
	 */
	public int getScrollState(){
		invalidateState();
		return overScrollSate;
	}
	
	/**
	 * ��������: ˢ�µ��Թ���״̬<br>
	 */
	private void invalidateState(){
		
		if(mContentLayout.getScrollerCurrY() == 0){
			overScrollSate =  NO_OVERSCROLL_STATE;
		}
		
		if(mContentLayout.getScrollerCurrY() < 0){
			overScrollSate =  TOP_OVERSCROLL_STATE;
		}
		
		if(mContentLayout.getScrollerCurrY() > 0){
			overScrollSate =  BOTTOM_OVERSCROLL_STATE;
		}
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//		Log.v("test", "deltaY "+deltaY+"   scrollY "+scrollY);
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if(mScrollListener != null && overScrollDistance == 0){
			mScrollListener.onScroll(l, t, oldl, oldt);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		if(mIsUseInertance && !isInertance && scrollY != 0){
			isInertance = true;
		}
		if(clampedY && !isOnTouch && isInertance){
			mContentLayout.smoothScrollBy(0, inertanceY);
			mContentLayout.smoothScrollToNormal();
			inertanceY = 0;
		}
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}
	
	/**
	 * ��������: ����OverScroll����������<br>
	 *
	 * @param listener
	 */
	public void setOverScrollListener(OverScrollListener listener){
		mOverScrollListener = listener;
	}
	
	/**
	 * ��������: ����OverScroll����������<br>
	 *
	 * @param listener
	 */
	public void setOverScrollTinyListener(OverScrollTinyListener listener){
		mOverScrollTinyListener = listener;
	}
	
	/**
	 * ��������: ����Scroll����������<br>
	 *
	 * @param listener
	 */
	public void setOnScrollListener(OnScrollListener listener){
		mScrollListener = listener;
	}
	
	/**
	 * ����OverScrollListener������ֵ
	 * @param height
	 */
	public void setOverScrollTrigger(int height){
		if(height >= 30){
			mOverScrollTrigger = height;
		}
	}
	
	private void overScrollTrigger(){
		if(mOverScrollListener == null){
			return;
		}
		
		if(overScrollDistance > mOverScrollTrigger && isOnBottom()){
			mOverScrollListener.footerScroll();
		}
		
		if(overScrollDistance < -mOverScrollTrigger && isOnTop()){
			mOverScrollListener.headerScroll();
		}
		
	}
	
	public void setQuickScroll(boolean isEnable){
		mIsBanQuickScroll = !isEnable;
	}
	
	@Override
	public void computeScroll() {
		if(!mIsBanQuickScroll){
			super.computeScroll();
		}
	}
	
	/**
	 * ��ȡScrollView�ɹ����߶�
	 * @return
	 */
	public int getScrollHeight(){
		return mContentLayout.getHeight() - getHeight();
	}
	
	@Override
	public void fling(int velocityY) {
		inertanceY = 50 * velocityY / 5000;
		super.fling(velocityY);
	}
	
	
	/**
	 * ��OverScroll����һ��ֵʱ�����ô˼���
	 * 
	 * @author King
	 * @since 2014-4-9 ����4:36:29
	 */
	public interface OverScrollListener {

		/**
		 * ����
		 */
		void headerScroll();
		
		/**
		 * �ײ�
		 */
		void footerScroll();
		
	}
	
	/**
	 * ÿ��OverScrollʱ�����ܴ����ļ���
	 * @author King
	 * @since 2014-4-9 ����4:39:06
	 */
	public interface OverScrollTinyListener{
		
		/**
		 * ��������
		 * @param tinyDistance ��ǰ������ϸС����
		 * @param totalDistance �������ܾ���
		 */
		void scrollDistance(int tinyDistance, int totalDistance);

        /**
         * �����ɿ�
         */
        void scrollLoosen();
	}
	
	/**
	 * ��ͨ����������<br>
	 * overScroll����Ϊ0���޹���ʱ����
	 * 
	 * @author king
	 *
	 */
	public interface OnScrollListener{
		void onScroll(int l, int t, int oldl, int oldt);
	}
}
