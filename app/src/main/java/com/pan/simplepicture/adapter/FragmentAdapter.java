package com.pan.simplepicture.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pan.simplepicture.view.fragment.VideoFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        VideoFragment fragment = new VideoFragment();
        fragment.setType(Integer.parseInt(mTitles.get(position).split("@panjichang@")[1]));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split("@panjichang@")[0];
    }
}
