package com.vk2.touchsreentab.fragment.fragmentcontroller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk2.touchsreentab.R;

public class Fragmenttz {
    public static void setToolbar(Context context, Fragmentez fzg, LinearLayout ll1, LinearLayout ll2, LinearLayout ll3, LinearLayout ll4) {
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        ll4.setVisibility(View.GONE);
        switch (fzg) {
            case PAGE_FRAGMENT:

                break;
            case PLAYER_FRAGMENT:

                break;
            case CONTROL_FRAGMENT:

                break;
            case ARTIST_FRAGMENT:
                ll2.setVisibility(View.VISIBLE);
                break;
            case PLAYLIST_FRAGMENT:
                ll3.setVisibility(View.VISIBLE);
                break;
            case RECOMMEND_FRAGMENT:
                ll1.setVisibility(View.VISIBLE);
                break;
            case SEARCH_COMPLEX_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                break;
            case SEARCH_YOUTUBE_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                break;
            case SEARCH_SOUNDCLOUND_FRAGMENT:
                ll4.setVisibility(View.VISIBLE);
                break;
            case SINGER_FRAGMENT:

                break;
            case SONG_FRAGMENT:
                ll2.setVisibility(View.VISIBLE);
                break;

        }
    }
}