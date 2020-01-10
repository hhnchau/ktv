package com.vk2.touchsreentab.model.setting;

public class SongBroadcast {
    private int position;
    private String song;
    private String singer;

    public SongBroadcast() {
    }


    public SongBroadcast(int position, String song, String singer) {
        this.position = position;
        this.song = song;
        this.singer = singer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
