package com.vk2.touchsreentab.view;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.database.entity.Song;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.vk2.touchsreentab.utils.Constants.MESSAGE_CURRENT_POSITION;

public class CustomSongDialog extends Dialog implements View.OnClickListener,
        SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    private Context mContext;
    private ImageView ivCancel, ivSelect, ivInsertTop;
    private TextView tvSongName, tvCurrentDuration, tvMaxDuration, tvSinger;
    private SurfaceView videoSurfaceView;
    private SeekBar seekBarVideo;

    MediaPlayer mediaPlayer;
    Thread updateSeekBarThread;

    private Song mSong;
    private int mCurrentPosition, mMaxDuration;
    private SurfaceHolder surfaceHolder;
    private Handler mHandler;
    private boolean isThreadWorking = false;

    public CustomSongDialog(Context context, Song song, OnButtonDialogClick onButtonDialogClick) {
        super(context);
        this.mContext = context;
        this.mSong = song;
        this.onButtonDialogClick = onButtonDialogClick;
    }

    public CustomSongDialog(Context context, Song song) {
        super(context);
        this.mContext = context;
        this.mSong = song;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_song_dialog);
        connectView();
    }

    private void connectView() {
        ivCancel = findViewById(R.id.iv_button_cancel);
        ivSelect = findViewById(R.id.iv_button_select);
        ivInsertTop = findViewById(R.id.iv_button_insert_top);
        tvSongName = findViewById(R.id.tv_song_name);
        tvSinger = findViewById(R.id.tv_singer);
        tvCurrentDuration = findViewById(R.id.tv_current_duration);
        tvMaxDuration = findViewById(R.id.tv_max_duration);
        videoSurfaceView = findViewById(R.id.video_surfaceView);
        seekBarVideo = findViewById(R.id.seekbar_video);
        surfaceHolder = videoSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        ivCancel.setOnClickListener(this);
        ivSelect.setOnClickListener(this);
        ivInsertTop.setOnClickListener(this);

        showSongInfo();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setDisplay(holder);
        showVideo();
    }

    //  hiển thị thông tin video
    private void showSongInfo() {
        if (mSong != null) {
            tvSongName.setText(mSong.getSongName());
            tvSinger.setText(mSong.getSingerName1());
            Log.e("nhat", "Song MType: " + mSong.getMType());
        }
    }

    // config media player, set data
    private void showVideo() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(mContext, Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.buon_khong_em));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initHandler();
        updateSeekBar();
    }

    // tạo handler xử lý trên UI
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_CURRENT_POSITION: {
                        int currentTime = msg.arg1;
                        seekBarVideo.setProgress(currentTime);
                        tvCurrentDuration.setText(millisecondsToString(currentTime));
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }

    //  config cho seekbar, tạo thread update current position của video
    private void updateSeekBar() {
        mMaxDuration = mediaPlayer.getDuration();
        tvMaxDuration.setText(millisecondsToString(mMaxDuration));
        seekBarVideo.setMax(mMaxDuration);
        seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentPosition = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(mCurrentPosition);
                    tvCurrentDuration.setText(millisecondsToString(mCurrentPosition));
                }
            }
        });
//
        isThreadWorking = true;
        updateSeekBarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    int currentTime = mediaPlayer.getCurrentPosition();
                    Log.d("TAG-THREAD", String.valueOf(currentTime));
                    Message msg = new Message();
                    msg.what = MESSAGE_CURRENT_POSITION;
                    msg.arg1 = currentTime;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (isThreadWorking);
            }
        });
        updateSeekBarThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //khi prepare xong thì có thể start video
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isThreadWorking = false;
    }

    //callback
    public interface OnButtonDialogClick {
        void onSelected();

        void onInsertedTop();
    }

    private OnButtonDialogClick onButtonDialogClick;

    public void setOnButtonDialogClick(OnButtonDialogClick onButtonDialogClick) {
        this.onButtonDialogClick = onButtonDialogClick;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_button_cancel: {
                dismiss();
                break;
            }
            case R.id.iv_button_select: {
                if (onButtonDialogClick != null) {
                    onButtonDialogClick.onSelected();
                }
                break;
            }
            case R.id.iv_button_insert_top: {
                if (onButtonDialogClick != null) {
                    onButtonDialogClick.onInsertedTop();
                }
                break;
            }
        }
        dismiss();
    }

    //  release everything here
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        isThreadWorking = false;
    }

    // chuyển milisecond sang định dạng mm:ss, kiểu string
    private String millisecondsToString(int milliseconds) {
        Log.e("nhat", "milisecond: " + milliseconds);
        if (milliseconds == 0) {
            return "0:00";
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) milliseconds);
        long subSecond = seconds / 60;
        if ((seconds - subSecond * 60) < 9) {
            return minutes + ":0" + ((seconds - subSecond * 60) + 1);
        }
        return minutes + ":" + ((seconds - subSecond * 60) + 1);
    }
}
