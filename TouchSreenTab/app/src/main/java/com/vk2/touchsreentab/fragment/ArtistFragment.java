package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.arch.paging.PagedList;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.GridLayoutManager;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.ArtistAdapter;import com.vk2.touchsreentab.application.MyApplication;import com.vk2.touchsreentab.database.entity.Singer;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.PageControl;public class ArtistFragment extends BaseFragment {    private View view;    private int position;    private int currentPage = 1;    private int numberItem = 4;    private int totalPage = 0;    private boolean isVisible;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_artist, container, false);        initArtist();        showBackButton();        showTopBar();        clearTextSearch();        return view;    }    private void initArtist() {        if (getActivity() == null) return;        final RecyclerView recyclerView = view.findViewById(R.id.rcvArtists);        recyclerView.setNestedScrollingEnabled(false);        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);        recyclerView.setLayoutManager(layoutManager);        final ArtistAdapter mAdapter = new ArtistAdapter();        recyclerView.setAdapter(mAdapter);        getSingerVewModel().getAllSinger(MyApplication.appDatabase.singerDao());        getSingerVewModel().listSinger.observe(getActivity(), new Observer<PagedList<Singer>>() {            @Override            public void onChanged(@Nullable PagedList<Singer> artists) {                if (artists != null) {                    totalPage = (int) Math.ceil((double) artists.size() / numberItem);                    updateCurrentPage(currentPage, totalPage);                }                mAdapter.submitList(artists);            }        });        getPageControlViewModel().getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.ARTIST_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                int nextPosition = currentPage * numberItem - 1;                                recyclerView.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                recyclerView.smoothScrollToPosition(prevPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.TOP) {                        if (position > 0) {                            recyclerView.post(new Runnable() {                                @Override                                public void run() {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                }                            });                        } else {                            gotoRecommend();                        }                    }                }            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int p;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findFirstVisibleItemPosition();                        if (position == 0 && isVisible) {                            showTopBar();                            isVisible = false;                        } else if (position > 0 && !isVisible) {                            hideTopBar();                            isVisible = true;                        }                        p = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = p / numberItem;                        if (currentPage <= 0) return;                        int current = p % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(currentPage, totalPage);                    }                }            }        });        mAdapter.setOnItemClick(new ArtistAdapter.OnItemClick() {            @Override            public void onClick(Singer singer) {                gotoSinger(singer.getId(), singer.getName());            }        });    }    private void handleTopBar() {        if (position == 0) {            showTopBar();            isVisible = false;        } else {            hideTopBar();            isVisible = true;        }    }    @Override    protected void onResumeFragment() {        updateCurrentPage(currentPage, totalPage);        showBackButton();        clearTextSearch();        handleTopBar();    }}