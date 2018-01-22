package com.example.youtubelist.adpater;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.youtubelist.fragment.FragmentVideo;
import com.example.youtubelist.util.Constants;

/**
 * Created by Suhas Upadya.
 */

public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    private int mCount;

    public DashboardPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.mCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentVideo fragmentVideo = new FragmentVideo();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ITEM_POSITION, position);
        fragmentVideo.setArguments(bundle);
        return fragmentVideo;
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
