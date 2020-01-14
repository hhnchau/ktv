package com.vk2.touchsreentab.model.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.application.MyApplication;
import com.vk2.touchsreentab.database.entity.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistModelView extends ViewModel {
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int EMPTY = 3;
    public static final int RANDOM = 4;
    public static final int MOVE = 5;
    public static final int PRIORITY = 6;
    public static final int PLAYED = 7;

    private List<Song> cachePlaylist = new ArrayList<>();
    private MutableLiveData<List<Song>> lstPlaylist = new MutableLiveData<>();
    private List<Song> cacheHistory = new ArrayList<>();
    private MutableLiveData<List<Song>> lstHistory = new MutableLiveData<>();

    public MutableLiveData<List<Song>> getPlaylist() {
        return lstPlaylist;
    }

    public MutableLiveData<List<Song>> getHistory() {
        return lstHistory;
    }

    public void setValue(Context context, Song song, int type) {
        if (!song.getFileName().startsWith("Y") && !song.getFileName().startsWith("S"))
            song.setVideoPath("https://manifest.googlevideo.com/api/manifest/hls_playlist/expire/1577717191/ei/Z7kJXq-gMYfcgQPOqbW4Dw/ip/2401:5f80:2001:0:b693:4b78:be4e:e3f/id/O62Vi7_VYXU.0/itag/95/source/yt_live_broadcast/requiressl/yes/ratebypass/yes/live/1/goi/160/sgoap/gir%3Dyes%3Bitag%3D140/sgovp/gir%3Dyes%3Bitag%3D136/hls_chunk_host/r1---sn-i3beln7r.googlevideo.com/playlist_type/DVR/mm/44/mn/sn-i3beln7r/ms/lva/mv/u/mvi/0/pl/36/dover/11/keepalive/yes/fexp/23842630/mt/1577695369/disable_polymer/true/sparams/expire,ei,ip,id,itag,source,requiressl,ratebypass,live,goi,sgoap,sgovp,playlist_type/sig/ALgxI2wwRgIhAMVCGQI7PGspx8k-3Kl9RZzjCzy8d860raIMHLtvcsA4AiEA7S0Cc29_gJWm6T58P7kRVvr4nhC_3KYfTfbumu5t3vQ%3D/lsparams/hls_chunk_host,mm,mn,ms,mv,mvi,pl/lsig/AHylml4wRAIge4mq-rDNPQ9CX1x_bQNHuiYMLcpfUJXZYGsXI4j09AQCICEw376Ux1m9cJL80Ji-KWjmomZX-ubDaR3KPBYzC4Ax/playlist/index.m3u8");
        if (type == ADD)
            for (Song s : cachePlaylist) {
                if (song != null && s.getFileName().equals(song.getFileName())) {
                    Toast.makeText(context, context.getString(R.string.song_added), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        switch (type) {
            case ADD:
                cachePlaylist.add(song);
                if (song != null)
                    new selected().execute(song.getFileName());
                break;
            case REMOVE:
                cachePlaylist.remove(song);
                setHistory(song);
                if (song != null)
                    new unSelected().execute(song.getFileName());
                break;
            case PLAYED:
                cachePlaylist.remove(song);
                setHistory(song);
                if (song != null)
                    new unSelected().execute(song.getFileName());
                break;
            case EMPTY:
                cachePlaylist.clear();
                new clearSelected().execute();
                break;
            case RANDOM:
                Collections.shuffle(cachePlaylist);
                break;
            case MOVE:
                for (int i = 0; i < cachePlaylist.size(); i++) {
                    if (song != null && cachePlaylist.get(i).getFileName().equals(song.getFileName())) {
                        cachePlaylist.remove(i);
                        cachePlaylist.add(1, song);
                        break;
                    }
                }
                break;
            case PRIORITY:
                cachePlaylist.add(0, song);
                break;
        }
        this.lstPlaylist.setValue(cachePlaylist);
    }

    private void setHistory(Song song) {
        cacheHistory.add(0, song);
        this.lstHistory.setValue(cacheHistory);
    }

    static class selected extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... fileName) {
            MyApplication.appDatabase.songDao().setSelected(1, fileName[0]);
            return null;
        }
    }

    static class unSelected extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... fileName) {
            MyApplication.appDatabase.songDao().setSelected(0, fileName[0]);
            return null;
        }
    }

    static class clearSelected extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            MyApplication.appDatabase.songDao().clearSelected();
            return null;
        }
    }
}
