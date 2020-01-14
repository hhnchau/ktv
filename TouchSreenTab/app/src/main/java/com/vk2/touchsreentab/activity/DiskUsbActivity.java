package com.vk2.touchsreentab.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.FolderAdapter;
import com.vk2.touchsreentab.adapter.FolderNameAdapter;
import com.vk2.touchsreentab.model.viewmodel.FileObject;
import com.vk2.touchsreentab.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.vk2.touchsreentab.utils.Constants.MESSAGE_CURRENT_POSITION;

public class DiskUsbActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public static void showMe(Activity activity){
        Intent intent = new Intent(activity, DiskUsbActivity.class);
        activity.startActivity(intent);
    }
    //
    private final int STALE_IDLE = 0;
    private final int STALE_PLAYING = 1;
    private final int STALE_PAUSE = 2;
    //
    private ImageView btnPrevious, btnNext, btnVolumeUp, btnVolumeDown, btnPlayOrPause, imgSong;
    private LinearLayout layoutLeft, layoutMedia;
    private RelativeLayout layoutRight;
    private RecyclerView rcvListFilesAndFolders, rcvListFolderName;
    private TextView tvEmptyFile, tvSongName, tvCurrentDuration, tvMaxDuration, btnBack, btnBackToBefore, tvEmptyBox;
    private SurfaceView videoSurfaceView;
    private SeekBar seekBarVideo;
    private SlidingPaneLayout slidingPaneLayout;
    //
    private FolderAdapter folderAdapter;
    private FolderNameAdapter folderNameAdapter;
    private ArrayList<FileObject> listFilesAndFolders, listFoldersName;
    private MediaPlayer mediaPlayer;
    private Thread updateSeekBarThread;
    private Handler mHandler;
    private SurfaceHolder surfaceHolder;
    //
    private ViewGroup.LayoutParams paramslayoutLeft;
    private int mCurrentPosition, mCurrentFile, mMaxDuration, mCurrentType, mCurrentState = STALE_IDLE, mLayoutLeftMaxWidth = 0, mLayoutRightMaxWidth = 0;
    private boolean isThreadWorking = false;
    private int marginClose = 0;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_usb);
        connectView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("nhat","onResume");
        if (paramslayoutLeft == null){
            paramslayoutLeft = layoutLeft.getLayoutParams();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connectView() {
        rcvListFilesAndFolders = findViewById(R.id.rcv_list_files);
        rcvListFolderName = findViewById(R.id.rcv_sub_folder);
        tvEmptyFile = findViewById(R.id.tv_empty_file);
        btnBack = findViewById(R.id.btn_back);
        btnPlayOrPause = findViewById(R.id.btn_play_or_pause);
        btnVolumeUp = findViewById(R.id.btn_volume_up);
        btnVolumeDown = findViewById(R.id.btn_volume_down);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnBackToBefore = findViewById(R.id.btn_back_to_file_before);
        tvSongName = findViewById(R.id.tv_song_name);
        tvCurrentDuration = findViewById(R.id.tv_current_duration);
        tvMaxDuration = findViewById(R.id.tv_max_duration);
        videoSurfaceView = findViewById(R.id.video_surface_view);
        seekBarVideo = findViewById(R.id.seekbar_video);
        surfaceHolder = videoSurfaceView.getHolder();
        imgSong = findViewById(R.id.image_song);
        slidingPaneLayout = findViewById(R.id.sliding_pane_layout);
        layoutRight = findViewById(R.id.layout_right);
        layoutLeft = findViewById(R.id.layout_left);
        tvEmptyBox = findViewById(R.id.tv_empty_box);
        layoutMedia = findViewById(R.id.layout_media_right_disk_usb);
//
        btnBack.setOnClickListener(this);
        btnPlayOrPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnVolumeDown.setOnClickListener(this);
        btnVolumeUp.setOnClickListener(this);
        btnBackToBefore.setOnClickListener(this);
        surfaceHolder.addCallback(this);
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(@NonNull View view, float v) {
                Log.e("nhat","onPanelSlide v "+v);
                Log.e("nhat","Layout Right Width"+layoutRight.getWidth());
                Log.e("nhat","Layout Left Width"+layoutLeft.getWidth());
                if (mLayoutLeftMaxWidth == 0){
                    mLayoutLeftMaxWidth = layoutLeft.getWidth();
                    mLayoutRightMaxWidth = layoutRight.getWidth();
                    if (mLayoutLeftMaxWidth > 0 && mLayoutRightMaxWidth >0){
                        marginClose = mLayoutLeftMaxWidth - mLayoutRightMaxWidth + 30;
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutRight.getLayoutParams();
                        params.leftMargin = marginClose;
                    }
                }
                if(mLayoutLeftMaxWidth != 0) {
                    if (v >0 && v <1) {
                        if (v * mLayoutLeftMaxWidth <= mLayoutRightMaxWidth) {
                            if (slidingPaneLayout.isOpen()){
                                slidingPaneLayout.closePane();
                                return;
                            }
                        }
                        paramslayoutLeft.width = (int) (mLayoutLeftMaxWidth -(mLayoutRightMaxWidth - mLayoutRightMaxWidth*(v)));
                        layoutLeft.setLayoutParams(paramslayoutLeft);
                    }
                }
            }

            @Override
            public void onPanelOpened(@NonNull View view) {
                Log.e("nhat","onPanelOpened ");
                paramslayoutLeft.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutLeft.setLayoutParams(paramslayoutLeft);
            }

            @Override
            public void onPanelClosed(@NonNull View view) {
                Log.e("nhat","onPanelClosed ");
                paramslayoutLeft.width = (int) (mLayoutLeftMaxWidth - mLayoutRightMaxWidth*0.95);
                layoutLeft.setLayoutParams(paramslayoutLeft);
            }
        });
