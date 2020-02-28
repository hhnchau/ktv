package com.vk2.touchsreentab.model.api;

import com.vk2.touchsreentab.database.entity.Song;

public class VerDetail {
    private int filename;
    private String operation;
    private Song song;

    public VerDetail() {
    }

    public int getFilename() {
        return filename;
    }

    public void setFilename(int filename) {
        this.filename = filename;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
