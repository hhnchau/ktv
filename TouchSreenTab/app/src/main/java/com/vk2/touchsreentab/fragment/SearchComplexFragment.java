package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.arch.paging.PagedList;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.text.TextUtils;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.MultiViewComplexAdapter;import com.vk2.touchsreentab.aplication.MyApplication;import com.vk2.touchsreentab.database.entity.Singer;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.PageControl;import com.vk2.touchsreentab.model.TextSearch;import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;public class SearchComplexFragment extends BaseFragment {    private View view;    private String search = "";    private int currentPage = 1;    private int numberItem = 6;    private int totalPage = 0;    private int position;    private boolean isVisible;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_search_complex, container, false);        initRecyclerView();        showBackButton();        hideEnterButton();        return view;    }    private void initRecyclerView() {        if (getActivity() == null) return;        final RecyclerView rcv = view.findViewById(R.id.rcv);        final MultiViewComplexAdapter adapter = new MultiViewComplexAdapter();        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);        rcv.setLayoutManager(layoutManager);        rcv.addItemDecoration(getDivider(layoutManager));        rcv.setAdapter(adapter);        adapter.setOnItemClick(new MultiViewComplexAdapter.OnItemClick() {            @Override            public void onImageSinger(Singer singer) {                gotoSinger(singer.getId(), singer.getName());            }            @Override            public void onImageClick(Song song) {                gotoSinger(song.getSingerId1(), song.getSingerName1());            }            @Override            public void onItemClick(Song song) {                updatePlaylist(song, PlaylistModelView.ADD);            }            @Override            public void onIconClick(Song song) {                showDialog(song);            }        });        getSongViewModel().getSearchSong(MyApplication.appDatabase.songDao());        getSongViewModel().listSearchSong.observe(getActivity(), new Observer<PagedList<Song>>() {            @Override            public void onChanged(@Nullable PagedList<Song> songs) {                if (!TextUtils.isEmpty(search)) {                    if (songs != null) {                        Log.d("TAG - Search Song: ", songs.size() + "");                        totalPage = (int) Math.ceil((double) songs.size() / numberItem);                        updateCurrentPage(currentPage, totalPage);                    }                    adapter.submitList(songs);                    showTopBar();                }            }        });        getTextSearchViewModel().getTextSearch().observe(getActivity(), new Observer<TextSearch>() {            @Override            public void onChanged(TextSearch textSearch) {                if (textSearch.getFrg() == Fragmentez.SEARCH_COMPLEX_FRAGMENT) {                    search = textSearch.getTextSearch();                    getSongViewModel().search.setValue(search);                }            }        });        getPageControlViewModel().getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.SEARCH_COMPLEX_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        rcv.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                int nextPosition = currentPage * numberItem - 1;                                rcv.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        rcv.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                rcv.smoothScrollToPosition(prevPosition);                            }                        });                    }else if (pageControl.getDirection() == PageControl.TOP) {                        if (position > 0) {                            rcv.post(new Runnable() {                                @Override                                public void run() {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    rcv.smoothScrollToPosition(0);                                }                            });                        } else {                            gotoRecommend();                        }                    }                }            }        });        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int p;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findFirstVisibleItemPosition();                        if (position == 0 && isVisible) {                            showTopBar();                            isVisible = false;                        } else if (position > 0 && !isVisible) {                            hideTopBar();                            isVisible = true;                        }                        p = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = p / numberItem;                        if (currentPage <= 0) return;                        int current = p % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(currentPage, totalPage);                    }                }            }        });    }    @Override    protected void onResumeFragment() {        showBackButton();        hideEnterButton();    }}