package com.vk2.touchsreentab.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.DualMode;
import com.vk2.touchsreentab.activity.MainActivity;

public class ExoPlayerFragment extends BaseFragment {
    private PlayerView mPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private String VIDEO_PATH;

    public static ExoPlayerFragment newInstance(@NonNull Bundle bundle) {
        ExoPlayerFragment exoFragment = new ExoPlayerFragment();
        exoFragment.setArguments(bundle);
        return exoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exo_player_fragment, container, false);
        mPlayer = view.findViewById(R.id.playerView);
        if (getArguments() != null)
            VIDEO_PATH = getArguments().getString("id");
        initPlayer();
        return view;
    }


    private void initPlayer() {
        if (getActivity() == null) return;
        Uri uri = Uri.parse(VIDEO_PATH);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
        simpleExoPlayer.setVolume(0);
        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(getActivity(), "ExoPlayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        simpleExoPlayer.prepare(audioSource);
        mPlayer.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    if (getActivity() != null)
                        ((DualMode) getActivity()).onFinished();
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

    }

    private void releaseExoPlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseExoPlayer();
    }
}