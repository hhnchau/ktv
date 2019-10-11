package com.vk2.touchsreentab.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.MainActivity;
import com.vk2.touchsreentab.adapter.CategoryAdapter;
import com.vk2.touchsreentab.adapter.PlaylistAdapter;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentaz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentcz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentoz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmenttz;
import com.vk2.touchsreentab.model.Category;
import com.vk2.touchsreentab.model.PageControl;
import com.vk2.touchsreentab.model.viewmodel.CategoryModelView;
import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageFragment extends BaseFragment implements View.OnClickListener, Fragmentaz.FragmentChangeListener {
    private View view;
    public static List<Fragmentoz> lstFrg;
    private Fragmentez backStack;
    private LinearLayout boxRecommend, boxSongArtist, boxPlaylist, boxSearch;
    private View[] listArrow;
    private View[] listBackground;
    private ImageView imgBack, arrowCategory;
    private RecyclerView rcvCategory;
    private TextView tvHot, tvNumber, tvRecommend, tvPages;

    private ConstraintLayout topBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page, container, false);
        initView();
        initCategory();
        onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
        return view;
    }

    private void initCategory() {
        if (getActivity() == null) return;
        CategoryModelView categoryModelView = ViewModelProviders.of(getActivity()).get(CategoryModelView.class);
        categoryModelView.getAllCategory();
        categoryModelView.mapCategory.observe(getActivity(), new Observer<Map<String, List<Category>>>() {
            @Override
            public void onChanged(@Nullable Map<String, List<Category>> stringListMap) {
                CategoryAdapter adapter = new CategoryAdapter(stringListMap);
                rcvCategory.setAdapter(adapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
                rcvCategory.setLayoutManager(layoutManager);
                adapter.setOnItemClick(new CategoryAdapter.OnItemClick() {
                    @Override
                    public void onClick(int id, String title) {
                        hideCategory();
                        setCategoryName(id, title);
                    }
                });
            }
        });
    }

    private void setCategoryName(int id, String title) {
        tvHot.setText(title);
    }

    private void showCategory() {
        rcvCategory.setVisibility(View.VISIBLE);
        arrowCategory.setVisibility(View.VISIBLE);
    }

    private void hideCategory() {
        rcvCategory.setVisibility(View.GONE);
        arrowCategory.setVisibility(View.GONE);
    }

    public void setTitleTopBar(final String s) {
        if (getActivity() != null && tvRecommend != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvRecommend.setText(s);
                }
            });
    }

    public void onFragmentChange(Fragmentez frgez) {
        Log.d("TAG - CHANGE FRAGMENT: ", frgez.name());
        if (frgez == Fragmentez.SINGER_FRAGMENT) backStack = Fragmentcz.getCurrentFragment();
        Fragmentcz.addFragment(lstFrg, getFragmentManager(), frgez, false, R.id.layout_container, null, Fragmentcz.NONE);
        Fragmentaz.getInstance().onFragmentListener(frgez, this, boxRecommend, boxSongArtist, boxPlaylist, boxSearch);
        hideCategory();
    }


    @Override
    protected void onPageChange(final int page, final int totalPage) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvPages.setText((page + "/" + totalPage));
                }
            });
    }

    public Fragmentez getBackStackFragment() {
        return backStack;
    }


    @Override
    protected void showBackButton() {
        if (imgBack != null && imgBack.getVisibility() == View.GONE)
            imgBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hideBackButton() {
        if (imgBack != null && imgBack.getVisibility() == View.VISIBLE)
            imgBack.setVisibility(View.GONE);
    }

    @Override
    protected void showTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void hideTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.black_29));
    }

    private void initView() {
        lstFrg = new ArrayList<>();
        tvHot = view.findViewById(R.id.tvHot);
        tvHot.setOnClickListener(this);
        arrowCategory = view.findViewById(R.id.arrowCategory);
        rcvCategory = view.findViewById(R.id.rcvCategory);
        topBar = view.findViewById(R.id.layoutTopbar);
        tvNumber = view.findViewById(R.id.tvNumber);
        boxRecommend = view.findViewById(R.id.boxRecommend);
        boxSongArtist = view.findViewById(R.id.boxSongArtist);
        boxPlaylist = view.findViewById(R.id.boxPlaylist);
        boxSearch = view.findViewById(R.id.boxSearch);
        setOnclickView();
    }

    private void setOnclickView() {
        //header
        tvRecommend = view.findViewById(R.id.tvRecommend);
        tvPages = view.findViewById(R.id.tvPages);
        view.findViewById(R.id.logo).setOnClickListener(this);
        view.findViewById(R.id.songs).setOnClickListener(this);
        view.findViewById(R.id.artists).setOnClickListener(this);
        view.findViewById(R.id.playList).setOnClickListener(this);
        view.findViewById(R.id.setting).setOnClickListener(this);
        view.findViewById(R.id.usb).setOnClickListener(this);
        view.findViewById(R.id.tvPlaylist).setOnClickListener(this);
        view.findViewById(R.id.tvHistory).setOnClickListener(this);
        view.findViewById(R.id.tvDownload).setOnClickListener(this);
        view.findViewById(R.id.tvComplex).setOnClickListener(this);
        view.findViewById(R.id.tvSoundCloud).setOnClickListener(this);
        view.findViewById(R.id.tvMixCloud).setOnClickListener(this);
        view.findViewById(R.id.tvYoutube).setOnClickListener(this);
        view.findViewById(R.id.imgPrevious).setOnClickListener(this);
        view.findViewById(R.id.imgNext).setOnClickListener(this);
        imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        listArrow = new View[]{
                view.findViewById(R.id.position_recommended),
                view.findViewById(R.id.position_song),
                view.findViewById(R.id.position_artists),
                view.findViewById(R.id.position_playlist)
        };
        listBackground = new View[]{
                view.findViewById(R.id.tvPlaylist),
                view.findViewById(R.id.tvHistory),
                view.findViewById(R.id.tvDownload),
                view.findViewById(R.id.tvComplex),
                view.findViewById(R.id.tvSoundCloud),
                view.findViewById(R.id.tvMixCloud),
                view.findViewById(R.id.tvYoutube),
        };

        if (getActivity() == null) return;
        PlaylistModelView playlistModelView = ViewModelProviders.of(getActivity()).get(PlaylistModelView.class);
        playlistModelView.getPlaylist().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                tvNumber.setText(String.valueOf(songs.size()));
            }
        });

    }

    private void showPositionFocus(int positionVisibility) {
        if (positionVisibility >= listArrow.length)
            return;
        for (int i = 0; i < listArrow.length; i++) {
            if (i == positionVisibility) {
                listArrow[i].setVisibility(View.VISIBLE);
            } else {
                listArrow[i].setVisibility(View.GONE);
            }
        }
    }

    private void setActiveBackground(int type) {
        for (int i = 0; i < listBackground.length; i++) {
            if (type == i) {
                listBackground[i].setBackgroundResource(R.drawable.rectangle);
            } else {
                listBackground[i].setBackgroundResource(0);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
                onFragmentChange(Fragmentez.RECOMMEND_FRAGMENT);
                break;
            case R.id.songs:
                onFragmentChange(Fragmentez.SONG_FRAGMENT);
                showPositionFocus(1);
                break;
            case R.id.artists:
                onFragmentChange(Fragmentez.ARTIST_FRAGMENT);
                break;
            case R.id.playList:
                onFragmentChange(Fragmentez.PLAYLIST_FRAGMENT);
                showPositionFocus(3);
                break;
            case R.id.setting:
                onFragmentChange(Fragmentez.SETTING_FRAGMENT);
                break;
            case R.id.usb:
//                CustomDialogMessage dialogMessage = new CustomDialogMessage(getActivity(), new CustomDialogMessage.OnDialogMessageClick() {
//                    @Override
//                    public void onButtonOK() {
//                        Toast.makeText(getContext(), "onButtonOK", Toast.LENGTH_SHORT).show();
////                        ((MainActivity) getActivity()).pageFragmentFullSceen();
////                        if (diskFragment == null) diskFragment = new DiskFragment();
////                        getActivity().getSupportFragmentManager().beginTransaction()
////                                .add(R.id.framePage, diskFragment, DiskFragment.class.getName()).addToBackStack(DiskFragment.class.getName()).commit();
//                    }
//
//                    @Override
//                    public void onButtonCancel() {
//                        Toast.makeText(getContext(), "onButtonCancel", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                dialogMessage.show();
                break;
            case R.id.tvPlaylist:
                onFragmentChange(Fragmentez.PLAYLIST_FRAGMENT);
                break;
            case R.id.tvHistory:
                onFragmentChange(Fragmentez.HISTORY_FRAGMENT);
                break;
            case R.id.tvDownload:
                onFragmentChange(Fragmentez.DOWNLOAD_FRAGMENT);
                break;
            case R.id.tvComplex:
                onFragmentChange(Fragmentez.SEARCH_COMPLEX_FRAGMENT);
                break;
            case R.id.tvSoundCloud:
                onFragmentChange(Fragmentez.SEARCH_SOUNDCLOUND_FRAGMENT);
                break;
            case R.id.tvMixCloud:
                showBackButton();
                setActiveBackground(5);
                break;
            case R.id.tvYoutube:
                onFragmentChange(Fragmentez.SEARCH_YOUTUBE_FRAGMENT);
                break;
            case R.id.imgPrevious:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.PREVIOUS);
                break;
            case R.id.imgNext:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.NEXT);
                break;
            case R.id.imgBack:
                pageControl(Fragmentcz.getCurrentFragment(), PageControl.TOP);
                break;
            case R.id.tvHot:
                if (rcvCategory.getVisibility() == View.GONE)
                    showCategory();
                else
                    hideCategory();
                break;
        }
    }

    @Override
    public void onRecommendFragment() {
        RecommendFragment recommendFragment = (RecommendFragment) getFragmentByTag(RecommendFragment.class.getName());
        if (recommendFragment != null) recommendFragment.onResumeFragment();
        showPositionFocus(0);
        setTitleTopBar(getString(R.string.title_recommended));
    }

    @Override
    public void onSongFragment() {
        SongFragment songFragment = (SongFragment) getFragmentByTag(SongFragment.class.getName());
        if (songFragment != null) songFragment.onResumeFragment();
        showPositionFocus(1);
    }

    @Override
    public void onArtistFragment() {
        ArtistFragment artistFragment = (ArtistFragment) getFragmentByTag(ArtistFragment.class.getName());
        if (artistFragment != null) artistFragment.onResumeFragment();
        showPositionFocus(2);
    }

    @Override
    public void onPlaylistFragment() {
        PlaylistFragment playlistFragment = (PlaylistFragment) getFragmentByTag(PlaylistFragment.class.getName());
        if (playlistFragment != null) playlistFragment.onResumeFragment();
        showPositionFocus(3);
        setActiveBackground(0);
    }

    @Override
    public void onHistoryFragment() {
        HistoryFragment historyFragment = (HistoryFragment) getFragmentByTag(HistoryFragment.class.getName());
        if (historyFragment != null) historyFragment.onResumeFragment();
        setActiveBackground(1);
    }

    @Override
    public void onDownloadFragment() {
        DownloadFragment downloadFragment = (DownloadFragment) getFragmentByTag(DownloadFragment.class.getName());
        if (downloadFragment != null) downloadFragment.onResumeFragment();
        setActiveBackground(2);
    }

    @Override
    public void onSearchComplexFragment() {
        SearchComplexFragment searchComplexFragment = (SearchComplexFragment) getFragmentByTag(SearchComplexFragment.class.getName());
        if (searchComplexFragment != null) searchComplexFragment.onResumeFragment();
        setActiveBackground(3);
    }

    @Override
    public void onSearchSoundCloudFragment() {
        SearchSoundCloundFragment searchSoundCloundFragment = (SearchSoundCloundFragment) getFragmentByTag(SearchSoundCloundFragment.class.getName());
        if (searchSoundCloundFragment != null) searchSoundCloundFragment.onResumeFragment();
        setActiveBackground(4);
    }

    @Override
    public void onSearchYoutubeFragment() {
        SearchYoutubeFragment searchYoutubeFragment = (SearchYoutubeFragment) getFragmentByTag(SearchYoutubeFragment.class.getName());
        if (searchYoutubeFragment != null) searchYoutubeFragment.onResumeFragment();
        setActiveBackground(6);
    }

    @Override
    public void onSingerFragment() {
        SingerFragment singerFragment = (SingerFragment) getFragmentByTag(SingerFragment.class.getName());
        if (singerFragment != null) singerFragment.onResumeFragment();
        showPositionFocus(2);
    }
}