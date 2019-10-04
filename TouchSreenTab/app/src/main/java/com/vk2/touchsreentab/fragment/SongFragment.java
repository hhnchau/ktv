package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.arch.lifecycle.ViewModelProviders;import android.arch.paging.PagedList;import android.os.AsyncTask;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.Toast;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.SongAdapter;import com.vk2.touchsreentab.aplication.MyApplication;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.PageControl;import com.vk2.touchsreentab.model.viewmodel.PageControlViewModel;import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;import com.vk2.touchsreentab.model.viewmodel.SongViewModel;import com.vk2.touchsreentab.view.CustomSongDialog;public class SongFragment extends BaseFragment {    private View view;    private int position;    private int currentPage = 1;    private int numberItem = 7;    private int totalPage = 0;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_song, container, false);        initView();        return view;    }    private void initView() {        if (getActivity() == null) return;        final RecyclerView recyclerView = view.findViewById(R.id.rcvSongs);        final SongAdapter songAdapter = new SongAdapter();        recyclerView.setAdapter(songAdapter);        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);        recyclerView.setLayoutManager(layoutManager);        recyclerView.addItemDecoration(getDivider(layoutManager));        getSongViewModel().getAllSong(MyApplication.appDatabase.songDao());        getSongViewModel().listSong.observe(getActivity(), new Observer<PagedList<Song>>() {            @Override            public void onChanged(@Nullable PagedList<Song> songs) {                if (songs != null) {                    totalPage = (int) Math.ceil((double) songs.size() / numberItem);                    updateCurrentPage(currentPage, totalPage);                }                songAdapter.submitList(songs);            }        });        songAdapter.setOnItemClick(new SongAdapter.OnItemClick() {            @Override            public void onImageClick(Song song) {                gotoSinger(song.getSingerId1());            }            @Override            public void onItemClick(Song song) {                updatePlaylist(song, PlaylistModelView.ADD);            }            @Override            public void onIconClick(Song song) {                showDialog(song);            }        });        getPageControlViewModel().getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.SONG_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                int nextPosition = currentPage * numberItem - 1;                                recyclerView.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                recyclerView.smoothScrollToPosition(prevPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.TOP) {                        if (position > 0) {                            recyclerView.post(new Runnable() {                                @Override                                public void run() {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                }                            });                        } else {                            gotoRecommend();                        }                    }                }            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int p;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findFirstVisibleItemPosition();                        p = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = p / numberItem;                        if (currentPage <= 0) return;                        int current = p % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(currentPage, totalPage);                    }                }            }        });    }    @Override    public int getCurrentPage() {        return currentPage;    }    @Override    public int getTotalPage() {        return totalPage;    }}