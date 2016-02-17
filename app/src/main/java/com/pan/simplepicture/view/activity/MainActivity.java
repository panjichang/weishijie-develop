package com.pan.simplepicture.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.pan.materialdrawer.AccountHeader;
import com.pan.materialdrawer.AccountHeaderBuilder;
import com.pan.materialdrawer.Drawer;
import com.pan.materialdrawer.DrawerBuilder;
import com.pan.materialdrawer.holder.BadgeStyle;
import com.pan.materialdrawer.holder.StringHolder;
import com.pan.materialdrawer.model.DividerDrawerItem;
import com.pan.materialdrawer.model.PrimaryDrawerItem;
import com.pan.materialdrawer.model.ProfileDrawerItem;
import com.pan.materialdrawer.model.interfaces.IDrawerItem;
import com.pan.materialdrawer.model.interfaces.IProfile;
import com.pan.materialdrawer.util.RecyclerViewCacheUtil;
import com.pan.simplepicture.R;
import com.pan.simplepicture.adapter.ArticleAdapter;
import com.pan.simplepicture.adapter.FragmentAdapter;
import com.pan.simplepicture.adapter.JuzimiAdapter;
import com.pan.simplepicture.bean.User;
import com.pan.simplepicture.presenter.BasePresenter;
import com.pan.simplepicture.presenter.MainPresenter;
import com.pan.simplepicture.utils.ContextUtils;
import com.pan.simplepicture.utils.ScreenUtils;
import com.pan.simplepicture.utils.UserManager;
import com.pan.simplepicture.view.impl.IMainView;
import com.pan.simplepicture.widget.CustomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.update.UmengUpdateAgent;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements IMainView {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    public int getContentLayout() {
        return R.layout.activity_sample_dark_toolbar;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.game:
                startActivity(new Intent(this, SmallGameActivity.class));
                break;
            case R.id.app:
                startActivity(new Intent(this, AppRecommendActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login(SHARE_MEDIA platform) {
        if (mPresenter == null || !(mPresenter instanceof MainPresenter)) return;
        ((MainPresenter) mPresenter).login(this, platform);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter == null || !(mPresenter instanceof MainPresenter)) return;
        ((MainPresenter) mPresenter).onActResult(requestCode, resultCode, data);
    }

    @Override
    public BasePresenter getPresenter() {
        return new MainPresenter();
    }

    private IProfile profile;

    @Override
    public void bindView(Bundle savedInstanceState) {
        UmengUpdateAgent.update(this);
        EventBus.getDefault().register(this);
        setupVideoViewPager();
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        if (UserManager.getInstance().isLogin()) {
            profile = new ProfileDrawerItem().withName(UserManager.getInstance().getUser().screen_name).withEmail("朋友，欢迎您回来 !").withIcon(Uri.parse(UserManager.getInstance().getUser().profile_image_url)).withIdentifier(100);
        } else {
            profile = new ProfileDrawerItem().withName("未登录").withEmail("朋友，欢迎您回来 !").withIcon(R.drawable.ic_default).withIdentifier(100);
        }
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder().withOnlyMainProfileImageVisible(true).withSelectionListEnabled(false)
                .withActivity(this).withHeightDp(230)
                .withHeaderBackground(R.drawable.slider_bg)
                .addProfiles(
                        profile
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                      /*  if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                                headerResult.setActiveProfile();
                            }
                        }*/
                        if (UserManager.getInstance().isLogin())
                            return true;
                        View login_dialog = ContextUtils.inflate(MainActivity.this, R.layout.login_dialog);
                        final CustomDialog dialog = new CustomDialog(MainActivity.this, login_dialog,
                                ScreenUtils.getInstance(MainActivity.this).getWidth() - ContextUtils.dip2px(MainActivity.this, 40),
                                ContextUtils.dip2px(MainActivity.this, 260), Gravity.CENTER);
                        ButterKnife.findById(login_dialog, R.id.login_qq).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                ((MainPresenter) mPresenter).login(MainActivity.this, SHARE_MEDIA.QQ);
                            }
                        });
                        ButterKnife.findById(login_dialog, R.id.login_sina).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                ((MainPresenter) mPresenter).login(MainActivity.this, SHARE_MEDIA.SINA);
                            }
                        });
                        ButterKnife.findById(login_dialog, R.id.cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(getToolbar())
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(GoogleMaterial.Icon.gmd_videocam).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(FontAwesome.Icon.faw_picture_o).withIdentifier(2).withSelectable(true).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(GoogleMaterial.Icon.gmd_text_format).withIdentifier(3).withSelectable(true),
                        new DividerDrawerItem(),
                     /*   new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withSelectable(false),
                        new DividerDrawerItem(),*/
                        new PrimaryDrawerItem().withName(R.string.drawer_item_keyboard_util_drawer).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(5).withSelectable(false)

                        //                    new SwitchDrawerItem().withName("日间模式").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1 && dId != 1) {
                                dId = 1;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupVideoViewPager();
                                //               intent = new Intent(MainActivity.this, TopicActivity.class);
                            } else if (drawerItem.getIdentifier() == 2 && dId != 2) {
                                dId = 2;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupPictureViewPager();
                            } else if (drawerItem.getIdentifier() == 3 && dId != 3) {
                                dId = 3;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupTextViewPager();
                            } /*else if (drawerItem.getIdentifier() == 4) {
                                //                intent = new Intent(MainActivity.this, SmallGameActivity.class);
                            }*/ else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(MainActivity.this, SettingActivity.class);
                                MainActivity.this.startActivity(intent);
                                return true;
                            } else if (drawerItem.getIdentifier() == 6) {
                                //
                               /* if (mSweetSheet != null && !mSweetSheet.isShow()) {
                                    mSweetSheet.show();
                                    return false;
                                }*/
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(MainActivity.this);
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
        //make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }

        result.updateBadge(4, new StringHolder(10 + ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private int dId = 1;

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Bind(R.id.viewpager)
    public ViewPager mViewPager;
    @Bind(R.id.tabs)
    public TabLayout mTabLayout;

    @Override
    protected void onStop() {
        super.onStop();
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }
    }

    private void setupVideoViewPager() {
        String[] titles = getResources().getStringArray(R.array.video_tab);
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void setupPictureViewPager() {
        String[] titles = getResources().getStringArray(R.array.pictrue_tab);
        JuzimiAdapter adapter =
                new JuzimiAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void setupTextViewPager() {
        String[] titles = getResources().getStringArray(R.array.text_tab);
        ArticleAdapter adapter =
                new ArticleAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void onEventMainThread(String remove) {
        if (headerResult != null && "remove".equals(remove)) {
            headerResult.removeProfile(profile);
            profile = new ProfileDrawerItem().withName("未登录").withEmail("朋友，欢迎您回来 !").withIcon(R.drawable.ic_default).withIdentifier(100);
            headerResult.addProfiles(profile);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserInfo(User user) {
        if (headerResult != null) {
            headerResult.removeProfile(profile);
            profile = new ProfileDrawerItem().withName(user.screen_name).withEmail("朋友，欢迎您回来 !").withIcon(Uri.parse(user.profile_image_url)).withIdentifier(100);
            headerResult.addProfiles(profile);
        }
    }
}

