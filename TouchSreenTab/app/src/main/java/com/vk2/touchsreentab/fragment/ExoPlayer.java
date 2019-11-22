package com.vk2.touchsreentab.fragment;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.model.viewmodel.PlayerViewModel;

import java.util.Map;

public class ExoPlayer extends Fragment {
    private PlayerView mPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private String VIDEO_PATH;

    public static ExoPlayer newInstance(@NonNull Bundle bundle) {
        ExoPlayer exoFragment = new ExoPlayer();
        exoFragment.setArguments(bundle);
        return exoFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.exo_player_fragment, container, false);
        mPlayer = result.findViewById(R.id.playerView);
        VIDEO_PATH = getArguments().getString("url");
        initPlayer();
        playerListener();
        return result;
    }


    private void playerListener() {
        if (getActivity() == null) return;
        final PlayerViewModel playerViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PlayerViewModel.class);
        playerViewModel.getPlayer().observe((LifecycleOwner) getActivity(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> stringObjectMap) {
                int action = (int) stringObjectMap.get("action");
                if (action == PlayerViewModel.ACTION_SEEK && simpleExoPlayer != null) {
                    long p = (long) stringObjectMap.get("progress");
                    simpleExoPlayer.seekTo((int) p);
                } else if (action == PlayerViewModel.ACTION_VOCAL) {

                }
            }
        });
    }


    public void initPlayer() {
        Uri uri = Uri.parse(VIDEO_PATH);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
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
