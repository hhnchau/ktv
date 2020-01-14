package com.vk2.touchsreentab.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.model.viewmodel.PlayerViewModel;

import java.util.Map;

public class ExoPlayerFragment extends BaseFragment {
    private PlayerView mPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private ImageView loading;
    private PlayerViewModel playerViewModel;

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
        loading = view.findViewById(R.id.loading);
        Glide.with(loading.getContext()).asGif().load(R.raw.player_loading).into(loading);
        playerListener();
        String videoPath = null;
        String audioPath = null;
        if (getArguments() != null) {
            videoPath = getArguments().getString("url");
            audioPath = getArguments().getString("audio");
        }
        initPlayer(videoPath, audioPath);

        return view;
    }


    private void playerListener() {
        if (getActivity() == null) return;
        playerViewModel = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
        playerViewModel.getPlayer().observe(getActivity(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> stringObjectMap) {
                int action = (int) stringObjectMap.get("action");
                if (action == PlayerViewModel.ACTION_BROADCAST && simpleExoPlayer != null) {
                    playerViewModel.setValue(PlayerViewModel.ACTION_PROGRESS, simpleExoPlayer.getDuration(), simpleExoPlayer.getContentPosition());
                } else if (action == PlayerViewModel.ACTION_SEEK && simpleExoPlayer != null) {
                    long p = (long) stringObjectMap.get("progress");
                    simpleExoPlayer.seekTo((int) p);
                } else if (action == PlayerViewModel.ACTION_VOCAL) {
                    //VOCAL
                }
            }
        });
    }

    private void initPlayer(String videoPath, String audioPath) {
        if (getActivity() == null) return;
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
        mPlayer.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);

        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory("playvideo", null, 50000, 50000, true);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), null, httpDataSourceFactory);

        int videoType = checkVideoType(videoPath);
        if (videoType == C.TYPE_HLS) {

            HlsMediaSource hlsMediaSource = new HlsMediaSource(Uri.parse(videoPath), dataSourceFactory, null, null);
            simpleExoPlayer.prepare(hlsMediaSource);

        } else if (videoType == C.TYPE_DASH) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            Uri uri = Uri.parse(videoPath);
            DataSource.Factory dataSourceFactory1 = new DefaultDataSourceFactory(getActivity(), bandwidthMeter, new DefaultHttpDataSourceFactory("media", bandwidthMeter));
            DashMediaSource videoSource = new DashMediaSource(uri, dataSourceFactory1, new DefaultDashChunkSource.Factory(dataSourceFactory1), null, null);
            simpleExoPlayer.prepare(videoSource);

        } else if (videoType == C.TYPE_SS) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            Uri uri = Uri.parse(videoPath);
            SsMediaSource ssMediaSource = new SsMediaSource(uri, new DefaultDataSourceFactory(getActivity(), null,
                    new DefaultHttpDataSourceFactory("media", null)),
                    new DefaultSsChunkSource.Factory(new DefaultDataSourceFactory(getActivity(), bandwidthMeter,
                            new DefaultHttpDataSourceFactory("media", bandwidthMeter))), null, null);
            simpleExoPlayer.prepare(ssMediaSource);

        } else {
            if (audioPath == null || TextUtils.isEmpty(audioPath)) {
                MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoPath));
                simpleExoPlayer.prepare(mediaSource);
            } else {
                ConcatenatingMediaSource concatenatedSource = new ConcatenatingMediaSource();
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoPath));
                MediaSource audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(audioPath));
                concatenatedSource.addMediaSource(new MergingMediaSource(videoSource, audioSource));
                simpleExoPlayer.prepare(concatenatedSource);
            }
        }

        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                simpleExoPlayer.seekTo(1);
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    Log.d("TAG", "Player ready!!!");
                    if (getActivity() != null && loading != null)
                        loading.setVisibility(View.GONE);
                } else if (playbackState == Player.STATE_ENDED) {
                    if (playerViewModel != null)
                        playerViewModel.setFinish(PlayerViewModel.ACTION_FINISHED);
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

    private int checkVideoType(String videoPath) {
        videoPath = Util.toLowerInvariant(videoPath);
        if (videoPath.endsWith(".mpd") || videoPath.contains("/dash/")) {
            return C.TYPE_DASH;
        } else if (videoPath.endsWith(".m3u8")) {
            return C.TYPE_HLS;
        } else if (videoPath.matches(".*\\.ism(l)?(/manifest(\\(.+\\))?)?")) {
            return C.TYPE_SS;
        } else {
            return C.TYPE_OTHER;
        }
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
