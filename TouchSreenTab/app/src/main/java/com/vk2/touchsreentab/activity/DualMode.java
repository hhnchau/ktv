package com.vk2.touchsreentab.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.commonsware.cwac.preso.PresentationFragment;
import com.commonsware.cwac.preso.PresentationHelper;
import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.ControlAdapter;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.ControlFragment;
import com.vk2.touchsreentab.fragment.ExoPlayerFragment;
import com.vk2.touchsreentab.fragment.FragmentDisplayPresentation;
import com.vk2.touchsreentab.fragment.PageFragment;
import com.vk2.touchsreentab.fragment.NormalPlayerFragment;
import com.vk2.touchsreentab.fragment.SearchYoutubeFragment;
import com.vk2.touchsreentab.fragment.YoutubePlayerFragment;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.model.viewmodel.PlayerViewModel;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;
import com.vk2.touchsreentab.utils.MyTask;
import com.vk2.touchsreentab.utils.OnDoubleClickListener;
import com.vk2.touchsreentab.utils.Utils;
import com.vk2.touchsreentab.utils.YtExtractor;

import java.util.List;
import java.util.Map;


public class DualMode extends BaseActivity implements PresentationHelper.Listener, View.OnClickListener, ControlAdapter.OnItemClick {
    private PlaylistModelView playlistModelView;
    private PlayerViewModel playerViewModel;
    private List<Song> playlist;
    private PageFragment pageFragment;
    private ControlFragment controlFragment;
    private FrameLayout framePlayer, framePage, frameControl;
    private PresentationHelper helper = null;
    private FragmentDisplayPresentation fragmentDisplayPresentation;
    private PresentationFragment preso;
    private View inline = null;
    private Bundle bundle;
    private ConstraintLayout boxPlayer;
    private ConstraintLayout controlPlayer, controlFull, controlSeekBar;
    private TextView currentDuration, maxDuration;
    private SeekBar seekBar;
    private ImageView imgControl, imgFull;
    private TextView tvControl, tvFun;
    private ConstraintSet constraintSet;
    private boolean isPlaying = false;
    private boolean isFull = false;
    private boolean isUpdate = false;
    private boolean isSeek = false;
    private RecyclerView controlRcv;
    private ControlAdapter controlAdapter, funAdapter;
    private View[] arrows;
    private static final int TIMEOUT = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual);

        playlistModelView = ViewModelProviders.of(this).get(PlaylistModelView.class);
        playlistModelView.getPlaylist().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                if (songs.size() == 0) {
                    onDefault();
                } else {
                    playlist = songs;
                    if (!isPlaying) onPlay();
                }
            }
        });


        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.getPlayer().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(final Map<String, Object> stringObjectMap) {
                int action = (int) stringObjectMap.get("action");
                if (action == PlayerViewModel.ACTION_PROGRESS)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isSeek) {
                                long progress = (long) stringObjectMap.get("progress");
                                seekBar.setProgress((int) progress);
                                currentDuration.setText(Utils.intToTime((int) progress));
                            }

                            if (!isUpdate) {
                                long duration = (long) stringObjectMap.get("duration");
                                if (duration > 0) isUpdate = true;
                                seekBar.setMax((int) duration);
                                maxDuration.setText(Utils.intToTime((int) duration));
                            }
                        }
                    });
                else if (action == PlayerViewModel.ACTION_FINISHED)
                    onFinished();
            }
        });

        initView();

        addFragment();

        addControlPlayerToBottom();

        final MyTask myTask = new MyTask(1000, new MyTask.TaskCallback() {
            @Override
            public void task() {
                playerViewModel.setValue(PlayerViewModel.ACTION_BROADCAST, 0, 0);
            }
        });
        myTask.loop();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (isSeek) playerViewModel.setValue(PlayerViewModel.ACTION_SEEK, 0, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeek = true;
                myTask.stop();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeek = false;
                myTask.loop();
            }
        });

        framePlayer.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                if (isFull) playerSmallScreen();
                else playerFullScreen();
                isFull = !isFull;
            }

            @Override
            public void onSingleClick(View v) {
                if (controlFull.getVisibility() == View.GONE)
                    showControl();
                else
                    hideControlRcv();
            }
        });

    }

    private void initView() {
        bundle = new Bundle();
        imgControl = findViewById(R.id.imgControl);
        imgControl.setOnClickListener(this);
        tvControl = findViewById(R.id.tvControl);
        imgFull = findViewById(R.id.imgFun);
        imgFull.setOnClickListener(this);
        tvFun = findViewById(R.id.tvFun);
        findViewById(R.id.imgFull).setOnClickListener(this);
        findViewById(R.id.imgVocal).setOnClickListener(this);
        findViewById(R.id.imgReplay).setOnClickListener(this);
        findViewById(R.id.imgPlay).setOnClickListener(this);
        findViewById(R.id.imgNext).setOnClickListener(this);
        findViewById(R.id.imgReduce).setOnClickListener(this);
        findViewById(R.id.imgAdd).setOnClickListener(this);
        arrows = new View[]{
                findViewById(R.id.arrowControl),
                findViewById(R.id.arrowFun),
        };
        controlRcv = findViewById(R.id.rcvControl);
        controlRcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        controlAdapter = new ControlAdapter(ControlAdapter.CONTROL);
        controlAdapter.setOnItemClick(this);
        funAdapter = new ControlAdapter(ControlAdapter.FUN);
        funAdapter.setOnItemClick(this);
        framePage = findViewById(R.id.framePage);
        frameControl = findViewById(R.id.frameControl);
        currentDuration = findViewById(R.id.currentDuration);
        maxDuration = findViewById(R.id.maxDuration);
        seekBar = findViewById(R.id.seekBar);
        framePlayer = findViewById(R.id.framePlayer);
        boxPlayer = findViewById(R.id.boxPlayer);
        controlPlayer = findViewById(R.id.controlPlayer);
        controlFull = findViewById(R.id.controlFull);
        controlSeekBar = findViewById(R.id.controlSeekBar);
        constraintSet = new ConstraintSet();

        helper = new PresentationHelper(this, this);
        inline = findViewById(R.id.preso);
    }

    private void addFragment() {
        if (pageFragment == null) pageFragment = new PageFragment();
        if (controlFragment == null) controlFragment = new ControlFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.framePage, pageFragment, PageFragment.class.getName())
                .add(R.id.frameControl, controlFragment, ControlFragment.class.getName())
                .commit();
    }

    private void playerFullScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                framePage.setVisibility(View.GONE);
                frameControl.setVisibility(View.GONE);
                controlSeekBar.setVisibility(View.GONE);
                imgControl.setVisibility(View.GONE);
                tvControl.setVisibility(View.GONE);
                imgFull.setVisibility(View.GONE);
                tvFun.setVisibility(View.GONE);
                addControlPlayerToFront();
            }
        });
    }

    private void playerSmallScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                framePage.setVisibility(View.VISIBLE);
                frameControl.setVisibility(View.VISIBLE);
                controlSeekBar.setVisibility(View.VISIBLE);
                imgControl.setVisibility(View.VISIBLE);
                tvControl.setVisibility(View.VISIBLE);
                imgFull.setVisibility(View.VISIBLE);
                tvFun.setVisibility(View.VISIBLE);
                addControlPlayerToBottom();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    public void onPause() {
        helper.onPause();
        super.onPause();
    }

    @Override
    public void showPreso(Display display) {
        if (inline.getVisibility() == View.VISIBLE) {
            inline.setVisibility(View.GONE);
            if (preso != null)
                getFragmentManager().beginTransaction().remove(preso).commit();
        }
        preso = buildPreso(display);
        if (preso != null) {
            preso.show(getFragmentManager(), FragmentDisplayPresentation.class.getName());
            fragmentDisplayPresentation = (FragmentDisplayPresentation) preso;
        }
    }

    @Override
    public void clearPreso(boolean switchToInline) {
        if (switchToInline) {
            inline.setVisibility(View.VISIBLE);
            getFragmentManager().beginTransaction().add(R.id.preso, buildPreso(null), FragmentDisplayPresentation.class.getName()).commit();
        }

        if (preso != null) {
            preso.dismiss();
            preso = null;
        }
    }

    private PresentationFragment buildPreso(Display display) {
        return (FragmentDisplayPresentation.newInstance(this, display));
    }


    private void isYoutubePlayer() {
        constraintSet.clone(boxPlayer);
        constraintSet.connect(R.id.controlFull, ConstraintSet.BOTTOM, R.id.framePlayer, ConstraintSet.TOP);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.TOP, R.id.controlFull, ConstraintSet.BOTTOM);
        constraintSet.setMargin(R.id.framePlayer, ConstraintSet.BOTTOM, 60);
        constraintSet.applyTo(boxPlayer);
    }

    private void noneYoutubePlayer() {
        constraintSet.clone(boxPlayer);
        constraintSet.connect(R.id.controlFull, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.framePlayer, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.setMargin(R.id.framePlayer, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(boxPlayer);
    }


    private void addControlPlayerToBottom() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                constraintSet.clone(boxPlayer);
                constraintSet.connect(R.id.framePlayer, ConstraintSet.BOTTOM, R.id.controlPlayer, ConstraintSet.TOP);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.TOP, R.id.framePlayer, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.START, R.id.framePlayer, ConstraintSet.START);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.END, R.id.framePlayer, ConstraintSet.END);
                constraintSet.applyTo(boxPlayer);
                controlPlayer.setBackground(null);
            }
        });
    }

    private void addControlPlayerToFront() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                constraintSet.clone(boxPlayer);
                constraintSet.connect(R.id.framePlayer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.BOTTOM, R.id.framePlayer, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.TOP, R.id.framePlayer, ConstraintSet.TOP);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.START, R.id.l1, ConstraintSet.START);
                constraintSet.connect(R.id.controlPlayer, ConstraintSet.END, R.id.l2, ConstraintSet.END);
                constraintSet.setVerticalBias(R.id.controlPlayer, 0.98f);
                constraintSet.applyTo(boxPlayer);
                controlPlayer.setBackground(getResources().getDrawable(R.drawable.bg_control_player));
            }
        });
    }

    private void showControlRcv(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 0) {
                    arrows[0].setVisibility(View.VISIBLE);
                    arrows[1].setVisibility(View.GONE);
                    controlRcv.setAdapter(controlAdapter);
                } else {
                    arrows[0].setVisibility(View.GONE);
                    arrows[1].setVisibility(View.VISIBLE);
                    controlRcv.setAdapter(funAdapter);
                }
            }
        });
    }

    private void hideControlRcv() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrows[0].setVisibility(View.GONE);
                arrows[1].setVisibility(View.GONE);
                controlRcv.setAdapter(null);
            }
        });
    }

    private void showControl() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controlFull.setVisibility(View.VISIBLE);
                if (isFull) {
                    controlPlayer.setVisibility(View.VISIBLE);
                    controlSeekBar.setVisibility(View.GONE);
                    imgControl.setVisibility(View.GONE);
                    tvControl.setVisibility(View.GONE);
                    imgFull.setVisibility(View.GONE);
                    tvFun.setVisibility(View.GONE);
                } else {
                    controlSeekBar.setVisibility(View.VISIBLE);
                    imgControl.setVisibility(View.VISIBLE);
                    tvControl.setVisibility(View.VISIBLE);
                    imgFull.setVisibility(View.VISIBLE);
                    tvFun.setVisibility(View.VISIBLE);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideControl();
                hideControlRcv();
            }
        }, TIMEOUT);
    }

    private void hideControl() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controlFull.setVisibility(View.GONE);
                if (isFull) controlPlayer.setVisibility(View.GONE);
                else controlSeekBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFull:
                if (isFull) playerSmallScreen();
                else playerFullScreen();
                isFull = !isFull;
                break;
            case R.id.imgControl:
                showControlRcv(0);
                break;
            case R.id.imgFun:
                showControlRcv(1);
                break;
            case R.id.imgVocal:
                playerViewModel.setValue(PlayerViewModel.ACTION_VOCAL, 0, 0);
                break;
            case R.id.imgReplay:
                onPlay();
                break;
            case R.id.imgPlay:

                break;
            case R.id.imgNext:
                onFinished();
                break;
            case R.id.imgReduce:

                break;
            case R.id.imgAdd:

                break;
            case R.id.framePlayer:
                if (controlFull.getVisibility() == View.GONE)
                    showControl();
                else
                    hideControlRcv();

                break;
        }
    }


    @Override
    public void onVolume(int progress) {

    }

    @Override
    public void onTone(int progress) {

    }

    @Override
    public void onPreset(int btn) {

    }

    @Override
    public void onFun(final int icon) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (fragmentDisplayPresentation != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("res", icon);
                    fragmentDisplayPresentation.showAdsFragment(bundle);
                }
            }
        });
        hideControlRcv();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fragmentDisplayPresentation != null) {
                    fragmentDisplayPresentation.hideAdsFragment();
                }
            }
        }, TIMEOUT);
    }

    public void addPlayerFragment(final Fragmentez frgez, final String url, final String audio) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getBundleId(url, audio);
                if (frgez == Fragmentez.NORMAL_PLAYER_FRAGMENT) {
                    showNormalPlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showNormalPlayer(bundle);
                } else if (frgez == Fragmentez.EXO_PLAYER_FRAGMENT) {
                    showExoPlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showExoPlayer(bundle);
                } else if (frgez == Fragmentez.YOUTUBE_PLAYER_FRAGMENT) {
                    showYoutubePlayer(bundle);
                    if (preso != null && fragmentDisplayPresentation != null)
                        fragmentDisplayPresentation.showYoutubePlayer(bundle);
                }
            }
        });
    }

    private void showNormalPlayer(@NonNull Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framePlayer, NormalPlayerFragment.newInstance(bundle))
                .commit();
    }

    private void showExoPlayer(@NonNull Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framePlayer, ExoPlayerFragment.newInstance(bundle))
                .commit();
    }

    private void showYoutubePlayer(@NonNull Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framePlayer, YoutubePlayerFragment.newInstance(bundle))
                .commit();
    }

    private Bundle getBundleId(String url, String audio) {
        bundle.clear();
        bundle.putString("url", url);
        if (audio != null) bundle.putString("audio", audio);
        bundle.putString("apiKey", "AIzaSyAeUyi3q4lB21g18hUnR9NWpqU8eTDYsl8");
        return bundle;
    }

    public void onDefault() {
        isPlaying = false;
        isUpdate = false;
        addPlayerFragment(Fragmentez.NORMAL_PLAYER_FRAGMENT, "android.resource://" + getPackageName() + "/" + R.raw.buon_khong_em, null);
    }

    private void onPlay() {
        isPlaying = true;
        isUpdate = false;
        if (playlist != null && playlist.size() > 0)
            addPlayerFragment(getVideoType(playlist.get(0)), playlist.get(0).getVideoPath(), playlist.get(0).getAudioPath());
    }

    private void onFinished() {
        isPlaying = false;
        isUpdate = false;
        if (playlist != null && playlist.size() > 0)
            playlistModelView.setValue(this, playlist.get(0), PlaylistModelView.REMOVE);
        else
            onDefault();
    }

    private Fragmentez getVideoType(@NonNull final Song song) {
        if (song.getFileName().startsWith("Y") && song.getVideoPath().startsWith(SearchYoutubeFragment.YOUTUBE_LINK_START)) {
            new YtExtractor.Builder(this).setVideoId(song.getFileName().substring(1)).setListener(new YtExtractor.OnYtListener() {
                @Override
                public void yt(YtExtractor.Link link) {
                    song.setVideoPath(link.getLink());
                    song.setAudioPath(link.getAudio());
                    onPlay();
                }
            }).build();
            return Fragmentez.NORMAL_PLAYER_FRAGMENT;
        } else
            return Fragmentez.EXO_PLAYER_FRAGMENT;
    }
}