//
        slidingPaneLayout.openPane();
        hideDisplay();
        getChildrenFromRoot();
    }
    //    Khoi tao list Folder Name va hien thi Folder Name root
    private void setFolderNameRoot(File fileRoot) {
        if (listFoldersName == null){
            listFoldersName = new ArrayList<>();
        }
//
        FileObject fileObject = new FileObject(fileRoot.getName(), fileRoot.getPath(), 0);
        listFoldersName.add(fileObject);
        folderNameAdapter = new FolderNameAdapter(getApplicationContext(), listFoldersName, new FolderNameAdapter.OnNameFolderClick() {
            @Override
            public void onNameFolderClick(FileObject fileObject, int position) {
                if (!(position == listFoldersName.size()-1)) {
                    backToFolderBefore(fileObject, position);
                }
            }
        });
        rcvListFolderName.setAdapter(folderNameAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvListFolderName.setLayoutManager(linearLayoutManager);

    }
    //      khởi tạo data của list folder root, hiển thị list folder
    private void getChildrenFromRoot() {
        if (listFilesAndFolders == null){
            listFilesAndFolders = new ArrayList<>();
        }
        File fileRoot = new File(Environment.getExternalStorageDirectory().getPath());
        if(!fileRoot.isDirectory())
        {
            return;
        }
        try {
            addFilesToList(fileRoot);
        } catch (NullPointerException ex) {
            Log.e("nhat", "error: " + ex.toString());
        }
        folderAdapter = new FolderAdapter(getApplicationContext(), listFilesAndFolders, new FolderAdapter.OnFileClickListener() {
            @Override
            public void onFileClick(FileObject fileObject, int position) {
                Toast.makeText(getApplicationContext(), "File: "+fileObject.getName(), Toast.LENGTH_SHORT).show();
                int type = fileObject.getType();
                if (type== Constants.TYPE_FOLDER){
                    getChildrenFromFolder(fileObject, false);
                }
                else if (type == Constants.TYPE_FILE_MP3 || type == Constants.TYPE_FILE_MP4){
//                    addPlay song or video
                    addSongFileToPlay(type, fileObject, position);
                }
            }
        });
//
        rcvListFilesAndFolders.setAdapter(folderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvListFilesAndFolders.setLayoutManager(linearLayoutManager);
//
        setFolderNameRoot(fileRoot);
    }
    //      nếu có file thì add vào list
    private void addFilesToList(File file) {
        File[] files = file.listFiles();
        for(File f : files) {
            if(f.isFile() || f.isDirectory()){
                String name = f.getName();
                String path = f.getPath();
                int type = checkTypeFile(f);
//              chỉ hiển thị Folder, và các file có đuôi mp3, mp4
                if (type != Constants.TYPE_FILE_NORMAL){
                    FileObject fileObject = new FileObject(name, path, type);
                    listFilesAndFolders.add(fileObject);
                }
                Log.e("nhat", "name" + name);
                Log.e("nhat", "path:" + path);
                Log.e("nhat", "type:" + type);
            }
        }
        if (listFilesAndFolders.size()==0){
            tvEmptyFile.setVisibility(View.VISIBLE);
        }else {
            tvEmptyFile.setVisibility(View.GONE);
        }
    }
    //      kiểm tra type Folder, Mp3, Mp4
    private int checkTypeFile(File file) {
//        nếu còn extend được nữa thì là folder
        if (file.listFiles() != null){
            return Constants.TYPE_FOLDER;
//            đuôi file là mp3
        }else if (file.getName().contains(Constants.SUFFIX_MP3)){
            return Constants.TYPE_FILE_MP3;
//            đuôi file là mp4
        }else if (file.getName().contains(Constants.SUFFIX_MP4)){
            return Constants.TYPE_FILE_MP4;
        }
//        default là file
        return Constants.TYPE_FILE_NORMAL;
    }
    //    sự kiện quay trở về folder trước
    private void backToFolderBefore(FileObject fileObject, int position){
        if (fileObject != null){
//            remove các thư mục đứng sau thư mục vừa chọn
            if (listFoldersName.size() > position + 1) {
                listFoldersName.subList(position + 1, listFoldersName.size()).clear();
            }
            getChildrenFromFolder(fileObject, true);
        }
        else {
//            remove thư mục cuối
            if (position>0){
                listFoldersName.remove(position);
                getChildrenFromFolder(listFoldersName.get(position-1), true);
            }
        }
        hideDisplay();
    }
    //    extend folder khi click
    private void getChildrenFromFolder(FileObject fileObject, boolean isBack) {
        listFilesAndFolders.clear();
//        update file name
        File file = new File(fileObject.getPath());
        if (!isBack){
            listFoldersName.add(fileObject);
        }
        try {
            addFilesToList(file);
            folderAdapter.notifyDataSetChanged();
            folderNameAdapter.notifyDataSetChanged();
        } catch (NullPointerException ex) {
            Log.e("nhat", "error: " + ex.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
                break;
            }
//            chỉ lùi lại 1 folder
            case R.id.btn_back_to_file_before: {
                backToFolderBefore(null, listFoldersName.size() - 1);
                break;
            }
//            player controller
            case R.id.btn_play_or_pause:{
                if (mCurrentState == STALE_PLAYING){
                    mediaPlayer.pause();
                    mCurrentState = STALE_PAUSE;
                    btnPlayOrPause.setImageResource(R.drawable.play_button);
                }else if (mCurrentState == STALE_PAUSE){
                    mediaPlayer.start();
                    mCurrentState = STALE_PLAYING;
                    btnPlayOrPause.setImageResource(R.drawable.pause_button);
                }
                break;
            }
            case R.id.btn_previous:{
                playPreviousFile(mCurrentFile);
                Log.e("nhat","btn_previous");
                break;
            }
            case R.id.btn_next:{
                playNextFile(mCurrentFile);
                Log.e("nhat","btn_next");
                break;
            }
            case R.id.btn_volume_down:{
                Log.e("nhat","btn_volume_down");
                break;
            }
            case R.id.btn_volume_up:{
                Log.e("nhat","btn_volume_up");
                break;
            }
        }
    }
    private void playPreviousFile(int currentPosition) {
        if (currentPosition > 0) {
            for (int i = currentPosition - 1; i >= 0; i--) {
                if (listFilesAndFolders.get(i).getType() == Constants.TYPE_FILE_MP4||listFilesAndFolders.get(i).getType() == Constants.TYPE_FILE_MP3){
                    addSongFileToPlay(listFilesAndFolders.get(i).getType(), listFilesAndFolders.get(i), i);
                    folderAdapter.setCurrentSelectedFile(i);
                    return;
                }
            }
        }
//      ẩn media, hiện empty
        hideDisplay();
//        nếu không còn lùi lại được thì play file cuối
//        if (listFilesAndFolders.size() > 0) {
//            playPreviousFile(listFilesAndFolders.size());
//        }

    }

    private void hideDisplay() {
        if (mCurrentState != STALE_IDLE){
            releasePlayer();
        }
        if (tvEmptyBox.getVisibility() == View.GONE){
            tvEmptyBox.setVisibility(View.VISIBLE);
            layoutMedia.setVisibility(View.GONE);
        }
        if (folderAdapter != null)
            folderAdapter.setCurrentSelectedFile(-1);
    }

    private void playNextFile(int currentPosition) {
        if (listFilesAndFolders.size()>currentPosition+1){
            for (int i = currentPosition+1; i < listFilesAndFolders.size(); i++){
                if (listFilesAndFolders.get(i).getType() == Constants.TYPE_FILE_MP4||listFilesAndFolders.get(i).getType() == Constants.TYPE_FILE_MP3){
                    addSongFileToPlay(listFilesAndFolders.get(i).getType(), listFilesAndFolders.get(i), i);
                    folderAdapter.setCurrentSelectedFile(i);
                    return;
                }
            }
        }
//        ẩn media, hiện empty
        hideDisplay();
//        dò tiếp từ file đầu
//        if (listFilesAndFolders.size() > 0) {
//            playNextFile(0);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideDisplay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        listFilesAndFolders.clear();
        listFoldersName.clear();
    }

    private void releasePlayer(){
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;

        }
        mCurrentFile = -1;
        mCurrentType = 0;
        mCurrentState = STALE_IDLE;
        tvCurrentDuration.setText(getString(R.string.default_duration));
        tvMaxDuration.setText(getString(R.string.default_duration));
        tvSongName.setText("");
        btnPlayOrPause.setImageResource(R.drawable.play_button);
        if (updateSeekBarThread != null){
            isThreadWorking = false;
            updateSeekBarThread.interrupt();
            updateSeekBarThread = null;
        }
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("surface","surfaceCreated");
        if (mLayoutLeftMaxWidth == 0){
            mLayoutLeftMaxWidth = layoutLeft.getWidth();
            mLayoutRightMaxWidth = layoutRight.getWidth();
            if (mLayoutLeftMaxWidth > 0 && mLayoutRightMaxWidth >0){
                marginClose = mLayoutLeftMaxWidth - mLayoutRightMaxWidth + 30;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutRight.getLayoutParams();
                params.leftMargin = marginClose;
                layoutRight.setLayoutParams(params);
                if (slidingPaneLayout.isOpen()){
                    slidingPaneLayout.closePane();
                }
            }
        }
        if (mediaPlayer != null){
            mediaPlayer.setDisplay(holder);
        }
    }

    // config media player, set data
    private void addSongFileToPlay(int type, FileObject file, int position) {
        Log.e("nhat","addSongFileToPlay");
        if (mCurrentState != STALE_IDLE){
            releasePlayer();
        }
        if (TextUtils.isEmpty(file.getPath())){
            return;
        }
        mCurrentFile = position;
        mCurrentType = type;
        initHandler();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        showViewDisplay(type, file.getName());
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(file.getPath()));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateSeekBar();
    }

    private void showViewDisplay(int type, String name) {
        Log.e("nhat","showViewDisplay");
        if (layoutMedia.getVisibility() == View.GONE){
            layoutMedia.setVisibility(View.VISIBLE);
            tvEmptyBox.setVisibility(View.GONE);
        }
        btnPlayOrPause.setImageResource(R.drawable.pause_button);
        videoSurfaceView.setVisibility(View.GONE);
        if (type == Constants.TYPE_FILE_MP4){
//            Surface Created
            imgSong.setVisibility(View.GONE);
            videoSurfaceView.setVisibility(View.VISIBLE);
        }
        else {
            imgSong.setVisibility(View.VISIBLE);
        }
        if (slidingPaneLayout.isOpen()){
            if (mLayoutLeftMaxWidth != 0)
                slidingPaneLayout.closePane();
        }
        tvSongName.setText(name);
    }

    // tạo handler xử lý trên UI
    private void initHandler() {
        Log.e("nhat","initHandler");
        if (mHandler == null){
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
    }
    //  config cho seekbar, tạo thread update current position của video
    private void updateSeekBar() {
        Log.e("nhat","updateSeekBar");
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
        if (!isThreadWorking){
            isThreadWorking = true;
        }
        if (updateSeekBarThread == null){
            updateSeekBarThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    do {
                        try {
                            int currentTime;
                            if (mediaPlayer != null){
                                currentTime = mediaPlayer.getCurrentPosition();
                                Message msg = new Message();
                                msg.what = MESSAGE_CURRENT_POSITION;
                                msg.arg1 = currentTime;
                                mHandler.sendMessage(msg);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (IllegalStateException ex){
                            Log.e("nhat","ex: "+ex.toString());
                        }
                    } while (isThreadWorking);
                }
            });
            updateSeekBarThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("surface","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("surface","surfaceDestroyed");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mCurrentState = STALE_PLAYING;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        releasePlayer();
        mCurrentState = STALE_IDLE;
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
        if ((seconds - subSecond * 60) <= 9) {
            return minutes + ":0" + ((seconds - subSecond * 60));
        }
        return minutes + ":" + ((seconds - subSecond * 60));
    }
}
