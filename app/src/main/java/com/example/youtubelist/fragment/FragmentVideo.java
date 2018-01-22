package com.example.youtubelist.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.youtubelist.R;
import com.example.youtubelist.util.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by Suhas Upadya.
 */

public class FragmentVideo extends Fragment {

    private static final String TAG = FragmentVideo.class.getSimpleName();

    private String[] mVideoList;
    private int mPosition;
    private YouTubePlayerFragment mYouTubePlayerFragment;
    private YouTubePlayer mYouTubePlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_item, container, false);
        init();
        initUI();
        return view;
    }

    /**
     * Initialization
     */
    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(Constants.ITEM_POSITION, 0);
        }
    }

    /**
     * Initialize UI.
     */
    private void initUI() {
        mYouTubePlayerFragment = YouTubePlayerFragment.newInstance();
        mVideoList = getResources().getStringArray(R.array.video_list);
        if (getUserVisibleHint()) {
            getChildFragmentManager().beginTransaction().replace(R.id.fl_video_item,
                    mYouTubePlayerFragment).commit();
            mYouTubePlayerFragment.initialize(getString(R.string.api_key), onInitializedListener);
        }
    }

    private YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
            mYouTubePlayer = youTubePlayer;
            if (!b) {
                youTubePlayer.loadVideo(mVideoList[mPosition]);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Log.d(TAG, youTubeInitializationResult.toString());
            if (getActivity() != null) {
                Toast.makeText(getActivity(), R.string.video_error_msg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isVisibleToUser && mYouTubePlayer != null) {
            mYouTubePlayer.release();
            getChildFragmentManager().beginTransaction().remove(mYouTubePlayerFragment).commit();
        }

        if (isVisibleToUser && mYouTubePlayerFragment != null) {
            getChildFragmentManager().beginTransaction().replace(R.id.fl_video_item,
                    mYouTubePlayerFragment).commit();
            mYouTubePlayerFragment.initialize(getString(R.string.api_key), onInitializedListener);
        }
    }
}
