package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.arch.lifecycle.ViewModelProviders;import android.arch.paging.PagedList;import android.os.Build;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.annotation.RequiresApi;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.ProgressBar;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.MultiViewRecommendAdapter;import com.vk2.touchsreentab.database.entity.Singer;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.Ablum;import com.vk2.touchsreentab.model.PageControl;import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;import com.vk2.touchsreentab.model.viewmodel.SongViewModel;import com.vk2.touchsreentab.utils.Constants;import java.util.Objects;public class RecommendFragment extends BaseFragment {    private View view;    private int position;    private int currentPage = 1;    private int numberItem = 6;    private int totalPage = 0;    private boolean isVisible;    private ProgressBar progressBar;    private int totalItem = 0;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_recommend, container, false);        initView();        handleBackButton();        return view;    }    private void initView() {        progressBar = view.findViewById(R.id.progressBar);        if (getActivity() == null) return;        final RecyclerView recyclerView = view.findViewById(R.id.rcvSongs);        final MultiViewRecommendAdapter multiViewRecommendAdapter = new MultiViewRecommendAdapter();        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);        recyclerView.setLayoutManager(layoutManager);        recyclerView.addItemDecoration(getDivider(layoutManager));        recyclerView.setAdapter(multiViewRecommendAdapter);        SongViewModel songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);        songViewModel.getAllListSong();        songViewModel.listSongOnline.observe(this, new Observer<PagedList<Song>>() {            @Override            public void onChanged(@Nullable PagedList<Song> songs) {                if (songs != null) {                    totalPage = (int) Math.ceil((double) Constants.MAX_ITEM_SONG / numberItem);                    updateCurrentPage(currentPage, totalPage);                }                multiViewRecommendAdapter.submitList(songs);            }        });        multiViewRecommendAdapter.setOnItemClick(new MultiViewRecommendAdapter.OnItemClick() {            @Override            public void onBannerClick(Ablum album) {                handleBanner(album);            }            @Override            public void onImageSinger(Singer singer) {                gotoSinger(singer.getId(), singer.getName());            }            @Override            public void onImageClick(Song song) {                gotoSinger(song.getSingerId1(), song.getSingerName1());            }            @Override            public void onItemClick(Song song) {                updatePlaylist(song, PlaylistModelView.ADD);            }            @Override            public void onIconClick(Song song) {                showDialog(song);            }        });        getPageControlViewModel().getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.RECOMMEND_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                if(currentPage > totalPage) {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                }else {                                    int nextPosition = currentPage * numberItem - 1;                                    recyclerView.smoothScrollToPosition(nextPosition);                                }                                int nextPosition = currentPage * numberItem - 1;                                recyclerView.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                recyclerView.smoothScrollToPosition(prevPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.TOP) {                        if (position > 0) {                            recyclerView.post(new Runnable() {                                @Override                                public void run() {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                }                            });                        }                    }                }            }        });        songViewModel.getProgressLoadStatus().observe(this, new Observer<String>() {            @RequiresApi(api = Build.VERSION_CODES.KITKAT)            @Override            public void onChanged(@Nullable String status) {                if (Objects.requireNonNull(status).equalsIgnoreCase(Constants.LOADING)) {                    progressBar.setVisibility(View.VISIBLE);                    Log.e("Tag", "progressBar View.VISIBLE ");                } else if (status.equalsIgnoreCase(Constants.LOADED)) {                    progressBar.setVisibility(View.GONE);                    Log.e("Tag", "progressBar View.GONE ");                }            }        });        songViewModel.getTotalLive().observe(this, new Observer<Integer>() {            @Override            public void onChanged(@Nullable Integer integer) {                totalItem = integer;            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int p;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findFirstVisibleItemPosition();                        if (position == 0 && isVisible) {                            hideBackButton();                            showTopBar();                            isVisible = false;                        } else if (position > 0 && !isVisible) {                            hideTopBar();                            showBackButton();                            isVisible = true;                        }                        p = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = p / numberItem;                        if (currentPage <= 0) return;                        int current = p % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(currentPage, totalPage);                    }                }            }        });    }    private void handleBackButton() {        if (position == 0) {            hideBackButton();            showTopBar();            isVisible = false;        } else {            showBackButton();            hideTopBar();            isVisible = true;        }    }    @Override    protected void onResumeFragment() {        handleBackButton();        clearTextSearch();    }}