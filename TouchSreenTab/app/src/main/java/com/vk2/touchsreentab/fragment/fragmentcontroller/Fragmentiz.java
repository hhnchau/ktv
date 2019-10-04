package com.vk2.touchsreentab.fragment.fragmentcontroller;

import android.support.v4.app.Fragment;

import com.vk2.touchsreentab.fragment.ArtistFragment;
import com.vk2.touchsreentab.fragment.ControlFragment;
import com.vk2.touchsreentab.fragment.DownloadFragment;
import com.vk2.touchsreentab.fragment.HistoryFragment;
import com.vk2.touchsreentab.fragment.PageFragment;
import com.vk2.touchsreentab.fragment.PlayerFragment;
import com.vk2.touchsreentab.fragment.PlaylistFragment;
import com.vk2.touchsreentab.fragment.RecommendFragment;
import com.vk2.touchsreentab.fragment.SearchComplexFragment;
import com.vk2.touchsreentab.fragment.SearchSoundCloundFragment;
import com.vk2.touchsreentab.fragment.SearchYoutubeFragment;
import com.vk2.touchsreentab.fragment.SettingFragment;
import com.vk2.touchsreentab.fragment.SingerFragment;
import com.vk2.touchsreentab.fragment.SongFragment;

public class Fragmentiz {
    public static Fragment getFragment(Fragmentez fzg) {
        switch (fzg) {
            case PAGE_FRAGMENT:
                return new PageFragment();
            case PLAYER_FRAGMENT:
                return new PlayerFragment();
            case CONTROL_FRAGMENT:
                return new ControlFragment();
            case ARTIST_FRAGMENT:
                return new ArtistFragment();
            case PLAYLIST_FRAGMENT:
                return new PlaylistFragment();
            case RECOMMEND_FRAGMENT:
                return new RecommendFragment();
            case SEARCH_COMPLEX_FRAGMENT:
                return new SearchComplexFragment();
            case SEARCH_YOUTUBE_FRAGMENT:
                return new SearchYoutubeFragment();
            case SEARCH_SOUNDCLOUND_FRAGMENT:
                return new SearchSoundCloundFragment();
            case SINGER_FRAGMENT:
                return new SingerFragment();
            case SONG_FRAGMENT:
                return new SongFragment();
            case SETTING_FRAGMENT:
                return new SettingFragment();
            case HISTORY_FRAGMENT:
                return new HistoryFragment();
            case DOWNLOAD_FRAGMENT:
                return new DownloadFragment();
        }
        return null;
    }
}