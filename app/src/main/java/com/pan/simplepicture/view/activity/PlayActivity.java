package com.pan.simplepicture.view.activity;


import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnPlayingBufferCacheListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pan.simplepicture.ConstantValue;
import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.PlayFragmentAdapter;
import com.pan.simplepicture.bean.YouKu;
import com.pan.simplepicture.inter.AbsVideoRes;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.presenter.PlayPresenter;
import com.pan.simplepicture.utils.AppUtils;
import com.pan.simplepicture.utils.ContextUtils;
import com.pan.simplepicture.utils.FrecsoUtils;
import com.pan.simplepicture.utils.MD5Utils;
import com.pan.simplepicture.utils.ScreenUtils;
import com.pan.simplepicture.utils.SharedPreferencesUtils;
import com.pan.simplepicture.utils.UserManager;
import com.pan.simplepicture.view.fragment.CommentFragment;
import com.pan.simplepicture.view.fragment.VideoDesFragment;
import com.pan.simplepicture.view.fragment.VideoListFragment;
import com.pan.simplepicture.view.impl.IPlayView;
import com.pan.simplepicture.widget.AlertDialog;
import com.pan.simplepicture.widget.CustomDialog;
import com.pan.simplepicture.widget.shimmer.Shimmer;
import com.pan.simplepicture.widget.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 播放页
 *
 * @author pan
 */
