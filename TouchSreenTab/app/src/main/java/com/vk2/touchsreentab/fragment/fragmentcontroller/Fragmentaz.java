package com.vk2.touchsreentab.fragment.fragmentcontroller;

import android.view.View;
import android.widget.LinearLayout;

public class Fragmentaz {
    private static Fragmentaz instance = null;

    public static Fragmentaz getInstance() {
        if (instance == null) {
            instance = new Fragmentaz();
        }
        return instance;
    }

    public void onFragmentListener(Fragmentez frg, FragmentChangeListener fragmentChangeListener, LinearLayout ll1, LinearLayout ll2, LinearLayout ll3, LinearLayout ll4) {
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        ll4.setVisibility(View.GONE);
        switch (frg) {
            case RECOMMEND_FRAGMENT:
                ll1.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onRecommendFragment();
                break;
            case SONG_FRAGMENT:
                ll2.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onSongFragment();
                break;
            case ARTIST_FRAGMENT:
                ll2.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onArtistFragment();
                break;
            case PLAYLIST_FRAGMENT:
                ll3.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onPlaylistFragment();
                break;
            case HISTORY_FRAGMENT:
                ll3.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onHistoryFragment();
                break;
            case DOWNLOAD_FRAGMENT:
                ll3.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onDownloadFragment();
                break;
            case SEARCH_COMPLEX_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null)
                    fragmentChangeListener.onSearchComplexFragment();
                break;
            case SEARCH_SOUNDCLOUND_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null)
                    fragmentChangeListener.onSearchSoundCloudFragment();
                break;
            case SEARCH_YOUTUBE_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null)
                    fragmentChangeListener.onSearchYoutubeFragment();
                break;
            case SINGER_FRAGMENT:
                ll1.setVisibility(View.VISIBLE);
                if (fragmentChangeListener != null) fragmentChangeListener.onSingerFragment();
                break;
        }
    }

    public interface FragmentChangeListener {
        void onRecommendFragment();

        void onSongFragment();

        void onArtistFragment();

        void onPlaylistFragment();

        void onHistoryFragment();

        void onDownloadFragment();

        void onSearchComplexFragment();

        void onSearchSoundCloudFragment();

        void onSearchYoutubeFragment();

        void onSingerFragment();
    }
}
