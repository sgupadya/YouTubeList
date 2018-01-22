package com.example.youtubelist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.example.youtubelist.R;
import com.example.youtubelist.adpater.DashboardPagerAdapter;
import com.example.youtubelist.util.Constants;
import com.example.youtubelist.util.NetworkUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;

import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

/**
 * Created by Suhas Upadya.
 */
public class ActivityDashboard extends YouTubeBaseActivity {

    private VerticalViewPager mDashBoardViewPager;
    private DashboardPagerAdapter mDashboardPagerAdapter;
    private static final int VIDEO_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        iniUI();
    }

    /**
     * Initialize UI.
     */
    private void iniUI() {
        mDashBoardViewPager = findViewById(R.id.vp_vertical);
        //mDashBoardViewPager.setOffscreenPageLimit(1);
        if (NetworkUtil.isConnected(this)) {
            setVPAdapter();
        } else {
            showPopup();
        }
    }

    /**
     * Show Dialog box.
     */
    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), Constants.TURN_ON_NETWORK);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setMessage(R.string.network_msg);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Set View Pager with adapter.
     */
    private void setVPAdapter() {
        mDashboardPagerAdapter = new DashboardPagerAdapter(getFragmentManager(), VIDEO_COUNT);
        mDashBoardViewPager.setAdapter(mDashboardPagerAdapter);
        mDashBoardViewPager.setPageTransformer(false, new DefaultTransformer());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TURN_ON_NETWORK) {
            if (NetworkUtil.isConnected(this)) {
                setVPAdapter();
            } else {
                showPopup();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
