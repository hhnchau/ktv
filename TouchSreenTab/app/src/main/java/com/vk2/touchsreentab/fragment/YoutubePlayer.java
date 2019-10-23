package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


public class YoutubePlayer extends YouTubePlayerFragment {
    private YouTubePlayer mPlayer;

    public static YoutubePlayer newInstance(@NonNull Bundle bundle) {
        YoutubePlayer videoFragment = new YoutubePlayer();
        videoFragment.init(bundle);
        return videoFragment;
    }

    public void init(@NonNull final Bundle bundle) {
        initialize(bundle.getString("apiKey"), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    mPlayer = youTubePlayer;
                    mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                    mPlayer.loadVideo(bundle.getString("id"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPlayer != null)
            mPlayer.release();
    }
}