public class PlayActivity extends BaseActivity implements OnPreparedListener,
        OnCompletionListener, OnErrorListener, OnInfoListener,
        OnPlayingBufferCacheListener, IPlayView {
    private final String TAG = "VideoViewPlayingActivity";
    private AbsVideoRes mVideoRes;


    @Override
    public void getIntentValue() {
        super.getIntentValue();
        mVideoRes = (AbsVideoRes) getIntent()
                .getParcelableExtra("video");
    }

    private ActionBar mActionBar;

    @Override
    public void setActionBar() {
        //set the back arrow in the toolbar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return new PlayPresenter();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_play;
    }

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.mViewPager)
    ViewPager mViewPager;

    @Bind(R.id.layout_play)
    RelativeLayout layout_play;
    @Bind(R.id.rl_container)
    RelativeLayout rl_container;
    @Bind(R.id.video_view)
    BVideoView mVV;
    @Bind(R.id.controlbar)
    RelativeLayout mController;
    @Bind(R.id.play_btn)
    ImageButton mPlaybtn;
    @Bind(R.id.time_current)
    TextView mCurrPostion;
    @Bind(R.id.media_progress)
    SeekBar mProgress;
    @Bind(R.id.time_total)
    TextView mDuration;
    @Bind(R.id.zoom_btn)
    Button zoom_btn;
    @Bind(R.id.VideoAction)
    RelativeLayout VideoAction;
    @Bind(R.id.detailPic)
    SimpleDraweeView detailPic;
    @Bind(R.id.pre_play_button)
    ImageButton pre_play_button;
    @Bind(R.id.shimmer_tv)
    ShimmerTextView shimmer_tv;

    @Bind(R.id.layout_info)
    RelativeLayout layout_info;

    @Bind(R.id.small_pic)
    SimpleDraweeView small_pic;

    @Bind(R.id.tv_info_title)
    TextView tv_info_title;
    @Bind(R.id.tv_info_update)
    TextView tv_info_update;
    @Bind(R.id.tv_info_area)
    TextView tv_info_area;

    @Bind(R.id.tv_info_main)
    TextView tv_info_main;
    @Bind(R.id.tv_info_count)
    TextView tv_info_count;
    @Bind(R.id.tv_info_des)
    TextView tv_info_des;

    public void showHeader(int i) {
        if (0 == i) {
            layout_play.setVisibility(View.VISIBLE);
            layout_info.setVisibility(View.GONE);
        } else {
            layout_play.setVisibility(View.GONE);
            layout_info.setVisibility(View.VISIBLE);
        }
    }


    public void setInfo(YouKu youku) {
        tv_info_title.setText(youku.title = mVideoRes.getVideoTitle());
        tv_info_update.setText(youku.update);
        tv_info_area.setText("地区:" + youku.area);
        if (TextUtils.isEmpty(youku.play_Count) || "null".equals(youku.play_Count)) {
            tv_info_count.setVisibility(View.GONE);
        } else {
            tv_info_count.setText(youku.play_Count + "万次播放");
        }
        tv_info_des.setText(youku.des = mVideoRes.getVideoDes());
        if (TextUtils.isEmpty(youku.mainPeople)) {
            tv_info_main.setVisibility(View.GONE);
        } else {
            tv_info_main.setText("主持人:" + youku.mainPeople);
        }
        FrecsoUtils.loadImage(mVideoRes.getSmallVideoThumbnail(), small_pic);
        setupViewPager(youku.data);
    }

    public void setupViewPager(List<? extends AbsVideoRes> data) {
        String[] titles = getResources().getStringArray(R.array.play_tab);
        titles[0] = titles[0] + "(" + data.size() + ")";
        List<Fragment> list = new ArrayList<Fragment>();
        VideoListFragment fragment = new VideoListFragment();
        fragment.setRes(data);
        list.add(fragment);
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setResId(mVideoRes.getVideoId());
        list.add(commentFragment);
        PlayFragmentAdapter adapter =
                new PlayFragmentAdapter(getSupportFragmentManager(), Arrays.asList(titles), list);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void setupDesViewPager(AbsVideoRes data) {
        String[] titles = getResources().getStringArray(R.array.play_tab);
        titles[0] = "简介";
        List<Fragment> list = new ArrayList<Fragment>();
        VideoDesFragment fragment = new VideoDesFragment();
        fragment.setRes(data);
        list.add(fragment);
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setResId(mVideoRes.getVideoId());
        list.add(commentFragment);
        PlayFragmentAdapter adapter =
                new PlayFragmentAdapter(getSupportFragmentManager(), Arrays.asList(titles), list);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    public boolean getFlag() {
        return flag;
    }

    private boolean flag;

    public void onEventMainThread(AbsVideoRes res) {
        flag = true;
      /*  if (res.getType() == 2) {
            setVideoSource(res.getUrl());
            sendPlayMessage(true);
        } else*/ if (res.getType() == 2||res.getType() == 100) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("url", res.getUrl());
            params.put("format", "normal");
            ((PlayPresenter) mPresenter).getBaozouRealUrl(this, params);
        } else {
            Map<String, String> params = new HashMap<String, String>();
         /*   params.put("deviceModel", Build.MODEL.replace(" ", "+"));
            params.put("plamformVersion", Build.VERSION.RELEASE);
            params.put("deviceName", Build.MANUFACTURER);
            params.put("plamform", "Android");
            params.put("link", res.getUrl());
            params.put("rsId", res.getVideoId());
            params.put("imieId", MD5Utils.MD5(ConstantValue.str + ConstantValue.str));
            ((PlayPresenter) mPresenter).getVideoUrl(this, params);*/
            params.put("url", res.getUrl());
            ((PlayPresenter) mPresenter).getRealAddress(this, params);
            showHeader(0);
        }
    }


    @Override
    public void bindView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initPlay();
        if (mPresenter == null && !(mPresenter instanceof PlayPresenter))
            return;
        FrecsoUtils.loadImage(mVideoRes.getVideoThumbnail(), detailPic);
        if (0 == mVideoRes.getType()) {
            setupDesViewPager(mVideoRes);
            setVideoSource(mVideoRes.getUrl());
            ((PlayPresenter) mPresenter).play(this);
            return;
        } else if (1 == mVideoRes.getType()) {
            setupDesViewPager(mVideoRes);
            Map<String, String> params = new HashMap<String, String>();
            params.put("deviceModel", Build.MODEL.replace(" ", "+"));
            params.put("plamformVersion", Build.VERSION.RELEASE);
            params.put("deviceName", Build.MANUFACTURER);
            params.put("plamform", "Android");
            params.put("link", mVideoRes.getUrl());
            params.put("rsId", mVideoRes.getVideoId());
            params.put("imieId", MD5Utils.MD5(ConstantValue.str + ConstantValue.str));
            ((PlayPresenter) mPresenter).getVideoUrl(this, params);
            return;
        } else if (2 == mVideoRes.getType() || 100 == mVideoRes.getType()) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", mVideoRes.getVideoId());
            params.put("version", "v2");
            params.put("type", String.valueOf(mVideoRes.getType()));
            ((PlayPresenter) mPresenter).getBaozouVideoInfo(this, params);
        } else if (3 == mVideoRes.getType()) {
            showHeader(1);
            Map<String, String> params = new HashMap<String, String>();
            params.put("url", mVideoRes.getUrl());
            params.put("_log_sid", mVideoRes.getVideoId());
            ((PlayPresenter) mPresenter).getYoukuVideoInfo(params);
        }
        pre_play_button.setVisibility(View.GONE);
    }

    @Override
    public void setVideoSource(String mVideoSource) {
        this.mVideoSource = mVideoSource;
    }


    /***************************************** 百度视频sdk ************************************************************/

    /**
     * 您的ak
     */
    private String AK = "t207G26112s0cbhYjtDuxBP7";
    /**
     * //您的sk的前16位
     */
    private String SK = "9s4Eof8Os1zvEht4ybem6U64Sw0R3Xtc";

    private String mVideoSource = null;


    /**
     * 记录播放位置
     */
    private int mLastPos = 0;

    /**
     * 播放状态
     */
    private enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.comment:
                //   shareVideo();
                if (!UserManager.getInstance().isLogin()) {
                    Toast.makeText(this, "please login first in Home-Page !", Toast.LENGTH_LONG).show();
                    return super.onOptionsItemSelected(item);
                }
                showCommentDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCommentDialog() {
        View view = ContextUtils.inflate(this, R.layout.send_comment_dialog);
        final EditText et_content = ButterKnife.findById(view, R.id.et_content);
        final TextView tv_length = ButterKnife.findById(view, R.id.tv_length);
        final Button comment_button = ButterKnife.findById(view, R.id.comment_button);
        comment_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(PlayActivity.this, "please input your comment first !", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                object = new AVObject("Comment");
                params.put("class", "Comment");
                params.put("content", content);
                object.put("content", content);
                params.put("rsId", mVideoRes.getVideoId());
                object.put("rsId", mVideoRes.getVideoId());
                String time = String.valueOf(System.currentTimeMillis());
                params.put("published", time);
                object.put("published", time);
                params.put("location", UserManager.getInstance().getUser().location);
                object.put("location", UserManager.getInstance().getUser().location);
                params.put("gender", UserManager.getInstance().getUser().gender);
                object.put("gender", UserManager.getInstance().getUser().gender);
                params.put("screen_name", UserManager.getInstance().getUser().screen_name);
                object.put("screen_name", UserManager.getInstance().getUser().screen_name);
                params.put("profile_image_url", UserManager.getInstance().getUser().profile_image_url);
                object.put("profile_image_url", UserManager.getInstance().getUser().profile_image_url);
                ((PlayPresenter) mPresenter).sendComment(params);
            }
        });
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null) {
                    tv_length.setText("0/70");
                } else {
                    tv_length.setText(charSequence.toString().length() + "/70");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog = new CustomDialog(this, view, ScreenUtils.getInstance(this).getWidth() - ContextUtils.dip2px(this, 20), ContextUtils.dip2px(this, 280), Gravity.CENTER);
        dialog.show();
    }

    private AVObject object;

    public void showCommentSuccess() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
            if (object != null) {
                EventBus.getDefault().post(object);
                object = null;
            }
        }
    }

    private CustomDialog dialog;

    private boolean isSharePause = false;


    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;


    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;

    private final Object SYNC_Playing = new Object();

    private WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "VideoViewPlayingActivity";
    private static final int UI_CONTROLLER_INVISIBLE = 0;

    private final int EVENT_PLAY = 0;
    private final int UI_EVENT_UPDATE_CURRPOSITION = 1;

    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PLAY:
                    /**
                     * 如果已经播放了，等待上一次播放结束
                     */
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                                Log.v(TAG, "wait player status to idle");
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    /**
                     * 设置播放url
                     */
                    mVV.setVideoPath(mVideoSource);

                    /**
                     * 续播，如果需要如此
                     */
                    if (mLastPos > 0) {

                        mVV.seekTo(mLastPos);
                        mLastPos = 0;
                    }

                    /**
                     * 显示或者隐藏缓冲提示
                     */
                    mVV.showCacheInfo(true);

                    /**
                     * 开始播放
                     */
                    mVV.start();
                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                default:
                    break;
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onHandleMessage(msg);
        }
    };

    public void onHandleMessage(Message msg) {
        switch (msg.what) {
            /**
             * 更新进度及时间
             */
            case UI_EVENT_UPDATE_CURRPOSITION:
                if (mVV.isPlaying()) {
                    int currPosition = mVV.getCurrentPosition();
                    int duration = mVV.getDuration();
                    updateTextViewWithTimeFormat(mCurrPostion, currPosition);
                    updateTextViewWithTimeFormat(mDuration, duration);
                    mProgress.setMax(duration);
                    mProgress.setProgress(currPosition);
                }
                mHandler.sendEmptyMessageDelayed(UI_EVENT_UPDATE_CURRPOSITION, 500);
                break;
            /**
             * 隐藏控制UI
             */
            case UI_CONTROLLER_INVISIBLE:
                if (mVV.isPlaying()) {
                    updateControlBar(false);
                }
                break;
            default:
                break;
        }
    }

    protected void initPlay() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);

        initUI();

        /**
         * 开启后台事件处理线程
         */
        mHandlerThread = new HandlerThread("event handler thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());
    }

    @Override
    public void sendPlayMessage(boolean auto_Play) {
        if (!auto_Play) {
            return;
        }
        int flag = AppUtils.isWifi(this);
        boolean b = SharedPreferencesUtils.getBoolean(this, "prompt__not_wifi",
                true);
        if (flag == 2 && b) {
            new AlertDialog(this).builder().setTitle("温馨提示")
                    .setMsg("当前播放使用的是非wifi环境(如果不想再提示,可以进入设置页面设置)，确定继续？")
                    .setPositiveButton("继续", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            play();
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();
            return;
        }
        play();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isReplay;

    private void play() {
        if (mVV.isPlaying() || mPlayerStatus == PLAYER_STATUS.PLAYER_PREPARING) {
            isReplay = true;
            release();
            return;
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                pre_play_button.setVisibility(View.GONE);
                VideoAction.setVisibility(View.GONE);
                mPlaybtn.setBackgroundResource(R.drawable.pause_btn_style);
                shimmer.cancel();
            }
        });
        mEventHandler.sendEmptyMessage(EVENT_PLAY);
    }

    // 我的收藏,缓存,意见反馈,检测新版本,评论
    private boolean isFullScreen = false;

    /**
     * 初始化界面
     */
    private void initUI() {
        shimmer = new Shimmer();
        shimmer.start(shimmer_tv);
        registerCallbackForControl();

        /**
         * 设置ak及sk的前16位
         */
        BVideoView.setAKSK(AK, SK);

        /**
         * 获取BVideoView对象
         */
        mVV.requestFocus();
        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnInfoListener(this);
        mVV.setVideoScalingMode(1);
        /**
         * 设置解码模式
         */
        mVV.setDecodeMode(BVideoView.DECODE_SW);
    }

    private class PlayOnclickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (mVV.isPlaying()) {
                mPlaybtn.setBackgroundResource(R.drawable.play_btn_style);
                /**
                 * 暂停播放
                 */
                videoPause();
            } else {
                mPlaybtn.setBackgroundResource(R.drawable.pause_btn_style);
                /**
                 * 继续播放
                 */
                videoResume();
            }
        }
    }

    private void changeScreen() {
        if (isFullScreen) {
            setMinScreen();
        } else {
            setFullScreen();
        }
    }

    // 切换为全屏
    private void setFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setStatusBarTintRes(android.R.color.transparent);
        getWindow().addFlags(1024);
        mActionBar.hide();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight());
        mVV.setLayoutParams(layoutParams);
        mController.setLayoutParams(layoutParams);
        VideoAction.setLayoutParams(layoutParams);
        zoom_btn.setBackgroundResource(R.drawable.screensize_zoomin_button);
        rl_container.setFocusable(true);
        rl_container.setFocusableInTouchMode(true);
        rl_container.requestFocus();
        isFullScreen = true;
    }

    // 切换为小屏幕
    private void setMinScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBarTintRes(R.color.material_drawer_primary);
        getWindow().clearFlags(1024);
        mActionBar.show();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                getWindowManager().getDefaultDisplay().getWidth(),
                getResources().getDimensionPixelSize(R.dimen.video_play_heigh));
        mVV.setLayoutParams(layoutParams);
        mController.setLayoutParams(layoutParams);
        VideoAction.setLayoutParams(layoutParams);
        zoom_btn.setBackgroundResource(R.drawable.screensize_zoomout_button);
        isFullScreen = false;
    }

    /**
     * 为控件注册回调处理函数
     */
    private void registerCallbackForControl() {
        PlayOnclickListener playListener = new PlayOnclickListener();
        rl_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                updateControlBar(!barShow);
            }
        });
        mPlaybtn.setOnClickListener(playListener);
        zoom_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                changeScreen();
            }
        });
        pre_play_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendPlayMessage(true);
            }
        });

        OnSeekBarChangeListener osbc1 = new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                // Log.v(TAG, "progress: " + progress);
                updateTextViewWithTimeFormat(mCurrPostion, progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                /**
                 * SeekBar开始seek时停止更新
                 */
                mHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                int iseekPos = seekBar.getProgress();
                /**
                 * SeekBark完成seek时执行seekTo操作并更新界面
                 *
                 */
                mVV.seekTo(iseekPos);
                Log.v(TAG, "seek to " + iseekPos);
                mHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
            }
        };
        mProgress.setOnSeekBarChangeListener(osbc1);
    }

    private void updateTextViewWithTimeFormat(TextView view, int second) {
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp = null;
        if (0 != hh) {
            strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            strTemp = String.format("%02d:%02d", mm, ss);
        }
        view.setText(strTemp);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        /**
         * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
         */
        if (mVV.isPlaying()) {
            mLastPos = mVV.getCurrentPosition();
            mVV.stopPlayback();
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v(TAG, "onResume");
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
        /**
         * 发起一次播放任务,当然您不一定要在这发起
         */
        if (!mVV.isPlaying() && mLastPos != 0) {
            sendPlayMessage(true);
        }
    }

    // private long mTouchTime;
    private boolean barShow = true;
    private Shimmer shimmer;
    private boolean auto_Play = false;

	/*
     * @Override public boolean onTouchEvent(MotionEvent event) {
	 * 
	 * // TODO Auto-generated method stub if (event.getAction() ==
	 * MotionEvent.ACTION_DOWN) mTouchTime = System.currentTimeMillis(); else if
	 * (event.getAction() == MotionEvent.ACTION_UP) { long time =
	 * System.currentTimeMillis() - mTouchTime; if (time < 400) {
	 * updateControlBar(!barShow); } }
	 * 
	 * return false; }
	 */

    public void updateControlBar(boolean show) {

        if (show) {
            mController.setVisibility(View.VISIBLE);
            mHandler.removeMessages(UI_CONTROLLER_INVISIBLE);
            mHandler.sendEmptyMessageDelayed(UI_CONTROLLER_INVISIBLE, 3000);
        } else {
            mController.setVisibility(View.INVISIBLE);
        }
        barShow = show;
    }

    private void videoPause() {
        if (mVV != null) {
            mLastPos = mVV.getCurrentPosition();
            mVV.pause();
        }
    }

    private void videoResume() {
        if (mVV != null) {
            mLastPos = 0;
            mVV.resume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 退出后台事件处理线程
         */
        mHandlerThread.quit();
        EventBus.getDefault().unregister(this);
        mViewPager.removeAllViews();
        if (mVV != null && mVV.isPlaying())
            releaseTask();
    }

    @Override
    public boolean onInfo(int what, int extra) {
        // TODO Auto-generated method stub
        switch (what) {
            /**
             * 开始缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_START:
                break;
            /**
             * 结束缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_END:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     */
    @Override
    public void onPlayingBufferCache(int percent) {
        // TODO Auto-generated method stub

    }

    /**
     * 播放出错
     */
    @Override
    public boolean onError(int what, int extra) {
        // TODO Auto-generated method stub
        Log.v(TAG, "onError");
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(PlayActivity.this, "服务器被大家蹂躏了哦...", Toast.LENGTH_SHORT).show();
            }
        });
        releaseTask();
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion() {
        // TODO Auto-generated method stub
        Log.v(TAG, "onCompletion");
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        if (isReplay) {
            isReplay = false;
            mEventHandler.sendEmptyMessageDelayed(EVENT_PLAY, 1000);
        } else {
            releaseTask();
        }
    }

    private void releaseTask() {
        release();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                VideoAction.setVisibility(View.VISIBLE);
                pre_play_button.setVisibility(View.VISIBLE);
                shimmer.start(shimmer_tv);
            }
        }, 1000);
    }

    private void release() {
        mVV.stopPlayback();
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        mHandler.removeMessages(UI_EVENT_UPDATE_CURRPOSITION);
    }

    /**
     * 准备播放就绪
     */
    @Override
    public void onPrepared() {
        // TODO Auto-generated method stub
        Log.v(TAG, "onPrepared");
        mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
        mHandler.sendEmptyMessage(UI_EVENT_UPDATE_CURRPOSITION);
    }

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            setMinScreen();
        } else {
            super.onBackPressed();
        }
    }
}
